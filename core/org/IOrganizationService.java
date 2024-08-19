package com.erp.core.org;

import java.util.List;

public interface IOrganizationService {

	Organization addOrganization(Organization organization);

	Organization getOrganizationById(Long orgId);
	
	Organization getOrganizationByName(String name);
	
	Organization updateOrganization(Organization organization);

	List<Organization> getAllOrganizations();
	
	List<Long> getAllOrganizationIds();

}
