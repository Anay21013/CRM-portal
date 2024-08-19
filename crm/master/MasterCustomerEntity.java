package com.erp.crm.master;

import java.io.Serializable;

import com.erp.core.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "crm_master_customer")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper=true)
public class MasterCustomerEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "name")
	private String name;

	@Column(name = "payment_terms")
	private Integer paymentTerms;

	@Column(name = "phone")
	private String phone;

	@Column(name = "email")
	private String email;

	@Column(name = "address_line1")
	private String addressLine1;

	@Column(name = "address_line2")
	private String addressLine2;

	@Column(name = "city")
	private String city;

	@Column(name = "le_state")
	private String state;

	@Column(name = "zip")
	private String zip;

	@Column(name = "country")
	private String country;
}
