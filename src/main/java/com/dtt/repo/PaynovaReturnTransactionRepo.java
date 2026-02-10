package com.dtt.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dtt.model.PaynovaReturnTransaction;

@Repository
public interface PaynovaReturnTransactionRepo extends JpaRepository<PaynovaReturnTransaction, Long> {

	@Query("""
			    SELECT p
			    FROM PaynovaReturnTransaction p
			    WHERE p.paymentTransactionReference = :paymentTransactionReference
			""")
	Optional<PaynovaReturnTransaction> findByPaymentTransactionReference(
			@Param("paymentTransactionReference") String paymentTransactionReference);
}
