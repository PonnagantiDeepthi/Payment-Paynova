package com.dtt.service.impl;

import com.dtt.repo.CustomerEnrollmentRepository;
import com.dtt.requestdto.*;
import com.dtt.service.iface.PayNovaWalletServiceIface;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dtt.responsedto.ApiResponse;
import com.dtt.responsedto.CustomerWalletBalanceResponse;
import com.dtt.responsedto.PaymentInitiateResponseDto;
import com.dtt.responsedto.PurchaseConfirmResponseDto;
import com.dtt.responsedto.WalletBalanceResponse;
import com.dtt.responsedto.WalletCreateResponse;
import com.dtt.responsedto.WalletModifyResponse;
import com.dtt.model.CustomerEnrollment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PayNovaWalletService implements PayNovaWalletServiceIface {

	private static final Logger log = LoggerFactory.getLogger(PayNovaWalletService.class);

	private final PayNovaClient payNovaClient;
	private final String clientIdentifier;

	@Value("${paynova.base-url}")
	private String baseUrl;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	public PayNovaWalletService(PayNovaClient payNovaClient,
			@Value("${paynova.client-identifier}") String clientIdentifier) {
		this.payNovaClient = payNovaClient;
		this.clientIdentifier = clientIdentifier;
	}

	@Autowired
	CustomerEnrollmentRepository customerEnrollmentRepository;

	// 1) Create Wallet
	@Override
	public ApiResponse createWallet(WalletCreateRequest request) {
		String signingString = request.getClientIdentifier() + request.getCustomerCode();
		String path = "/api/wallet-management/wallet/create";
		ApiResponse resp = payNovaClient.initiatePayment(path, request, WalletCreateResponse.class, signingString);
		return resp;
	}

	// 2) Balance Inquiry
	@Override
	public ApiResponse getWalletBalance(WalletBalanceRequest request) {
		String signingString = request.getClientIdentifier() + request.getCustomerCode() + request.getWalletNumber();
		request.setClientIdentifier(clientIdentifier);
		String path = "/api/wallet-management/balance/inquiry";
		ApiResponse resp = payNovaClient.initiatePayment(path, request, WalletBalanceResponse.class, signingString);
		return resp;
	}

	// 3) Modify Wallet
	@Override
	public ApiResponse modifyWallet(WalletModifyRequest request) {
		String signingString = request.getClientIdentifier() + request.getCustomerCode() + request.getWalletNumber();
		String path = "/api/wallet-management/wallet/update";
		ApiResponse resp = payNovaClient.initiatePayment(path, request, WalletModifyResponse.class, signingString);

		return resp;
	}

	// 4) Card Wallet Top-up
	@Override
	public ApiResponse initiateCardWalletTopUp(CardWalletTopUpRequest request) {
		try {
			String signingString = clientIdentifier + request.getCustomerCode() + request.getClientReference()
					+ request.getWalletNumber();
			String path = "/api/card/wallet/topup";
			request.setClientIdentifier(clientIdentifier);
			ApiResponse resp = payNovaClient.initiatePayment(path, request, PaymentInitiateResponseDto.class,
					signingString);

			return resp;
		} catch (Exception e) {
			ApiResponse error = new ApiResponse(false, "Failed to initiate payment: " + e.getMessage(), null);
			return error;
		}
	}

	// 5) Wallet Purchase Initiate (use same signing as docs: client_identifier +
	// client_reference + "Wallet")
	@Override
	public ApiResponse customerWalletToMerchantWalletPurchaseInitiate(PayNovaPaymentRequestDto request) {
		try {
			log.info(" customerWalletToMerchantWalletPurchaseInitiate request ::" + request);
			String signingString = clientIdentifier + request.getClientReference() + "Wallet";

			request.setClientIdentifier(clientIdentifier);

			String path = "/api/wallet/purchase/initiate";
//			ResponseEntity<ApiResponse<PaymentInitiateResponseDto>> resp = payNovaClient.initiatePayment(path, request,
//					PaymentInitiateResponseDto.class, signingString);
			ApiResponse resp = payNovaClient.initiatePayment(path, request, PaymentInitiateResponseDto.class,
					signingString);
			if (!resp.isSuccess()) {
				return resp;
			}

			PaymentInitiateResponseDto initiateResponse = objectMapper.convertValue(resp.getResult(),
					PaymentInitiateResponseDto.class);

			ApiResponse response = walletPurchaseConfirm(request.getCustomer().getCustomerCode(),
					request.getCustomer().getWalletNumber(), initiateResponse.getPaymentReference());

			if (response.isSuccess()) {
				log.info(" customerWalletToMerchantWalletPurchaseInitiate  walletPurchaseConfirm ::" + response);
			}
			return resp;
		} catch (Exception e) {
			//e.printStackTrace();
			ApiResponse error = new ApiResponse(false, "Failed to initiate payment: " + e.getMessage(), null);
			return error;
		}
	}

	// 6) Wallet Purchase Confirm
	@Override
	public ApiResponse walletPurchaseConfirm(String customerCode, Long walletNumber, String paymentReference) {

		try {
			// Signing: client_identifier + customer_code + payment_reference
			System.out.println("customerCode ::" + customerCode + "\n WalletNumber ::" + walletNumber
					+ "\n paymentReference ::" + paymentReference);
			String signingString = clientIdentifier + customerCode + paymentReference;

			WalletPurchaseConfirmRequest request = new WalletPurchaseConfirmRequest();
			request.setClientIdentifier(clientIdentifier);
			request.setCustomerIdentifier(customerCode);
			request.setPaymentReference(paymentReference);
			request.setWalletNumber(walletNumber);

			String path = "/api/wallet/purchase/confirm";

			return payNovaClient.initiatePayment(path, request, PurchaseConfirmResponseDto.class, signingString);

		} catch (Exception e) {
			ApiResponse error = new ApiResponse(false, "Failed to confirm wallet payment: " + e.getMessage(), null);
			return error;
		}
	}

	// 7) Wallet-to-Wallet Initiate
	@Override
	public ApiResponse walletToWalletTransfer(PayNovaPaymentRequestDto request) {
		String signingString = clientIdentifier + request.getClientReference() + "Wallet";
		// request.setClientIdentifier(AppUtils.generateUniqueId());
		request.setClientIdentifier(clientIdentifier);
		System.out.println(" walletToWalletTransfer ::" + request);
		String path = "/api/wallet/purchase/wallet-to-wallet/initiate";
		ApiResponse resp = payNovaClient.initiatePayment(path, request, PaymentInitiateResponseDto.class,
				signingString);

		return resp;
	}

	@Override
	public ApiResponse customerWalletToMerchantWalletTransferInitiate(PayNovaPaymentRequestDto request) {
		try {
			String signingString = clientIdentifier + request.getClientReference() + "Wallet";

			request.setClientIdentifier(clientIdentifier);

			String path = "/api/wallet/transfer/initiate";
//			ResponseEntity<ApiResponse<PaymentInitiateResponseDto>> resp = payNovaClient.initiatePayment(path, request,
//					PaymentInitiateResponseDto.class, signingString);
			ApiResponse resp = payNovaClient.initiatePayment(path, request, PaymentInitiateResponseDto.class,
					signingString);

			if (!resp.isSuccess() || resp.getResult() == null) {
				return resp;
			}

			PaymentInitiateResponseDto initiateResponse = objectMapper.convertValue(resp.getResult(),
					PaymentInitiateResponseDto.class);

			ApiResponse response = walletPurchaseConfirm(request.getCustomer().getCustomerCode(),
					request.getCustomer().getWalletNumber(), initiateResponse.getPaymentReference());
			System.out.println(" walletPurchaseConfirm response ::" + response);

			return resp;
		} catch (Exception e) {
			ApiResponse error = new ApiResponse(false, "Failed to initiate payment: " + e.getMessage(), null);
			return error;
		}
	}

	@Override
	public ApiResponse walletTransferConfrim(String customerCode, Long walletNumber, String paymentReference) {
		try {
			// Signing: client_identifier + customer_code + payment_reference
			System.out.println("customerCode ::" + customerCode + "\n WalletNumber ::" + walletNumber
					+ "\n paymentReference ::" + paymentReference);
			String signingString = clientIdentifier + customerCode + paymentReference;

			WalletPurchaseConfirmRequest request = new WalletPurchaseConfirmRequest();
			request.setClientIdentifier(clientIdentifier);
			request.setCustomerIdentifier(customerCode);
			request.setPaymentReference(paymentReference);
			request.setWalletNumber(walletNumber);

			String path = "/api/wallet/transfer/confirm";

			return payNovaClient.initiatePayment(path, request, PurchaseConfirmResponseDto.class, signingString);

		} catch (Exception e) {
			ApiResponse error = new ApiResponse(false, "Failed to confirm wallet payment: " + e.getMessage(), null);
			return error;
		}
	}

	/*
	 * { "client_identifier": "UAEID", "wallet_number": 22483200968,
	 * "customer_code": "UAEID1" }
	 */
	@Override
	public ApiResponse getOrganizationWalletBalance(String ouid) {
		CustomerWalletBalanceResponse customerWalletBalanceResponse = new CustomerWalletBalanceResponse();
		try {
			CustomerEnrollment customerEnrollment = customerEnrollmentRepository.findByIdentificationNumber(ouid);
			if (customerEnrollment == null) {
				return new ApiResponse(true, "no record found", null);
			}
			WalletBalanceRequest walletBalanceRequest = new WalletBalanceRequest();
			walletBalanceRequest.setClientIdentifier(clientIdentifier);
			walletBalanceRequest.setCustomerCode(customerEnrollment.getCustomerCode());
			walletBalanceRequest.setWalletNumber(customerEnrollment.getWalletNumber());
			ApiResponse response = getWalletBalance(walletBalanceRequest);

			if (response != null && response.isSuccess() && response.getResult() != null) {
				WalletBalanceResponse walletBalanceResponse = objectMapper.convertValue(response.getResult(),
						WalletBalanceResponse.class);
				customerWalletBalanceResponse.setWalletBalanceResponse(walletBalanceResponse);

			}

			customerWalletBalanceResponse.setCustomerEnrollment(customerEnrollment);
			return new ApiResponse(true, "success", customerWalletBalanceResponse);
		} catch (Exception e) {
			//e.printStackTrace();
			return new ApiResponse(false, "Failed to confirm wallet payment: " + e.getMessage(), null);
		}
	}

	@Override
	public ApiResponse getWalletNumberByOuid(String ouid) {
		try {
			log.info("get wallet number by ouid {}", ouid);
			CustomerEnrollment customerEnrollment = customerEnrollmentRepository.findByIdentificationNumber(ouid);
			if (customerEnrollment == null) {
				return new ApiResponse(false, "no record found", null);
			}
			return new ApiResponse(true, "Success", customerEnrollment);
		} catch (Exception e) {
			//e.printStackTrace();
			return new ApiResponse(false, "Something went wrong. please try after sometime", null);
		}
	}

	@Override
	public ApiResponse getAccountBalance(String customerCode) {
		try {
			log.info("getAccountBalance :: " + customerCode);
			AccountBalanceDto accountBalanceDto = new AccountBalanceDto();
			accountBalanceDto.setCustomerCode(customerCode);
			accountBalanceDto.setClientIdentifier(clientIdentifier);

			String path = "/api/wallet-management/wallet/account-Inquiry";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Object> entity = new HttpEntity<>(accountBalanceDto, headers);

			String url = baseUrl + path;

			ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);

			return new ApiResponse(true, "Success", response.getBody());

		} catch (Exception e) {
			
			return new ApiResponse(false, "Something went wrong. please try after sometime", null);
		}
	}

}
