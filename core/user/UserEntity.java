package com.erp.core.user;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.erp.core.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "org_id")
	private Long orgId;
	
	@Column(name = "employee_id")
	private Long employeeId;
	
	@Column(name = "role_id")
	private Long roleId;

	@NotNull
	@Email
	@Column(name = "email", unique = true)
	private String email;
	
	@Column(name = "user_name")
	private String userName;

	private String password;
	
	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	private Boolean verified;
	
	private Boolean disabled;

	@Column(name = "unsuccessful_count")
	private Integer unsuccessfulCount;
	
	@Column(name = "forgot_password_token")
	private String forgotPasswordToken;
	
	@Column(name = "last_sucessful_login")
	private LocalDateTime lastSuccessfulLogin;
	
	@Column(name = "last_password_change")
	private LocalDateTime lastPasswordChange;

	@Column(name = "effective_date")
	private LocalDate effectiveDate;

}
