package com.erp.core.role.permission;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RolePermissionRepository extends CrudRepository<RolePermissionEntity, RolePermissionEntityPK> {
		
	@Query("Select rp from RolePermissionEntity rp "
			+"WHERE (:#{#roleId == null} = true or rp.roleId in (:roleId) )"
			+"AND (rp.deleted is null or rp.deleted = false)")	
	List<RolePermissionEntity> findByRoleIdAndDeletedFalse(@Param("roleId") List<Long> roleId);
	
	@Query("Select rp from RolePermissionEntity rp "
			+"WHERE (rp.roleId = :roleId) "
			+"AND (rp.permissionId = :permissionId) "
			+"AND (rp.deleted is NULL OR rp.deleted = false)")
	RolePermissionEntity findByRoleIdAndPermissionIdAndDeletedFalse(@Param("roleId") Long roleId,@Param("permissionId") Long permissionId);
}
