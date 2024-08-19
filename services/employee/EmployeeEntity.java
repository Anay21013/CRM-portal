package com.erp.services.employee;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.annotations.SQLDelete;

import com.erp.core.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "employee")
@SQLDelete(sql = "UPDATE employee SET deleted = true WHERE id = ?")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class EmployeeEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "org_id")
	@NotNull
	private Long orgId;
	
	@Column(name = "employee_number")
	@Min(100000)
	@Max(999999)
	private Integer employeeNumber;
	
	private Integer type;

	private String prefix;

	@Column(name = "first_name")
	@NotNull
	private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "reporting_manager")
	private Long reportingManager;
	
	@Column(name = "dob")
	private LocalDate dob;
	
	@Column(name = "joining_date")
	private LocalDate joiningDate;
	
	@Column(name = "alt_email")
	private String alternateEmail;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String homePhone;

	@Column(name = "work_phone")
	private String workPhone;

	@Column(name = "address_line1")
	private String addressLine1;

	@Column(name = "address_line2")
	private String addressLine2;

	@Column(name = "city")
	private String addressCity;

	@Column(name = "state")
	private String addressState;

	@Column(name = "zip")
	private String addressZip;

	@Column(name = "country")
	private String addressCountry;
	
	@Column(name = "approver")
	private Boolean approver;
	
	@Column(name = "role_id")
	private Long roleId;

	@Column(name = "confirmation_date")
	private LocalDate confirmationDate;
	
	@Column(name = "internship_end_date")
	private LocalDate internshipEndDate;
	
	@Column(name = "confirmed")
	private Boolean confirmed;
	
	@Column(name = "separation_date")
	private LocalDate separationDate;
	
	@Column(name = "separated")
	private Boolean separated;
	
	@Column(name = "separation_remark")
	private String separationRemark;
	
	@Column(name = "gender")
	private Integer gender;
	
	@Column(name = "status")
	private Integer status;
	
	@PreUpdate
	@PrePersist
	private void preInsert() {
		if(this.separated == null) {
			this.separated = false;
		}
		if(this.confirmed == null) {
			this.confirmed = false;
		}
		if(this.approver == null) {
			this.approver = false;
		}
	}
}
