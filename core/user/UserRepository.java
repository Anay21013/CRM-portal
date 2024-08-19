package com.erp.core.user;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

	@Query("SELECT ue FROM UserEntity ue WHERE ue.email = :email AND ue.deleted = :deleted")
	UserEntity findUserByEmail(String email, boolean deleted);

	@Query("SELECT ue FROM UserEntity ue" + " WHERE ue.deleted is NULL OR ue.deleted = false")
	List<UserEntity> getNonDeletedUsers();

	List<UserEntity> getByRoleId(Long roleId);

	UserEntity findByIdAndDeletedFalse(Long id);
	
	List<UserEntity> findByUserName(String userName);
}
