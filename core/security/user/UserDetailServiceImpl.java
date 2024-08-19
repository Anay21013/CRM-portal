package com.erp.core.security.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erp.core.role.IRoleService;
import com.erp.core.user.User;
import com.erp.core.user.UserEntity;
import com.erp.core.user.UserRepository;

@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private IRoleService roleService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		UserEntity userEntity = userRepository.findUserByEmail(username, false);
		List<UserEntity> userEntity = userRepository.findByUserName(username);
		User user = new User(userEntity.get(0));
		
		List<String> permissionNameList = roleService.getAllPermissionNamesForRole(user.getRoleId());
		user.setPermissionNamesList(permissionNameList);
		return UserDetailsImpl.build(user);
	}

}
