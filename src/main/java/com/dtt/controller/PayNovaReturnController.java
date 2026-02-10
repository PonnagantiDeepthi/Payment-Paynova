/**
 * @author (Vijay kumar)
 */
package com.dtt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dtt.service.iface.PayNovaReturnIface;

/**
 * The Class PayNovaReturnController.
 * 
 * Handle return from Paynova
 */

@RestController
@RequestMapping("/paynova")
public class PayNovaReturnController {

	// private static final Logger log =
	// LoggerFactory.getLogger(PayNovaReturnController.class);
	// private static final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	PayNovaReturnIface payNovaReturnIface;

	@PostMapping(value = "/return", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> handleReturn(
			@RequestParam("PaymentTransactionSummary") String paymentTransactionSummary) {
		return payNovaReturnIface.handleReturn(paymentTransactionSummary);
	}

//	@PostMapping(value = "/returnOLD", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<ObjectNode> handleReturnOLD(@RequestParam("PaymentTransactionSummary") String encryptedData) {
//		try {
//			log.info("Encrypted PaymentTransactionSummary received: {}", encryptedData);
//
//			// 1️⃣ Decrypt AES Base64
//			String decryptedJson = AesDecryptor.decryptBase64(encryptedData, encryptionKey);
//			log.info("Decrypted PayNova response: {}", decryptedJson);
//
//			// 2️⃣ Parse JSON
//			JsonNode jsonNode = mapper.readTree(decryptedJson);
//			String clientReference = jsonNode.path("client_reference").asText();
//			String paymentReference = jsonNode.path("payment_reference").asText(null);
//			String status = jsonNode.path("status").asText("UNKNOWN");
//			double amount = jsonNode.path("amount").asDouble(0.0);
//
//			// 3️⃣ Update DB (mock)
//			PaymentRecord record = paymentDb.get(clientReference);
//			if (record != null) {
//				record.setPaymentReference(paymentReference);
//				record.setStatus(status);
//				record.setAmount(amount);
//				record.setUpdatedAt(LocalDateTime.now());
//				log.info("Payment record updated: {}", record);
//			} else {
//				log.warn("No existing payment record found for clientReference={}", clientReference);
//				record = new PaymentRecord(clientReference, paymentReference, status, amount, LocalDateTime.now());
//				paymentDb.put(clientReference, record);
//			}
//
//			// 4️ Build JSON response
//			ObjectNode response = mapper.createObjectNode();
//			response.put("client_reference", clientReference);
//			response.put("payment_reference", paymentReference);
//			response.put("status", status);
//			response.put("amount", amount);
//
//			switch (status.toUpperCase()) {
//			case "SUCCESS":
//				response.put("message", "Payment processed successfully");
//				return ResponseEntity.ok(response);
//			case "CANCELLED":
//				response.put("message", "Payment was cancelled by the user");
//				return ResponseEntity.status(HttpStatus.OK).body(response);
//			default:
//				response.put("message", "Payment failed or unknown status");
//				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//			}
//
//		} catch (Exception e) {
//			log.error("Failed to decrypt or process PayNova PaymentTransactionSummary", e);
//			ObjectNode error = mapper.createObjectNode();
//			error.put("status", "ERROR");
//			error.put("message", "Invalid or corrupted payment data");
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
//		}
//	}

}
