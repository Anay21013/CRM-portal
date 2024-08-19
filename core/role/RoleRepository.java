package com.erp.core.role;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
	
	@Query("Select re from RoleEntity re where (re.name = :name) AND (re.deleted is NULL OR re.deleted = false)")
	Optional<RoleEntity> findByNameAndDeletedFalse(@Param("name") String name);
	
	@Query("Select re from RoleEntity re where (re.id = :id) AND (re.deleted is NULL OR re.deleted = false)")
	Optional<RoleEntity> findByIdAndDeletedFalse(@Param("id") Long id);
	
	@Query("Select re from RoleEntity re where re.deleted = false")
	List<RoleEntity> findAllByDeletedFalse();
	
	@Query("SELECT r FROM FROM RoleEntity r WHERE (:#{#parentRole == null} = true or r.parentRole in (:parentRole))")
	List<RoleEntity> getRoleForParentRoleList(@Param("parentRole") List<Long> parentRole);

}
