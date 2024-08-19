package com.erp.crm.master.customer.contract;

import java.io.Serializable;
import java.time.LocalDate;

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
@Table(name = "crm_contract")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper=true)
public class ContractEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "customer")
	private Long customer;
	
	@NotNull
	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "name")
	private String name;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "owner")
	private Long owner;
	
	@Column(name = "type")
	private Integer type;

}
