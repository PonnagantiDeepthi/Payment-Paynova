/**
 * PayNovaWebhookController
 *
 * This controller handles PayNova's webhook (callback) notifications for payment
 * transactions. It validates the incoming webhook using HMAC verification,
 * extracts transaction details, updates the internal payment history records,
 * and responds back with appropriate HTTP status codes.
 *
 * Responsibilities:
 *  - Validate PayNova webhook payloads using HMAC SHA-256
 *  - Support mock/test mode without signature validation
 *  - Parse and extract payment transaction details
 *  - Update or insert payment history records
 *  - Provide a simple status endpoint for monitoring webhook availability
 *
 * Author: Vijay Kumar
 * Created On: 13-Nov-2025
 * Description: REST controller for processing PayNova payment callback notifications.
 */

package com.dtt.controller;
import com.dtt.repo.PaymentHistoryRepo;
import com.dtt.service.iface.PayNovaWebhookIface;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paynova")
public class PayNovaWebhookController {

	private static final Logger log = LoggerFactory.getLogger(PayNovaWebhookController.class);

	@Value("${paynova.signature-key}")
	private String signatureKey;

	@Value("${paynova.mockapi}")
	private boolean isMock;

	// Shared mock DB for demonstration
	//private static final Map<String, PaymentRecord> paymentDb = new ConcurrentHashMap<>();

	@Autowired
	PaymentHistoryRepo paymentHistoryRepo;
	
	@Autowired
	PayNovaWebhookIface novaWebhookIface;

	/**
	 * Health check endpoint to verify that the PayNova webhook callback controller
	 * is running and reachable.
	 *
	 * @return A simple text message confirming service availability.
	 */
	@GetMapping("/callback/status")
	public String getStatus() {
		return "Payment Callback service running";
	}

	/**
	 * Handle the incoming PayNova webhook callback for payment notification.
	 *
	 * Workflow: 1. Log the incoming webhook payload. 2. Extract key fields such as
	 * payment reference, client reference, instrument name, and transaction
	 * details. 3. Validate webhook authenticity via HMAC signature (unless in mock
	 * mode). 4. Look up existing payment history using client reference. 5. Insert
	 * or update payment history records with the latest status. 6. Return HTTP 200
	 * on success or appropriate error response on failure.
	 *
	 * @param payload The incoming JSON webhook payload sent by PayNova.
	 * @return ResponseEntity<String> indicating success or failure.
	 */
	
	@PostMapping("/webhook")
	public ResponseEntity<String> handleWebhook(@RequestBody JsonNode payload) {
		try {
			return novaWebhookIface.handleWebhook(payload);
		} catch (Exception ex) {
			log.error("Webhook processing error", ex);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Webhook failed");
		}
	}
	
	
}
