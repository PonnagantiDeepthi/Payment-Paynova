package com.dtt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dtt.model.CustomerEnrollment;

@Repository
public interface CustomerEnrollmentRepository extends JpaRepository<CustomerEnrollment, Long> {

	// 1. Find by customerCode
	@Query("SELECT c FROM CustomerEnrollment c WHERE c.customerCode = :customerCode")
	CustomerEnrollment findByCustomerCode(@Param("customerCode") String customerCode);

	// 2. Find by identificationNumber
	@Query("SELECT c FROM CustomerEnrollment c WHERE c.identificationNumber = :identificationNumber")
	CustomerEnrollment findByIdentificationNumber(@Param("identificationNumber") String identificationNumber);

	// 3. Find by email
	@Query("SELECT c FROM CustomerEnrollment c WHERE c.email = :email")
	CustomerEnrollment findByEmail(@Param("email") String email);

	// 4. Find by email OR customerCode OR identificationNumber
	@Query("""
			SELECT c FROM CustomerEnrollment c
			WHERE c.email = :email
			   OR c.customerCode = :customerCode
			   OR c.identificationNumber = :identifier
			""")
	List<CustomerEnrollment> findByEmailOrCustomerCodeOrIdentificationNumber(@Param("email") String email,
			@Param("customerCode") String customerCode, @Param("identifier") String identifier);
}
