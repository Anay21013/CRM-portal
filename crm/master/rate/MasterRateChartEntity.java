package com.erp.crm.master.rate;

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
@Table(name = "crm_master_rate_chart")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class MasterRateChartEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "master_customer")
	private Long masterCustomer;

	@NotNull
	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "role_name")
	private String roleName;

	@Column(name = "role_rate")
	private BigDecimal roleRate;

	@Column(name = "role_rate_frequency")
	private Integer roleRateFrequency;

	@Column(name = "currency_code")
	private String currencyCode;
	
	@Column(name = "start_date")
	private LocalDate startDate;

}
