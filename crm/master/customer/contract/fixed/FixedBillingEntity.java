package com.erp.crm.master.customer.contract.fixed;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "crm_billing_fixed")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper=true)
public class FixedBillingEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "contract")
	private Long contract;
	
	@NotNull
	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "milestone_name")
	private String milestoneName;

	@Column(name = "milestone_date")
	private LocalDate milestoneDate;

	@Column(name = "milestone_amount")
	private BigDecimal milestoneAmount;
	
	@Column(name = "currency_code")
	private String currencyCode;
	
}
