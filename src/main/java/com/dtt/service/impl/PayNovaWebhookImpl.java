package com.dtt.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.dtt.model.PaymentHistory;
import com.dtt.repo.PaymentHistoryRepo;
import com.dtt.service.iface.PayNovaWebhookIface;
import com.dtt.utils.AppUtils;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class PayNovaWebhookImpl implements PayNovaWebhookIface {

	private static final Logger log = LoggerFactory.getLogger(PayNovaWebhookImpl.class);

	@Autowired
	PaymentHistoryRepo paymentHistoryRepo;

	@Autowired
	LogMetricsIfaceImpl logMetricsIfaceImpl;

	@Override
	public ResponseEntity<String> handleWebhook(JsonNode payload) {
		System.out.println(" handleWebhook payload ::" + payload);
		try {
			String clientRef = payload.path("client_reference").asText(); // Transaction ID
			String paymentTransactionReference = payload.path("payment_transaction_reference").asText(); // client
																											// Transaction
																											// id
			int statusCode = payload.path("status").asInt(); // Paid=2, Failed=3 etc.
			JsonNode tx = payload.path("payment_transactions").get(0);
			String paymentRef = tx.path("payment_reference").asText();
			BigDecimal amount = tx.path("total_amount").decimalValue();

			String finalStatus = switch (statusCode) {
			case 2 -> "SUCCESS";
			case 3 -> "FAILED";
			case 4 -> "REVERSED";
			case 5 -> "IN_PROGRESS";
			default -> "UNKNOWN";
			};

			boolean alreadyProcessed = paymentHistoryRepo.existsByTransactionIdAndStatus(clientRef, finalStatus);

			if (alreadyProcessed) {
				log.warn("Duplicate webhook ignored for clientRef={}, status={}", clientRef, finalStatus);
				return ResponseEntity.ok("Already processed");
			}

			// Boolean paymentRecordExitByClientReferenceId =
			// paymentHistoryRepo.existsByTransactionId(clientRef);
			Optional<PaymentHistory> oldPaymentRecord = paymentHistoryRepo.findByTransactionId(clientRef);
			if (oldPaymentRecord.isPresent()) {
				PaymentHistory newRecord = new PaymentHistory();
				newRecord.setTransactionId(clientRef);
				newRecord.setPaymentReferenceTransactionId(paymentRef);
				newRecord.setAmount(amount);

				newRecord.setStatus(finalStatus);

				PaymentHistory p = oldPaymentRecord.get();

				newRecord.setPaymentMethod(p.getPaymentMethod());
				newRecord.setCustomerType(p.getCustomerType());
				// newRecord.setSubscriberUid(p.getSubscriberUid());
				newRecord.setEnglishName(p.getEnglishName());
				newRecord.setCreatedOn(AppUtils.getCurrentDateTime());
				newRecord.setTransactionId(clientRef);
				newRecord.setPaymentReferenceTransactionId(paymentTransactionReference);
				newRecord.setEmail(p.getEmail());
				newRecord.setMobile(p.getMobile());
				newRecord.setPaymentCategory(p.getPaymentCategory());
				newRecord.setDescription(p.getDescription());
				newRecord.setTransactionType(p.getTransactionType());
				newRecord.setOrganizationId(p.getOrganizationId());
				if (p.getPaymentInfo() != null && !p.getPaymentInfo().isEmpty()) {
					newRecord.setPaymentInfo(p.getPaymentInfo());
				}
				newRecord.setVat(p.getVat());
				newRecord.setTransactionFee(p.getTransactionFee());
				newRecord.setPlatformFee(p.getPlatformFee());
				newRecord.setStatus(finalStatus);
				newRecord.setPaymentResponse(payload.toString());
				paymentHistoryRepo.save(newRecord);

				if ("SUCCESS".equalsIgnoreCase(finalStatus)) {
					logMetricsIfaceImpl.publishMetricsUpdate(); // ✅ PUBLISH AFTER SAVE
				}

			} else {
				log.warn("Webhook received but no existing payment history found for clientRef={}", clientRef);
			}
			return ResponseEntity.ok("Webhook processed");

		} catch (Exception e) {
		//	e.printStackTrace();
			log.error("Webhook processing error", e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Webhook failed");
		}
	}

}
