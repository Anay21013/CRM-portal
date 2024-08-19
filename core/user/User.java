package com.erp.core.user;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.erp.core.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(Include.NON_NULL)
public class User extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long orgId;
	private Long roleId;
	private String email;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private Boolean verified;
	private Boolean disabled;
	private Integer unsuccessfulCount;
	private String forgotPasswordToken;
	private LocalDateTime lastSuccessfulLogin;
	private LocalDateTime lastPasswordChange;
	private LocalDate effectiveDate;
	private Long employeeId;
	
	private String roleName;
	private List<String> permissionNamesList;

	public User(UserEntity userEntity) {

		this.id = userEntity.getId();
		this.orgId = userEntity.getOrgId();
		this.employeeId = userEntity.getEmployeeId();
		this.roleId = userEntity.getRoleId();
		this.email = userEntity.getEmail();
		this.userName = userEntity.getUserName();
		this.password = userEntity.getPassword();
		this.firstName = userEntity.getFirstName();
		this.lastName = userEntity.getLastName();
		this.verified = userEntity.getVerified();
		this.disabled = userEntity.getDisabled();
		this.unsuccessfulCount = userEntity.getUnsuccessfulCount();
		this.forgotPasswordToken = userEntity.getForgotPasswordToken();
		this.lastSuccessfulLogin = userEntity.getLastSuccessfulLogin();
		this.lastPasswordChange = userEntity.getLastPasswordChange();
		this.effectiveDate = userEntity.getEffectiveDate();

		super.populateBaseDTO(userEntity);
	}

	public String getName() {
		return (getFirstName() != null ? (getFirstName().trim() + " ") : "")
				+ (getLastName() != null ? getLastName().trim() : "");
	}

	public UserEntity populateUserEntity() {
		UserEntity userEntity = new UserEntity();
		super.populateBaseEntity(userEntity);

		userEntity.setId(getId());
		userEntity.setOrgId(getOrgId());
		userEntity.setEmployeeId(getEmployeeId());
		userEntity.setUserName(getUserName());
		userEntity.setRoleId(getRoleId());
		userEntity.setEmail(getEmail());
		userEntity.setPassword(getPassword());
		userEntity.setFirstName(getFirstName());
		userEntity.setLastName(getLastName());
		userEntity.setVerified(getVerified());
		userEntity.setDisabled(getDisabled());
		userEntity.setUnsuccessfulCount(getUnsuccessfulCount());
		userEntity.setForgotPasswordToken(getForgotPasswordToken());
		userEntity.setLastSuccessfulLogin(getLastSuccessfulLogin());
		userEntity.setLastPasswordChange(getLastPasswordChange());
		userEntity.setEffectiveDate(getEffectiveDate());

		return userEntity;
	}

}
