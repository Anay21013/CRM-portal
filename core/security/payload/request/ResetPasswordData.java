package com.erp.core.security.payload.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResetPasswordData {
	private Long userId;
	private String email;
	private String token;
	private String password;

}