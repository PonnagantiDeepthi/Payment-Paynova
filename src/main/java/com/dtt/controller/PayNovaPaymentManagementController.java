package com.dtt.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.dtt.requestdto.PaymentInquiryByClientRefRequest;
import com.dtt.requestdto.PaymentInquiryByPaymentRefRequest;
import com.dtt.requestdto.PaymentRefundRequest;
import com.dtt.responsedto.ApiResponse;
import com.dtt.service.impl.PayNovaPaymentManagementService;

/**
 * PayNovaPaymentManagementController
 *
 * Exposes REST endpoints for PayNova Payment Management: -
 * /inquiry/by-payment-reference - /inquiry/by-client-reference - /refund
 *
 * Uses PayNovaPaymentManagementService to call PayNova via PayNovaClient.
 *
 * Author: Vijay Kumar Created On: 28-Nov-2025
 */
@RestController
@RequestMapping("/paynova/api/payments")
//@RequestMapping("/api/payments")
public class PayNovaPaymentManagementController {

	@Autowired
	private PayNovaPaymentManagementService paymentManagementService;

	/**
	 * Payment Inquiry by Payment Reference
	 *
	 * Example request: { "clientIdentifier": "paynova", // optional, filled from
	 * config if null "paymentReference": "c06a0587-6877-403d-..." // required }
	 */
	@PostMapping("/inquiry/by-payment-reference")
	public ApiResponse inquiryByPaymentReference(@RequestBody PaymentInquiryByPaymentRefRequest request) {

		return paymentManagementService.inquiryByPaymentReference(request);
	}

	/**
	 * Payment Inquiry by Client Reference
	 *
	 * Example request: { "clientIdentifier": "paynova", // optional
	 * "clientReference": "032698547140" // required }
	 */
	@PostMapping("/inquiry/by-client-reference")
	public ApiResponse inquiryByClientReference(@RequestBody PaymentInquiryByClientRefRequest request) {

		return paymentManagementService.inquiryByClientReference(request);
	}

	/**
	 * Payment Refund
	 *
	 * Example request: { "clientIdentifier": "paynova", // optional
	 * "paymentReference": "c06a0587-6877-403d-b736-aa15e6dc7558", // required
	 * "clientReference": "hugefuygasfd", "clientExtra": "client_extra",
	 * "clientWebhook": "https://yourapp.com/paynova/refund-webhook", "language":
	 * "en" }
	 */
	@PostMapping("/refund")
	public ApiResponse refundPayment(@RequestBody PaymentRefundRequest request) {

		return paymentManagementService.refundPayment(request);
	}
}
