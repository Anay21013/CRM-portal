package com.erp.core.security.user;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.erp.core.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long orgId;
	private Long roleId;
	private Long employeeId;
	private String email;
	private String userName;
	private List<String> permissionNamesList;

	@JsonIgnore
	private String password;

	private boolean verified;
	private String firstName;
	private String lastName;
	private Boolean disabled;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long id, String email, String password, boolean verified, Long orgId, Long roleId,
			boolean disabled, String firstName, String lastName, Long employeeId, List<String> permissionNamesList, String userName) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.verified = verified;
		this.orgId = orgId;
		this.roleId = roleId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.disabled = disabled;
		this.employeeId = employeeId;
		this.permissionNamesList = permissionNamesList;
		this.userName = userName;
	}

	public static UserDetailsImpl build(User user) {
		return new UserDetailsImpl(user.getId(), user.getEmail(), user.getPassword(), user.getVerified(),
				user.getOrgId(), user.getRoleId(), user.getDisabled(), user.getFirstName(), user.getLastModifiedBy(), user.getEmployeeId(), user.getPermissionNamesList(), user.getUserName());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public Long getOrgId() {
		return orgId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public List<String> getPermissionNamesList() {
		return permissionNamesList;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public boolean getVerified() {
		return verified;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}

	@Override
	public String getUsername() {
		return userName;
	}

}
