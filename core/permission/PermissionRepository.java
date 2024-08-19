package com.erp.core.permission;
 
import java.util.List;

import org.springframework.data.repository.CrudRepository;
 
public interface PermissionRepository extends CrudRepository<PermissionEntity, Long> {
 
	List<PermissionEntity> getByDeletedFalse();

}