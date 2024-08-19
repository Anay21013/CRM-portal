package com.erp.core.security.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;

}
