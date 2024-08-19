package com.erp.crm.master;

import java.io.Serializable;
import java.util.List;

import com.erp.core.base.BaseDTO;
import com.erp.crm.master.customer.Customer;
import com.erp.crm.master.rate.MasterRateChart;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class MasterCustomer extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long orgId;
	private String name;
	private Integer paymentTerms;
	private String phone;
	private String email;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zip;
	private String country;
	
	private List<Customer> customers;
	private MasterRateChart rateChart;
	

	public MasterCustomer(MasterCustomerEntity customerEntity) {
		this.id = customerEntity.getId();
		this.orgId = customerEntity.getOrgId();
		this.name = customerEntity.getName();
		this.paymentTerms = customerEntity.getPaymentTerms();
		this.phone = customerEntity.getPhone();
		this.email = customerEntity.getEmail();
		this.addressLine1 = customerEntity.getAddressLine1();
		this.addressLine2 = customerEntity.getAddressLine2();
		this.city = customerEntity.getCity();
		this.state = customerEntity.getState();
		this.zip = customerEntity.getZip();
		this.country = customerEntity.getCountry();

		super.populateBaseDTO(customerEntity);
	}

	public MasterCustomerEntity populateMasterCustomerEntity() {
		MasterCustomerEntity customerEntity = new MasterCustomerEntity();
		super.populateBaseEntity(customerEntity);

		customerEntity.setId(getId());
		customerEntity.setOrgId(getOrgId());
		customerEntity.setName(getName());
		customerEntity.setPaymentTerms(getPaymentTerms());
		customerEntity.setPhone(getPhone());
		customerEntity.setEmail(getEmail());
		customerEntity.setAddressLine1(getAddressLine1());
		customerEntity.setAddressLine2(getAddressLine2());
		customerEntity.setCity(getCity());
		customerEntity.setState(getState());
		customerEntity.setZip(getZip());
		customerEntity.setCountry(getCountry());

		return customerEntity;
	}
}
