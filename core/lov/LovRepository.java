package com.erp.core.lov;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LovRepository extends CrudRepository<LovEntity, LovPK> {

	@Query("SELECT d FROM LovEntity d WHERE (d.deleted = false) ")
	List<LovEntity> findAll();

	@Query("SELECT d FROM LovEntity d WHERE (d.deleted = false)")
	List<LovEntity> findAllUiEnabled();

	@Query("SELECT d FROM LovEntity d WHERE (d.deleted = false) AND (:#{#ids == null} = true or  d.id in (:ids))")
	List<LovEntity> findLovByIds(@Param("ids") List<LovPK> ids);

	@Query("SELECT d FROM LovEntity d WHERE (d.deleted = false) AND (d.id = (:id))")
	LovEntity findLovById(@Param("id") LovPK id);

	LovEntity findByIdTypeAndIdCodeAndDeletedFalse(String type, Integer code);

	List<LovEntity> findById_TypeOrderById_CodeDesc(String type);
}
