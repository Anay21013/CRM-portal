package com.erp.core.security.payload.response;

import com.erp.core.role.Role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private boolean verified;
	private Role role;

	public JwtResponse(String accessToken, Long id, String username, String email, Role role, boolean verified) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role;
		this.verified = verified;
	}

}
