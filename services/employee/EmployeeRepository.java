package com.erp.services.employee;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Long> {
	@Query("SELECT ee FROM EmployeeEntity ee WHERE ee.userId = :id AND ee.status != 3")
	EmployeeEntity findByUserId(Long id);

	@Query("SELECT ee FROM EmployeeEntity ee WHERE ee.id = :id AND ee.deleted = :deleted")
	EmployeeEntity findByIdAndDeletedFlag(Long id, boolean deleted);

	@Query("SELECT ee FROM EmployeeEntity ee WHERE (ee.deleted is NULL OR ee.deleted = :deleted) ORDER BY ee.employeeNumber DESC")
	List<EmployeeEntity> findAllByDeletedFlag(boolean deleted);


	@Query("SELECT ee FROM FROM EmployeeEntity ee WHERE (:#{#reportingToEmployeeList == null} = true or ee.reportingManager in (:reportingToEmployeeList)) ORDER BY ee.employeeNumber DESC")
	List<EmployeeEntity> getEmployeeForReportingEmployeesList(
			@Param("reportingToEmployeeList") List<Long> reportingToEmployeeList);
	
	List<EmployeeEntity> getByEmail(String email);

	List<EmployeeEntity> getByApproverTrueAndOrgIdOrderByEmployeeNumberDesc(Long orgId);
	
	List<EmployeeEntity> getByDeletedFalseAndOrgIdOrderByEmployeeNumberDesc(Long orgId);
	
	List<EmployeeEntity> getByIdOrReportingManagerAndDeletedFalseOrderByEmployeeNumberDesc(Long id, Long reportingManager);
	
	List<EmployeeEntity> findByEmployeeNumber(Integer employeeNumber);
	
	@Query(value = "select max(employee_number) from employee where type = :employmentType", nativeQuery = true)
	Integer latestEmployeeNumberByEmploymentType(@Param("employmentType") Integer employmentType);
	
	@Query("SELECT ee FROM EmployeeEntity ee WHERE (:approverId = null OR ee.reportingManager = :approverId) ORDER BY ee.employeeNumber DESC")
	List<EmployeeEntity> getEmployeeList(@Param("approverId") Long approverId);
	
	@Query("SELECT ee FROM EmployeeEntity ee WHERE ee.orgId = :orgId "//ee.reportingManager = :reportingManager "
			+ "AND ee.type = :type "
			+ "AND ee.internshipEndDate IS NOT NULL "
			+ "AND ee.internshipEndDate <= :internshipEndDate "
			+ "AND ee.endDate IS NULL "
			+ "AND ee.deleted = FALSE ")
//	List<EmployeeEntity> getInternList(@Param("reportingManager") Long reportingManager,
	List<EmployeeEntity> getInternList(@Param("orgId") Long orgId, @Param("type") Integer type, @Param("internshipEndDate") LocalDate internshipEndDate);
}
