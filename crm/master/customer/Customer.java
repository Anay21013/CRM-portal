package com.erp.crm.master.customer;

import java.io.Serializable;
import java.util.List;

import com.erp.core.base.BaseDTO;
import com.erp.crm.master.customer.contract.Contract;

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
public class Customer extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long masterCustomer;
	private Long orgId;
	private String name;
	private String phone;
	private String email;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zip;
	private String country;
	private List<Contract> contracts;
	private String address;
	

	public Customer(CustomerEntity customerEntity) {
		this.id = customerEntity.getId();
		this.orgId = customerEntity.getOrgId();
		this.masterCustomer = customerEntity.getMasterCustomer();
		this.name = customerEntity.getName();
		this.phone = customerEntity.getPhone();
		this.email = customerEntity.getEmail();
		this.addressLine1 = customerEntity.getAddressLine1();
		this.addressLine2 = customerEntity.getAddressLine2();
		this.city = customerEntity.getCity();
		this.state = customerEntity.getState();
		this.zip = customerEntity.getZip();
		this.country = customerEntity.getCountry();

		this.address = (customerEntity.getAddressLine1() != null ? customerEntity.getAddressLine1() : "") 
				+ (customerEntity.getAddressLine2() != null ? ", " + customerEntity.getAddressLine2() : "") 
				+ (customerEntity.getCity() != null ? ", " + customerEntity.getCity() : "")
				+ (customerEntity.getState() != null ? ", " + customerEntity.getState() : "") 
				+ (customerEntity.getCountry() != null ? ", " + customerEntity.getCountry() : "");
		
		super.populateBaseDTO(customerEntity);
	}

	public CustomerEntity populateCustomerEntity() {
		CustomerEntity customerEntity = new CustomerEntity();
		super.populateBaseEntity(customerEntity);

		customerEntity.setId(getId());
		customerEntity.setOrgId(getOrgId());
		customerEntity.setMasterCustomer(getMasterCustomer());
		customerEntity.setName(getName());
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
