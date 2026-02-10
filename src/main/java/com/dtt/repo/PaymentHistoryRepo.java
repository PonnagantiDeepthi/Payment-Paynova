package com.dtt.repo;

import java.math.BigDecimal;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dtt.model.PaymentHistory;

@Repository
public interface PaymentHistoryRepo extends JpaRepository<PaymentHistory, Long> {

	boolean existsByTransactionIdAndStatus(String transactionId, String status);

	// 1️⃣ Find latest FINAL status per transaction (SUCCESS / FAILED / CANCELLED)
	@Query("""
			    SELECT p
			    FROM PaymentHistory p
			    WHERE p.email = :email
			      AND p.status IN ('SUCCESS', 'FAILED', 'CANCELLED')
			      AND p.createdOn = (
			          SELECT MAX(ph.createdOn)
			          FROM PaymentHistory ph
			          WHERE ph.transactionId = p.transactionId
			            AND ph.email = :email
			            AND ph.status IN ('SUCCESS', 'FAILED', 'CANCELLED')
			      )
			""")
	List<PaymentHistory> findLatestStatusByEmail(@Param("email") String email);

	// 2️⃣ Find by transactionId
	@Query("""
			    SELECT p
			    FROM PaymentHistory p
			    WHERE p.transactionId = :transactionId
			""")
	Optional<PaymentHistory> findByTransactionId(@Param("transactionId") String transactionId);

	// 3️⃣ SUCCESS / FAILED by paymentReferenceTransactionId
	@Query("""
			    SELECT p
			    FROM PaymentHistory p
			    WHERE p.paymentReferenceTransactionId = :paymentReferenceTransactionId
			      AND p.status IN ('SUCCESS', 'FAILED')
			""")
	List<PaymentHistory> findSuccessAndFailedByPaymentReferenceTransactionId(
			@Param("paymentReferenceTransactionId") String paymentReferenceTransactionId);

	// 4️⃣ INITIATED by paymentReferenceTransactionId
	@Query("""
			    SELECT p
			    FROM PaymentHistory p
			    WHERE p.paymentReferenceTransactionId = :paymentReferenceTransactionId
			      AND p.status = 'INITIATED'
			""")
	List<PaymentHistory> findInitiatePaymentRecordByReferenceTransactionId(
			@Param("paymentReferenceTransactionId") String paymentReferenceTransactionId);

	// 5️⃣ SUCCESS / FAILED by transactionId
	@Query("""
			    SELECT p
			    FROM PaymentHistory p
			    WHERE p.transactionId = :transactionId
			      AND p.status IN ('SUCCESS', 'FAILED')
			""")
	List<PaymentHistory> findSuccessAndFailedByTransactionId(@Param("transactionId") String transactionId);

	// 6️⃣ INITIATED by transactionId
	@Query("""
			    SELECT p
			    FROM PaymentHistory p
			    WHERE p.transactionId = :transactionId
			      AND p.status = 'INITIATED'
			""")
	List<PaymentHistory> findInitiatedPaymentRecordByTransactionId(@Param("transactionId") String transactionId);

	// 7️⃣ Filter with pagination (Admin / Dashboard)
//    @Query("""
//        SELECT p
//        FROM PaymentHistory p
//        WHERE (:transactionId IS NULL OR p.transactionId LIKE %:transactionId%)
//          AND (:status IS NULL OR p.status = :status)
//          AND (:transactionType IS NULL OR p.transactionType = :transactionType)
//          AND (:organizationId IS NULL OR p.organizationId = :organizationId)
//        ORDER BY p.createdOn DESC
//    """)
	@Query("""
			    SELECT p
			    FROM PaymentHistory p
			    WHERE (:transactionId IS NULL OR p.transactionId LIKE %:transactionId%)
			      AND (
			            (:status IS NULL AND p.status IN ('SUCCESS', 'FAILED'))
			            OR (:status IS NOT NULL AND p.status = :status)
			          )
			      AND (:transactionType IS NULL OR p.transactionType = :transactionType)
			      AND (:organizationId IS NULL OR p.organizationId = :organizationId)
			    ORDER BY p.id DESC
			""")
	Page<PaymentHistory> filterPayments(@Param("transactionId") String transactionId, @Param("status") String status,
			@Param("transactionType") String transactionType, @Param("organizationId") String organizationId,
			Pageable pageable);

	// 8️⃣ EXISTS by transactionId
	@Query("""
			    SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END
			    FROM PaymentHistory p
			    WHERE p.transactionId = :transactionId
			""")
	boolean existsByTransactionId(@Param("transactionId") String transactionId);

	// 9️⃣ EXISTS by paymentReferenceTransactionId
	@Query("""
			    SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END
			    FROM PaymentHistory p
			    WHERE p.paymentReferenceTransactionId = :paymentReferenceTransactionId
			""")
	boolean existsByPaymentReferenceTransactionId(
			@Param("paymentReferenceTransactionId") String paymentReferenceTransactionId);

	// 🔟 Delete by transactionId (if needed later)
	@Query("""
			    DELETE FROM PaymentHistory p
			    WHERE p.transactionId = :transactionId
			""")
	void deleteByTransactionId(@Param("transactionId") String transactionId);

	@Query("""
			    SELECT SUM(
			        COALESCE(p.vat, 0) +
			        COALESCE(p.transactionFee, 0) +
			        COALESCE(p.platformFee, 0) +
			        COALESCE(p.amount, 0)
			    )
			    FROM PaymentHistory p
			    WHERE p.status = 'SUCCESS'
			    AND p.transactionType = 'DEBIT'
			""")
	BigDecimal getAllFeeSum();

	@Query("""
			    SELECT SUM(p.vat), SUM(p.platformFee), SUM(p.transactionFee)
			    FROM PaymentHistory p
			    WHERE p.status = 'SUCCESS'
			    AND p.transactionType = 'DEBIT'
			""")
	List<Object[]> fetchSums();

	@Query("""
			    SELECT COUNT(p)
			    FROM PaymentHistory p
			    WHERE p.transactionType = 'DEBIT' AND p.status = 'SUCCESS'
			""")
	long countSuccessfulDebits();

	@Query("""
			    SELECT SUM(p.amount)
			    FROM PaymentHistory p
			    WHERE p.status = 'SUCCESS'
			    AND p.transactionType = 'DEBIT'
			    AND MONTH(p.createdOn) = MONTH(CURRENT_DATE)
			    AND YEAR(p.createdOn) = YEAR(CURRENT_DATE)
			""")
	BigDecimal sumAmountForCurrentMonth();

	@Query("""
			    SELECT SUM(p.amount)
			    FROM PaymentHistory p
			    WHERE p.status = 'SUCCESS'
			    AND p.transactionType = 'CREDIT'
			    AND MONTH(p.createdOn) = MONTH(CURRENT_DATE)
			    AND YEAR(p.createdOn) = YEAR(CURRENT_DATE)
			""")
	BigDecimal sumAmountForCurrentMonthTopup();

	@Query("""
			    SELECT SUM(p.amount)
			    FROM PaymentHistory p
			    WHERE p.transactionType = 'CREDIT'
			    AND p.status = 'SUCCESS'
			""")
	BigDecimal sumCreditAmount();

	@Query("""
			    SELECT COUNT(DISTINCT p.organizationId)
			    FROM PaymentHistory p
			    WHERE p.customerType = 'SERVICE_PROVIDER'
			""")
	long countUniqueServiceProviders();

	@Query("""
			    SELECT
			        CAST(p.createdOn AS date) as trendDate,
			        SUM(CASE WHEN p.transactionType = 'DEBIT' AND p.status = 'SUCCESS' THEN p.amount ELSE 0 END) as successAmount,
			        SUM(CASE WHEN p.status = 'FAILED' THEN p.amount ELSE 0 END) as failedAmount
			    FROM PaymentHistory p
			    WHERE p.createdOn >= :startDate
			    GROUP BY CAST(p.createdOn AS date)
			    ORDER BY trendDate ASC
			""")
	List<Object[]> findSevenDayTrends(@Param("startDate") Instant startDate);

//	@Query("""
//			    SELECT
//			        YEAR(p.createdOn) as y,
//			        MONTH(p.createdOn) as m,
//			        SUM(COALESCE(p.transactionFee, 0)),
//			        SUM(COALESCE(p.platformFee, 0)),
//			        SUM(COALESCE(p.vat, 0)),
//			        SUM(COALESCE(p.transactionFee, 0) + COALESCE(p.platformFee, 0) + COALESCE(p.vat, 0))
//			    FROM PaymentHistory p
//			    WHERE p.createdOn >= :startDate AND p.status = 'SUCCESS'
//			    GROUP BY YEAR(p.createdOn), MONTH(p.createdOn)
//			    ORDER BY y DESC, m DESC
//			""")
//	List<Object[]> findMonthlyBreakdown(@Param("startDate") Instant startDate);

	@Query("""
			    SELECT
			        YEAR(p.createdOn) as y,
			        MONTH(p.createdOn) as m,
			        SUM(COALESCE(p.transactionFee, 0)),
			        SUM(COALESCE(p.platformFee, 0)),
			        SUM(COALESCE(p.vat, 0)),
			        SUM(
			            COALESCE(p.transactionFee, 0) +
			            COALESCE(p.platformFee, 0) +
			            COALESCE(p.vat, 0)
			        ),
			        SUM(
			            CASE
			                WHEN p.transactionType = 'DEBIT'
			                THEN COALESCE(p.amount, 0)
			                ELSE 0
			            END
			        ),
			        SUM(
			            CASE
			                WHEN p.transactionType = 'CREDIT'
			                THEN COALESCE(p.amount, 0)
			                ELSE 0
			            END
			        )
			    FROM PaymentHistory p
			    WHERE p.createdOn >= :startDate
			      AND p.status = 'SUCCESS'
			    GROUP BY YEAR(p.createdOn), MONTH(p.createdOn)
			    ORDER BY y ASC, m ASC
			""")
	List<Object[]> findMonthlyBreakdown(@Param("startDate") Instant startDate);

	@Query("""
			    SELECT
			        p.organizationId,
			        SUM(CASE WHEN p.transactionType = 'CREDIT' AND p.status = 'SUCCESS' THEN p.amount ELSE 0 END) as walletTopup,
			        COUNT(p.id) as transactionCount
			    FROM PaymentHistory p
			    WHERE p.customerType = 'SERVICE_PROVIDER'
			    GROUP BY p.organizationId
			    ORDER BY walletTopup DESC
			""")
	List<Object[]> findTopProviders();

	@Query("""
			    SELECT
			        SUM(CASE WHEN p.status = 'INITIATED' THEN 1 ELSE 0 END),
			        SUM(CASE WHEN p.status = 'SUCCESS' AND p.transactionType = 'CREDIT' THEN 1 ELSE 0 END),
			        SUM(CASE WHEN p.status = 'SUCCESS' AND p.transactionType = 'DEBIT' THEN 1 ELSE 0 END)
			    FROM PaymentHistory p
			""")
	List<Object[]> getTransactionCounts();

}
