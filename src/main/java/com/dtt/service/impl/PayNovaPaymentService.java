package com.dtt.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dtt.requestdto.Customer;
import com.dtt.requestdto.PayNovaPaymentRequestDto;
import com.dtt.responsedto.ApiResponse;
import com.dtt.responsedto.PaymentInitiateResponseDto;
import com.dtt.service.iface.PayNovaPaymentIface;

@Service
public class PayNovaPaymentService implements PayNovaPaymentIface {

	private final PayNovaClient payNovaClient;
	private final String clientIdentifier;
	private final boolean mockapi;

	private final RestTemplate restTemplate;
	private final String baseUrl;
	private final String customerCode;

	private final double payNovaCharge;

	public PayNovaPaymentService(PayNovaClient payNovaClient,
			@Value("${paynova.client-identifier:mock-client}") String clientIdentifier,
			@Value("${paynova.mockapi}") boolean mockapi, RestTemplate restTemplate,
			@Value("${paynova.base-url}") String baseUrl, @Value("${paynova.charge}") double payNovaCharge,
			@Value("${paynova.customerCode}") String customerCode) {
		this.payNovaClient = payNovaClient;
		this.clientIdentifier = clientIdentifier;
		this.mockapi = mockapi;
		this.restTemplate = restTemplate;
		this.baseUrl = baseUrl;
		this.payNovaCharge = payNovaCharge;
		this.customerCode = customerCode;
	}

	@Override
	public ApiResponse initiateCardPayment(PayNovaPaymentRequestDto dto) {
		dto.setClientIdentifier(clientIdentifier);
		// 🔹 Real API call
		try {
			String signingString = dto.getClientIdentifier() + dto.getClientReference() + "Card";
			System.out.println(" initiate payment dto ::" + dto + "\n" + dto.getPayments());
			System.out.println(
					" signingString ::" + signingString + "  signingString hash ::" + signingString.hashCode());
			return payNovaClient.initiatePayment("/api/card/purchase/initiate", dto, PaymentInitiateResponseDto.class,
					signingString);
		} catch (Exception e) {
			ApiResponse error = new ApiResponse(false, "Failed to initiate payment: " + e.getMessage(), null);
			return error;
		}
	}

	// 5) Wallet Purchase Initiate (use same signing as docs: client_identifier +
	// client_reference + "Wallet")
	@Override
	public ApiResponse initiatePayToWallet(PayNovaPaymentRequestDto request) {
		String signingString = clientIdentifier + request.getClientReference() + "Card";
		;
		request.setClientIdentifier(clientIdentifier);
		Customer customer = new Customer();
		customer.setCustomerCode(customerCode);
		request.setCustomer(customer);

		String path = "/api/wallet/purchase/initiate";
		ApiResponse resp = payNovaClient.initiatePayment(path, request, PaymentInitiateResponseDto.class,
				signingString);
		return resp;
	}

	@Override
	public ApiResponse initiateCardToMerchantWalletTopUp(PayNovaPaymentRequestDto request) {
		request.setClientIdentifier(clientIdentifier);
		// 🔹 Real API call
		try {
			String signingString = request.getClientIdentifier() + request.getClientReference() + "Card";
			System.out.println(" initiate payment dto ::" + request + " \n " + request.getPayments());
			System.out.println(
					" signingString ::" + signingString + "  signingString hash ::" + signingString.hashCode());
			return payNovaClient.initiatePayment("/api/card/wallet/payment", request, PaymentInitiateResponseDto.class,
					signingString);
		} catch (Exception e) {
			ApiResponse error = new ApiResponse(false, "Failed to initiate payment: " + e.getMessage(), null);
			return error;
		}
	}

}
