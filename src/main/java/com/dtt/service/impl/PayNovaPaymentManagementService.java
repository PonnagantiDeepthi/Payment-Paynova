package com.dtt.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import com.dtt.requestdto.PaymentInquiryByClientRefRequest;
import com.dtt.requestdto.PaymentInquiryByPaymentRefRequest;
import com.dtt.requestdto.PaymentRefundRequest;
import com.dtt.responsedto.ApiResponse;
import com.dtt.responsedto.PaymentInquiryResponseDto;
import com.dtt.responsedto.PaymentRefundResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PayNovaPaymentManagementService {

	private static final Logger log = LoggerFactory.getLogger(PayNovaPaymentManagementService.class);

	private final PayNovaClient payNovaClient;
	private final String clientIdentifier;

	@Autowired
	private ObjectMapper objectMapper;

	public PayNovaPaymentManagementService(PayNovaClient payNovaClient,
			@Value("${paynova.client-identifier}") String clientIdentifier) {
		this.payNovaClient = payNovaClient;
		this.clientIdentifier = clientIdentifier;
	}

	public ApiResponse inquiryByPaymentReference(PaymentInquiryByPaymentRefRequest request) {

		try {
			log.info("PayNova InquiryByPaymentReference initiated. Request: {}", request);

			if (request.getClientIdentifier() == null || request.getClientIdentifier().isEmpty()) {
				request.setClientIdentifier(clientIdentifier);
			}

			String signingString = request.getClientIdentifier() + request.getPaymentReference();
			String path = "/api/payment-management/inquiry/by-transaction-reference";

			log.info("Signing String: {}", signingString);

			ApiResponse resp = payNovaClient.initiatePayment(path, request, PaymentInquiryResponseDto.class,
					signingString);

			log.info("inquiryByPaymentReference resp ::" + resp);

			PaymentInquiryResponseDto data = objectMapper.convertValue(resp.getResult(),
					PaymentInquiryResponseDto.class);

			String status = (data != null && data.getResponseCode() != null && data.getResponseCode() == 0) ? "SUCCESS"
					: "FAILED";

			log.info("PayNova InquiryByPaymentReference completed. Status: {}", status);
			if (resp.isSuccess()) {
				ApiResponse apiResponse = new ApiResponse(true, "Success", data);
				return apiResponse;
			} else {
				ApiResponse apiResponse = new ApiResponse(false, resp.getMessage(), resp.getResult());
				return apiResponse;
			}

		} catch (Exception e) {
			//e.printStackTrace();
			log.error("Error in inquiryByPaymentReference: {}", e.getMessage(), e);
			ApiResponse error = new ApiResponse(false, "PayNova inquiry by payment reference failed: " + e.getMessage(),
					null);
			return error;
		}
	}

	public ApiResponse inquiryByClientReference(PaymentInquiryByClientRefRequest request) {

		try {
			log.info("PayNova InquiryByClientReference initiated. Request: {}", request);

			if (request.getClientIdentifier() == null || request.getClientIdentifier().isEmpty()) {
				request.setClientIdentifier(clientIdentifier);
			}

			String signingString = request.getClientIdentifier() + request.getClientReference();
			String path = "/api/payment-management/inquiry/by-client-reference";

			log.info("Signing String: {}", signingString);

			ApiResponse resp = payNovaClient.initiatePayment(path, request, PaymentInquiryResponseDto.class,
					signingString);
			log.info("inquiryByClientReference resp ::" + resp);

			PaymentInquiryResponseDto data = objectMapper.convertValue(resp.getResult(),
					PaymentInquiryResponseDto.class);

			String status = (data != null && data.getResponseCode() != null && data.getResponseCode() == 0) ? "SUCCESS"
					: "FAILED";

			log.info("PayNova InquiryByClientReference completed. Status: {}", status);
			if (resp.isSuccess()) {
				ApiResponse apiResponse = new ApiResponse(true, "Success", resp.getResult());
				return apiResponse;
			} else {
				ApiResponse apiResponse = new ApiResponse(false, resp.getMessage(), resp.getResult());
				return apiResponse;
			}

		} catch (Exception e) {
			
			log.info("Error in inquiryByClientReference: {}", e.getMessage(), e);
			ApiResponse error = new ApiResponse(false, "PayNova inquiry by client reference failed: " + e.getMessage(),
					null);
			return error;
		}
	}

	public ApiResponse refundPayment(PaymentRefundRequest request) {

		try {
			log.info("PayNova Refund API initiated. Request: {}", request);

			if (request.getClientIdentifier() == null || request.getClientIdentifier().isEmpty()) {
				request.setClientIdentifier(clientIdentifier);
			}

			String signingString = request.getClientIdentifier() + request.getPaymentReference();
			String path = "/api/payment-management/refund";

			log.debug("Signing String: {}", signingString);
			ApiResponse resp = payNovaClient.initiatePayment(path, request, PaymentRefundResponseDto.class,
					signingString);

			return resp;

		} catch (Exception e) {
			log.error("Error in refundPayment: {}", e.getMessage(), e);
			return new ApiResponse(false, "PayNova refund failed: " + e.getMessage(), null);
		}
	}
}
