package com.erp.core.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erp.core.exceptions.BusinessException;
import com.erp.core.role.IRoleService;
import com.erp.core.role.Role;
import com.erp.core.security.jwt.JwtUtils;
import com.erp.core.security.payload.request.LoginRequest;
import com.erp.core.security.payload.request.ResetPasswordData;
import com.erp.core.security.payload.response.JwtResponse;
import com.erp.core.security.user.UserDetailsImpl;

import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Transactional
public class UserService implements IUserService {

//	@Value("${ses.source}")
//	private String systemEmail;
	
	@Autowired
	private Environment environment;

	@Autowired
	private UserRepository userRepository;

//	@Autowired
//	private IEmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private Environment environmentProperties;

	@Override
	public JwtResponse authenticateUser(LoginRequest loginRequest) {
		log.debug("Authenticating User....");
		UserEntity userEntity = userRepository.findUserByEmail(loginRequest.getUsername().toLowerCase(), false);
		
		if(userEntity != null && userEntity.getDisabled()!=null && userEntity.getDisabled().equals(Boolean.TRUE)) {
			throw new BusinessException("Please provide valid credentials.");
		}
		Authentication authentication = null;
		try {
		  authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					loginRequest.getUsername().toLowerCase(), loginRequest.getPassword()));
		}catch (Exception e) {
			
			if (userEntity == null || !userEntity.getVerified()) {
				// we will ignore in case account is not verified or account does not exists
				throw new BusinessException("Please provide valid credentials.");
			}
			userEntity.setUnsuccessfulCount(userEntity.getUnsuccessfulCount() != null ? userEntity.getUnsuccessfulCount()+1 : 1);
			userRepository.save(userEntity);
			
			if(userEntity.getUnsuccessfulCount() > 2) {
				userEntity.setPassword(null);
				userRepository.save(userEntity);
				throw new BusinessException("Your account has been locked. Please click on Forgot password link below to reset your password.");
			}
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		log.debug("Fetching User Details....");
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		log.debug("Fetching roles for user by name....");
		Role role = roleService.getRole(userDetails.getRoleId());
		role.setPermissions(roleService.getAllPermissionForRole(role.getId()));

		return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), role,
				userDetails.getVerified());
	}

	@Override
	public boolean checkIfUserExist(String email) {
		log.debug("Checking if user exists or not....");
		return userRepository.findUserByEmail(email, false) != null;
	}
	
	@Override
	public User getUserByUserId(Long userId) {
		Optional<UserEntity> userEntity = userRepository.findById(userId);
		if (!userEntity.isPresent()) {
			throw new BusinessException("Please provide valid credentials.");
		} else {
			return new User(userEntity.get());
		}
	}

	@Override
	public User updatePassword(String password, Long userId) {
		User user = null;
		try {
			log.debug("Fetching user detail for id: {}", userId);
			user = getUserByUserId(userId);
		} catch (Exception e) {
			throw new BusinessException("Please provide valid credentials.");
		}
		if (passwordEncoder.matches(password, user.getPassword())) {
			throw new BusinessException(environment.getProperty("duplicate.password.input.error"));
		}
		user.setPassword(passwordEncoder.encode(password));
		user.setVerified(true);
		userRepository.save(user.populateUserEntity());
		user.setPassword(null);
		return user;
	}

	@Override
	public List<User> getAllUsers(Integer roleValue) {
		log.debug("fetching all users....");
		List<UserEntity> usersEntities =  new ArrayList<>();
		if(roleValue==null) {
		    usersEntities = userRepository.getNonDeletedUsers();
		}else {
			usersEntities= userRepository.getByRoleId(Long.valueOf(roleValue));
		}
		
		List<User> users = new ArrayList<>();
		usersEntities.forEach(userEntity -> {
			userEntity.setPassword(null);
			users.add(new User(userEntity));
		});
		return users;
	}
	
	public User getUserDetails(Long Id) {
		log.debug("fetching user with id: "+ Id);
		UserEntity userEntity= userRepository.findByIdAndDeletedFalse(Id);
		User user= new User(userEntity);
		user.setPassword(null);
		return user;
	}
	
	@Override
	public User sendResetPasswordCode(String email) {
		log.debug("Sending forgot password email....");
		UserEntity userEntity= userRepository.findUserByEmail(email,false);
		
		if(userEntity == null) {
			throw new BusinessException("Please provide valid credentials.");
		}
		if(userEntity != null && userEntity.getDisabled()!=null && userEntity.getDisabled().equals(Boolean.TRUE)) {
			throw new BusinessException("Please provide valid credentials.");
		}
		User user = new User(userEntity);
		user = generateAndSaveForgotPasswordToken(user);
		return user;
	}
	@Override
	public User deleteUser(Long id) {
		log.debug("Deleting user with id: "+id);
		UserEntity userEntity = (userRepository.findById(id)).get();
		userEntity.setDeleted(true);
		userEntity = userRepository.save(userEntity);
		userEntity.setPassword(null);
		return new User(userEntity);
	} 
	
	@Override
	public User updateUser(Long id,User user) {
		log.debug("Updating User with id: "+user.getId());
		UserEntity userEntityFromDB = (userRepository.findById(id)).get();
		UserEntity userEntity = user.populateUserEntity();
		if(userEntityFromDB.equals(userEntity)) {
			log.debug("No Changes For Current User with Id : "+userEntity.getId());
		}
		else {
			if(userEntity.getFirstName()!=null)
			userEntityFromDB.setFirstName(userEntity.getFirstName());
			
			if(userEntity.getLastName()!=null)
			userEntityFromDB.setLastName(userEntity.getLastName());
			userEntityFromDB.setRoleId(userEntity.getRoleId());
			userEntityFromDB = userRepository.save(userEntityFromDB);

		}
		user= new User(userEntityFromDB);
		user.setPassword(null);
		return user;
	}
	
	private User generateAndSaveForgotPasswordToken(User user) {
		String forgotPasswordToken = String.format("%06d", new Random().nextInt(999999));
		user.setForgotPasswordToken(forgotPasswordToken.toString());
		sendForgetPasswordEmail(user);
		UserEntity userEntity = userRepository.save(user.populateUserEntity());
		return new User(userEntity);
	}
	
	@Async("threadPoolTaskExecutor")
	private void sendForgetPasswordEmail(User user) {
		
		String subject = "Password Reset Code";
		String textBody = "<p>Hi, " + user.getName() + "</p>"
				+ "<br>"
				+ "<p>Your password reset code is <strong>" + user.getForgotPasswordToken() + "</strong></p>"
				+ "<br>"
				+ "<p>Regards,<br>IConnect Team";
		
		String htmlBody = "<html>"
                + "<head></head>"
                + "<body>"
                + textBody
                + "</body>"
                + "</html>";
		
		try {
			if(environment.equals("local")) {
				Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
				if (authentication == null) {
					throw new JwtException("Authorization can not be empty.");
				}
				UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
//				emailService.sendEmailMessage(userDetail.getEmail(), null, null, systemEmail, htmlBody, textBody, subject, null);
				log.info("Invite email sent successfully to {}.",userDetail.getEmail());
			}else {
//				emailService.sendEmailMessage(user.getEmail(), null, null, systemEmail, htmlBody, textBody, subject, null);
				log.info("Invite email sent successfully to {}.",user.getEmail());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public User resetPassword(ResetPasswordData resetPasswordData) {
		log.debug("Resetting user password....");
		UserEntity userEntity = userRepository.findUserByEmail(resetPasswordData.getEmail(), false);
		
		if(userEntity != null && userEntity.getDisabled()!=null && userEntity.getDisabled().equals(Boolean.TRUE)) {
			throw new BusinessException("Please provide valid credentials.");
		}
		
		if (passwordEncoder.matches(resetPasswordData.getPassword(), userEntity.getPassword())) {
			throw new BusinessException(environment.getProperty("duplicate.password.input.error"));
		}
		if (userEntity.getForgotPasswordToken().equals(resetPasswordData.getToken())) {
			userEntity.setUnsuccessfulCount(0);
			userEntity.setPassword(passwordEncoder.encode(resetPasswordData.getPassword()));
			userRepository.save(userEntity);
		} else {
			throw new BusinessException(environment.getProperty("incorrect.reset.code.error"));
		}
		return new User(userEntity);
	}

	@Override
	public User addUser(User userToSave) {
		if (checkIfUserExist(userToSave.getEmail().toLowerCase())) {
			throw new BusinessException("User already exists with this email - "+userToSave.getEmail());
		}
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
		String generatedPassword = RandomStringUtils.random(10, characters);
		
		UserEntity userEntity = userToSave.populateUserEntity();
		userEntity.setEmail(userToSave.getEmail().toLowerCase());
		userEntity.setPassword(passwordEncoder.encode(generatedPassword));
		userEntity.setVerified(false);
		userEntity.setDisabled(Boolean.FALSE);
		
		User user = new User(userRepository.save(userEntity));
		sendInviteEmail(user, generatedPassword);
		return user;
	}
	
	@Async("threadPoolTaskExecutor")
	private void sendInviteEmail(User user, String password) {
		
		String subject = "Welcome to iConnect";
		
		String environment = environmentProperties.getProperty("erp.services.deployment.environment");
		if(!environment.equals("prod")) {
			subject = "["+environment.toUpperCase()+"] : "+subject;
		}
		
		String textBody = "<p>Hi, " + user.getName() + "</p>"
				+ "<br>"
				+ "<p>Welcome to the iConnect, one stop portal for your all official information and services. You can login in the portal with "
				+ "<p>Username : <strong>"+user.getUserName()+"</strong></p>"
				+ "<p>Code : <strong>" + password + "</strong></p>"
				+ "<p> Application URL: <strong>"+environmentProperties.getProperty(environment+".application.url")+ "</strong></p>"
				+ "<p>Please verify your information on the system and do let us know at iConnect Support ID for any corrections. </p>"
				+ "<p>Regards,<br>IConnect Team";
		
		String htmlBody = "<html>"
                + "<head></head>"
                + "<body>"
                + textBody
                + "</body>"
                + "</html>";
		
		try {
			if(environment.equals("local")) {
				Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
				if (authentication == null) {
					throw new JwtException("Authorization can not be empty.");
				}
				UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
//				emailService.sendEmailMessage(userDetail.getEmail(), null, null, systemEmail, htmlBody, textBody, subject, null);
				log.info("Invite email sent successfully to {}.",userDetail.getEmail());
			}else {
//				emailService.sendEmailMessage(user.getEmail(), null, null, systemEmail, htmlBody, textBody, subject, null);
				log.info("Invite email sent successfully to {}.",user.getEmail());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public User deactivateUser(Long id) {
		log.debug("deactivating user with id: "+ id);
		UserEntity userEntityFromDB= userRepository.findByIdAndDeletedFalse(id);
		
		userEntityFromDB.setDisabled(Boolean.TRUE);
		userEntityFromDB = userRepository.save(userEntityFromDB);
		userEntityFromDB.setPassword(null);
		
		return new User(userEntityFromDB);
	}

	@Override
	public User activateUser(Long id) {
		log.debug("activating user with id: "+ id);
		UserEntity userEntityFromDB= userRepository.findByIdAndDeletedFalse(id);
		
		userEntityFromDB.setDisabled(Boolean.FALSE);
		userEntityFromDB = userRepository.save(userEntityFromDB);
		userEntityFromDB.setPassword(null);
		
		return new User(userEntityFromDB);
	}

	@Override
	public User updateEmployeeIdInUser(Long userId, Long newEmployeeId, Long newRoleId) {
		log.debug("Updating employee id for user {} to {} ", userId, newEmployeeId);

		UserEntity userEntityFromDB = userRepository.findById(userId).get();

		userEntityFromDB.setEmployeeId(newEmployeeId);
		userEntityFromDB.setRoleId(newRoleId);
		userEntityFromDB = userRepository.save(userEntityFromDB);

		return new User(userEntityFromDB);

	}
}
