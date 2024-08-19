package com.erp.core.org;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Transactional
public class OrganizationService implements IOrganizationService {

	@Autowired
	private OrganizationRepository organizationRepository;

	@Override
	public Organization addOrganization(Organization organization) {
		log.debug("Add Organization with id: {}", organization.getId());
		return new Organization(organizationRepository.save(organization.populateOrganizationEntity()));
	}

	@Override
	public Organization getOrganizationById(Long orgId) {
		log.debug("Fetch Organization details with organization {} ", orgId);
		OrganizationEntity orgEntity = organizationRepository.findById(orgId)
				.orElseThrow(() -> new RuntimeException("Error: Organization not found for given id - " + orgId));

		return new Organization(orgEntity);
	}

	@Override
	public Organization getOrganizationByName(String name) {
		log.debug("Fetch Organization with name: " + name);
		OrganizationEntity orgEntity = organizationRepository.findByOrganizationNameAndDeletedFlag(name, false)
				.orElseThrow(() -> new RuntimeException("Error: Organization not found for given name - " + name));

		return new Organization(orgEntity);
	}

	@Override
	public List<Organization> getAllOrganizations() {
		log.debug("Fetch Organization list.");
		List<Organization> orgList = new ArrayList<>();
		organizationRepository.findAllByDeletedFlag(false).forEach(org -> orgList.add(new Organization(org)));
		return orgList;
	}

	@Override
	public Organization updateOrganization(Organization organization) {
		log.debug("Update Organization with id: " + organization.getId());
		return new Organization(organizationRepository.save(organization.populateOrganizationEntity()));
	}

	@Override
	public List<Long> getAllOrganizationIds() {
		log.debug("Fetch Organization id list.");
		List<Long> orgIdList = new ArrayList<>();
		organizationRepository.findAllByDeletedFlag(false).forEach(org -> orgIdList.add(org.getId()));
		return orgIdList;
	}

}
