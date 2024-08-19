package com.erp.core.org;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepository extends CrudRepository<OrganizationEntity, Long> {

	@Query("SELECT oe FROM OrganizationEntity oe WHERE oe.deleted = :deleted")
	List<OrganizationEntity> findAllByDeletedFlag(boolean deleted);

	@Query("SELECT oe FROM OrganizationEntity oe WHERE oe.name = :name AND oe.deleted = :deleted")
	Optional<OrganizationEntity> findByOrganizationNameAndDeletedFlag(String name, boolean deleted);

	@Query("SELECT oe FROM OrganizationEntity oe WHERE oe.id = :id AND oe.deleted = :deleted")
	Optional<OrganizationEntity> findByOrganizationIdAndDeletedFlag(Long id, boolean deleted);

}
