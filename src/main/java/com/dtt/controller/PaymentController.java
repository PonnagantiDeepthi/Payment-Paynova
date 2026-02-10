/**
 * PaymentController
 *
 * This controller manages all payment-related operations in the system.
 * It exposes endpoints to:
 *  - Initiate a new payment
 *  - Retrieve a user's latest payment transactions
 *  - Delete all payment history records associated with a user
 *
 * Each endpoint delegates business logic to the PaymentService and returns
 * a standardized ApiResponse wrapper.
 *
 * Author: Vijay Kumar
 * Created On: 14-Nov-2025
 * Description: REST controller for handling payment processing and user payment history.
 */
package com.dtt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtt.constant.EndPointURL;
import com.dtt.requestdto.PaymentHistoryFilterRequest;
import com.dtt.requestdto.PaymentRequestDto;
import com.dtt.responsedto.ApiResponse;
import com.dtt.service.iface.PaymentServiceIface;

@RestController
@RequestMapping("/paynova")
public class PaymentController {

	@Autowired
	PaymentServiceIface paymentService;

	/**
	 * Initiates a payment request using the provided payment details.
	 *
	 * @param paymentRequestDto DTO containing payment amount, user info, and
	 *                          transaction data.
	 * @return ApiResponse<PaymentInitiateResponseDto> containing PayNova initiation
	 *         response.
	 */
	@PostMapping(EndPointURL.MAKE_PAYMENT)
	public ApiResponse makePayment(@RequestBody PaymentRequestDto paymentRequestDto) {
		return paymentService.makePayment(paymentRequestDto);
	}

	/**
	 * Fetches the latest payment history for a given user.
	 *
	 * @param userId The ID of the user whose payment history is requested.
	 * @return ApiResponse<List<PaymentHistory>> containing the user's recent
	 *         transactions.
	 */
	@GetMapping(EndPointURL.GET_PAYMENT_HISTORY)
	public ApiResponse getLatestPaymentsRecord(@PathVariable("userId") String userId) {
		return paymentService.getLatestTransactionsByUser(userId);
	}

	@GetMapping(EndPointURL.GET_PAYMENT_RECORD_BY_REFERENCE_TRANSACTION_ID)
	public ApiResponse getPaymentRecordByPaymentReferenceTransactionId(
			@PathVariable("paymentReference") String paymentReference) {
		return paymentService.getPaymentRecordByPaymentReferenceTransactionId(paymentReference);
	}

	@GetMapping(EndPointURL.GET_PAYMENT_RECORD_BY_TRANSACTION_ID)
	public ApiResponse getPaymentRecordByTransactionId(@PathVariable("transactionId") String transactionId) {
		return paymentService.getPaymentRecordByTransactionId(transactionId);
	}

//	@PostMapping(EndPointURL.SEARCH_PAYMENT_HISTORY_FILTER)
//	public ApiResponses<Page<PaymentHistory>> search(@RequestBody PaymentHistoryFilterRequest request) {
//		return paymentService.getPayments(request);
//	}
	
	@PostMapping(EndPointURL.SEARCH_PAYMENT_HISTORY_FILTER)
	public ApiResponse search(@RequestBody PaymentHistoryFilterRequest request) {
		return paymentService.getPayments(request);
	}

}
