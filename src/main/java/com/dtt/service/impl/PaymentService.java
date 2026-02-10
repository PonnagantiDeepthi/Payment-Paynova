package com.dtt.service.impl;

import java.util.ArrayList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import com.dtt.requestdto.PaymentRequestDto;
import com.dtt.requestdto.Payments;
import com.dtt.requestdto.Services;
import com.dtt.requestdto.CardWalletTopUpRequest;
import com.dtt.requestdto.PayNovaPaymentRequestDto;
import com.dtt.requestdto.PaymentHistoryFilterRequest;
import com.dtt.responsedto.ApiResponse;
import com.dtt.responsedto.PageMeta;
import com.dtt.responsedto.PagedResponse;
import com.dtt.responsedto.PaymentInitiateResponseDto;
import com.dtt.model.PaymentHistory;
import com.dtt.repo.PaymentHistoryRepo;
import com.dtt.service.iface.PayNovaPaymentIface;
import com.dtt.service.iface.PayNovaWalletServiceIface;
import com.dtt.service.iface.PaymentServiceIface;
import com.dtt.utils.AppUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PaymentService implements PaymentServiceIface {

	/** The class. */
	final private String CLASS = "PaymentService";

	private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

	@Value("${paynova.wallet-num}")
	private String merchantWalletNumber;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	PaymentHistoryRepo paymentHistoryRepo;

	@Autowired
	LogMetricsIfaceImpl logMetricsIface;

	@Autowired
	PayNovaPaymentIface payNovaPaymentIface;

	@Autowired
	PayNovaWalletServiceIface payNovaWalletServiceIface;

	@Autowired
	LogMetricsIfaceImpl logMetricsIfaceImpl;

	@Override
	public ApiResponse makePayment(PaymentRequestDto paymentRequestDto) {
		System.out.println(" paymentRequestDto ::" + paymentRequestDto);
		log.info(CLASS + " payment Request dto ::" + paymentRequestDto);
		String category = paymentRequestDto.getPaymentCategory();
		switch (category.toUpperCase()) {
		case "CARD":
			// payment customer card to merchant account directly
			return payCardToMerchantAccount(paymentRequestDto);
		case "CARD-PAY-MERCHANT-WALLET":
			// payment customer card to merchant wallet
			return payCardToMerchantWallet(paymentRequestDto);
		case "PAY-WALLET-TO-MERCHANT-WALLET-PURCHASE":
			// no callback call confirmation api to debit amount from his wallet
			return payCustomerWalletToMerchantwalletPurchaseInitiate(paymentRequestDto);
		case "PAY-WALLET-TO-MERCHANT-WALLET-TRANSFER":
			// no callback call confirmation to debits amount from his walltet
			return payCustomerWalletToMerchantWalletTransferInitiate(paymentRequestDto);
		case "WALLET-TOP-UP":
			return walletTopUp(paymentRequestDto);
		case "WALLET-TO-WALLET-TRANSFER":
			// no callback no confirmation api debits amount from his wallet
			return payWalletToWalletTransfer(paymentRequestDto);
		default:
			return new ApiResponse(false, "Payment Category not supported", null);
		}
	}

	// pay customer card to merchant account directly
	public ApiResponse payCardToMerchantAccount(PaymentRequestDto paymentRequestDto) {
		String transactionId = AppUtils.generateUniqueId();
		PayNovaPaymentRequestDto dto = new PayNovaPaymentRequestDto();
		// 3️⃣ Create PaymentHistory entity and save to DB
		PaymentHistory paymentHistory = new PaymentHistory();
		dto.setClientReference(transactionId);
		dto.setClientExtra("notes");
		dto.setLanguage("en");

		List<Payments> paymentsList = new ArrayList<>();

		if (paymentRequestDto.getPayments() != null && paymentRequestDto.getPayments().size() != 0) {
			for (Payments p : paymentRequestDto.getPayments()) {
				List<Services> pServices = new ArrayList<>();
				Payments newPayment = new Payments();

				for (Services s : p.getServices()) {
					// ss.setAmount(s.getAmount());
					if (s.getCode().toLowerCase().contains("vat")) {
						paymentHistory.setVat(s.getAmount());
					} else {
						paymentHistory.setTransactionFee(s.getAmount());
					}
					s.setCode(s.getCode());
					s.setOnHold(Boolean.FALSE);
					s.setDeductCharges(s.getDeductCharges() != null ? s.getDeductCharges() : Boolean.TRUE);
					pServices.add(s);

				}
				newPayment.setServices(pServices);
				paymentsList.add(newPayment);

			}
		}

		dto.setCustomer(paymentRequestDto.getCustomer());
		dto.setPayments(paymentsList);

		// 3️⃣ Create PaymentHistory entity and save to DB
		// PaymentHistory paymentHistory = new PaymentHistory();

		// paymentHistory.setSubscriberUid(paymentRequestDto.getSubscriberUid());

		paymentHistory.setEmail(paymentRequestDto.getEmail());
		paymentHistory.setMobile(paymentRequestDto.getMobile());
		paymentHistory.setEnglishName(paymentRequestDto.getEnglishName());
		paymentHistory.setCustomerType(paymentRequestDto.getCustomerType());
		// paymentHistory.setCurrency(paymentRequestDto.getCurrency());
		paymentHistory.setPaymentInfo(paymentRequestDto.getPayments().toString());
		paymentHistory.setDescription(paymentRequestDto.getDescription());
		paymentHistory.setTransactionId(transactionId);
		paymentHistory.setPaymentReferenceTransactionId(null); // Will update later after gateway response
		paymentHistory.setAmount(paymentRequestDto.getAmount());
		paymentHistory.setStatus("INITIATED");
		paymentHistory.setTransactionType("DEBIT");
		paymentHistory.setPaymentMethod(paymentRequestDto.getPaymentCategory().toUpperCase());
		paymentHistory.setCreatedOn(AppUtils.getCurrentDateTime());
		paymentHistory.setOrganizationId(paymentRequestDto.getOrganizationId());
		// paymentHistory.setUpdatedAt(AppUtils.getCurrentDateTime());
		paymentHistory.setPaymentCategory("CARD");
		// paymentHistoryRepo.save(paymentHistory);

		ApiResponse response = payNovaPaymentIface.initiateCardPayment(dto);
		System.out.println("cardPayment response :: " + response);

		if (response != null && response.isSuccess() && response.getResult() != null) {
			PaymentInitiateResponseDto initiateResponse = objectMapper.convertValue(response.getResult(),
					PaymentInitiateResponseDto.class);
			paymentHistory.setPaymentReferenceTransactionId(initiateResponse.getPaymentReference());
		}
//		if (response.isSuccess()) {
//			paymentHistory.setPaymentReferenceTransactionId(response.getBody().getResult().getPaymentReference());
//		}
		paymentHistoryRepo.save(paymentHistory);
		logMetricsIface.publishMetricsUpdate();
		return response;
	}

	// pay to merchant wallet or any wallet (similar like top-up wallet)
	public ApiResponse payCardToMerchantWallet(PaymentRequestDto paymentRequestDto) {
		String transactionId = AppUtils.generateUniqueId();
		PayNovaPaymentRequestDto dto = new PayNovaPaymentRequestDto();
		// 3️⃣ Create PaymentHistory entity and save to DB
		PaymentHistory paymentHistory = new PaymentHistory();
		dto.setClientReference(transactionId);
		dto.setClientExtra("notes");
		dto.setLanguage("en");

		// Payments + Services
		List<Payments> paymentsList = new ArrayList<>();

		if (paymentRequestDto.getPayments() != null && paymentRequestDto.getPayments().size() != 0) {
			for (Payments p : paymentRequestDto.getPayments()) {
				List<Services> pServices = new ArrayList<>();
				Payments newPayment = new Payments();

				for (Services s : p.getServices()) {
					if (s.getCode().toLowerCase().contains("vat")) {
						paymentHistory.setVat(s.getAmount());
					} else {
						paymentHistory.setTransactionFee(s.getAmount());
					}
					s.setCode(merchantWalletNumber);
					s.setOnHold(Boolean.FALSE);
					s.setDeductCharges(s.getDeductCharges() != null ? s.getDeductCharges() : Boolean.TRUE);
					pServices.add(s);

				}
				newPayment.setServices(pServices);
				paymentsList.add(newPayment);

			}
		}

		dto.setCustomer(paymentRequestDto.getCustomer());
		dto.setPayments(paymentsList);

		paymentHistory.setEmail(paymentRequestDto.getEmail());
		paymentHistory.setMobile(paymentRequestDto.getMobile());
		paymentHistory.setEnglishName(paymentRequestDto.getEnglishName());
		paymentHistory.setCustomerType(paymentRequestDto.getCustomerType());
		// paymentHistory.setCurrency(paymentRequestDto.getCurrency());
		paymentHistory.setPaymentInfo(paymentRequestDto.getPayments().toString());
		paymentHistory.setDescription(paymentRequestDto.getDescription());
		paymentHistory.setTransactionId(transactionId);
		paymentHistory.setPaymentReferenceTransactionId(null); // Will update later after gateway response
		paymentHistory.setAmount(paymentRequestDto.getAmount());
		paymentHistory.setStatus("INITIATED");
		paymentHistory.setTransactionType("DEBIT");
		paymentHistory.setPaymentMethod(paymentRequestDto.getPaymentCategory().toUpperCase());
		paymentHistory.setCreatedOn(AppUtils.getCurrentDateTime());
		paymentHistory.setOrganizationId(paymentRequestDto.getOrganizationId());
		// paymentHistory.setUpdatedAt(AppUtils.getCurrentDateTime());
		paymentHistory.setPaymentCategory("CARD-PAY-MERCHANT-WALLET");
		// paymentHistoryRepo.save(paymentHistory);

		ApiResponse response = payNovaPaymentIface.initiateCardToMerchantWalletTopUp(dto);
		System.out.println("cardPayment response :: " + response);
		if (response != null && response.isSuccess() && response.getResult() != null) {
			PaymentInitiateResponseDto initiateResponse = objectMapper.convertValue(response.getResult(),
					PaymentInitiateResponseDto.class);
			paymentHistory.setPaymentReferenceTransactionId(initiateResponse.getPaymentReference());
		}

		paymentHistoryRepo.save(paymentHistory);
		logMetricsIface.publishMetricsUpdate();
		return response;
	}

	public ApiResponse payCustomerWalletToMerchantwalletPurchaseInitiate(PaymentRequestDto paymentRequestDto) {
		String transactionId = AppUtils.generateUniqueId();
		PayNovaPaymentRequestDto dto = new PayNovaPaymentRequestDto();
		// 3️⃣ Create PaymentHistory entity and save to DB
		PaymentHistory paymentHistory = new PaymentHistory();
		dto.setClientReference(transactionId);
		dto.setClientExtra("notes");
		dto.setLanguage("en");

		// Payments + Services
		List<Payments> paymentsList = new ArrayList<>();

		if (paymentRequestDto.getPayments() != null && paymentRequestDto.getPayments().size() != 0) {
			for (Payments p : paymentRequestDto.getPayments()) {
				List<Services> pServices = new ArrayList<>();
				Payments newPayment = new Payments();

				for (Services s : p.getServices()) {

					if (s.getCode().toLowerCase().contains("vat")) {
						paymentHistory.setVat(s.getAmount());
					} else {
						paymentHistory.setTransactionFee(s.getAmount());
					}
					s.setCode(s.getCode());
					s.setOnHold(Boolean.FALSE);
					s.setDeductCharges(s.getDeductCharges() != null ? s.getDeductCharges() : Boolean.TRUE);
					pServices.add(s);

				}
				newPayment.setServices(pServices);
				paymentsList.add(newPayment);

			}
		}

		dto.setCustomer(paymentRequestDto.getCustomer());

		// dto.setCustomer(customer);
		dto.setPayments(paymentsList);

		// paymentHistory.setSubscriberUid(paymentRequestDto.getSubscriberUid());
		paymentHistory.setEmail(paymentRequestDto.getEmail());
		paymentHistory.setMobile(paymentRequestDto.getMobile());
		paymentHistory.setEnglishName(paymentRequestDto.getEnglishName());
		paymentHistory.setCustomerType(paymentRequestDto.getCustomerType());
		// paymentHistory.setCurrency(paymentRequestDto.getCurrency());
		paymentHistory.setPaymentInfo(paymentRequestDto.getPayments().toString());
		paymentHistory.setDescription(paymentRequestDto.getDescription());
		paymentHistory.setTransactionId(transactionId);
		paymentHistory.setPaymentReferenceTransactionId(null); // Will update later after gateway response
		paymentHistory.setAmount(paymentRequestDto.getAmount());
		paymentHistory.setStatus("INITIATED");
		paymentHistory.setTransactionType("DEBIT");
		paymentHistory.setPaymentMethod(paymentRequestDto.getPaymentCategory().toUpperCase());
		paymentHistory.setCreatedOn(AppUtils.getCurrentDateTime());
		paymentHistory.setOrganizationId(paymentRequestDto.getOrganizationId());
		// paymentHistory.setUpdatedAt(AppUtils.getCurrentDateTime());
		paymentHistory.setPaymentCategory("PAY-WALLET-TO-MERCHANT-WALLET-PURCHASE");
		// paymentHistoryRepo.save(paymentHistory);

		ApiResponse response = payNovaWalletServiceIface.customerWalletToMerchantWalletPurchaseInitiate(dto);
		System.out.println("walletPayment response :: " + response);

		if (response != null && response.isSuccess() && response.getResult() != null) {
			PaymentInitiateResponseDto initiateResponse = objectMapper.convertValue(response.getResult(),
					PaymentInitiateResponseDto.class);
			paymentHistory.setPaymentReferenceTransactionId(initiateResponse.getPaymentReference());
			paymentHistory.setPaymentResponse(response.getResult().toString());
			paymentHistoryRepo.save(paymentHistory);
			logMetricsIface.publishMetricsUpdate();
			savePaymentHistory(paymentRequestDto, transactionId, paymentRequestDto.getPaymentCategory(), "SUCCESS",
					initiateResponse.getPaymentReference());

			logMetricsIfaceImpl.publishMetricsUpdate(); // ✅ PUBLISH AFTER SAVE

		} else {
			PaymentInitiateResponseDto initiateResponse = objectMapper.convertValue(response.getResult(),
					PaymentInitiateResponseDto.class);
			paymentHistory.setPaymentResponse(response.getResult().toString());
			paymentHistoryRepo.save(paymentHistory);
			logMetricsIface.publishMetricsUpdate();
			savePaymentHistory(paymentRequestDto, transactionId, paymentRequestDto.getPaymentCategory(), "FAILED",
					initiateResponse.getPaymentReference());
		}

		return response;
	}

	ApiResponse payCustomerWalletToMerchantWalletTransferInitiate(PaymentRequestDto paymentRequestDto) {
		String transactionId = AppUtils.generateUniqueId();
		PayNovaPaymentRequestDto dto = new PayNovaPaymentRequestDto();
		// 3️⃣ Create PaymentHistory entity and save to DB
		PaymentHistory paymentHistory = new PaymentHistory();
		dto.setClientReference(transactionId);
		dto.setClientExtra("notes");
		dto.setLanguage("en");

		List<Payments> paymentsList = new ArrayList<>();

		if (paymentRequestDto.getPayments() != null && paymentRequestDto.getPayments().size() != 0) {
			for (Payments p : paymentRequestDto.getPayments()) {
				List<Services> pServices = new ArrayList<>();
				Payments newPayment = new Payments();

				for (Services s : p.getServices()) {

					if (s.getCode().toLowerCase().contains("vat")) {
						paymentHistory.setVat(s.getAmount());
					} else {
						paymentHistory.setTransactionFee(s.getAmount());
					}
					s.setCode(merchantWalletNumber);
					s.setOnHold(Boolean.FALSE);
					s.setDeductCharges(s.getDeductCharges() != null ? s.getDeductCharges() : Boolean.TRUE);
					pServices.add(s);

				}
				newPayment.setServices(pServices);
				paymentsList.add(newPayment);

			}
		}

		dto.setCustomer(paymentRequestDto.getCustomer());
		dto.setPayments(paymentsList);

		dto.setCustomer(paymentRequestDto.getCustomer());
		dto.setPayments(paymentsList);

		// paymentHistory.setSubscriberUid(paymentRequestDto.getSubscriberUid());
		paymentHistory.setEmail(paymentRequestDto.getEmail());
		paymentHistory.setMobile(paymentRequestDto.getMobile());
		paymentHistory.setEnglishName(paymentRequestDto.getEnglishName());
		paymentHistory.setCustomerType(paymentRequestDto.getCustomerType());
		// paymentHistory.setCurrency(paymentRequestDto.getCurrency());
		paymentHistory.setPaymentInfo(paymentRequestDto.getPayments().toString());
		paymentHistory.setDescription(paymentRequestDto.getDescription());
		paymentHistory.setTransactionId(transactionId);
		paymentHistory.setPaymentReferenceTransactionId(null); // Will update later after gateway response
		paymentHistory.setAmount(paymentRequestDto.getAmount());
		paymentHistory.setStatus("INITIATED");
		paymentHistory.setTransactionType("DEBIT");
		paymentHistory.setPaymentMethod(paymentRequestDto.getPaymentCategory().toUpperCase());
		paymentHistory.setCreatedOn(AppUtils.getCurrentDateTime());
		paymentHistory.setOrganizationId(paymentRequestDto.getOrganizationId());
		// paymentHistory.setUpdatedAt(AppUtils.getCurrentDateTime());
		paymentHistory.setPaymentCategory("PAY-WALLET-TO-MERCHANT-WALLET-TRANSFER");
		// paymentHistoryRepo.save(paymentHistory);

		ApiResponse response = payNovaWalletServiceIface.customerWalletToMerchantWalletTransferInitiate(dto);
		System.out.println("walletPayment response :: " + response);
		if (response != null && response.isSuccess() && response.getResult() != null) {
			PaymentInitiateResponseDto initiateResponse = objectMapper.convertValue(response.getResult(),
					PaymentInitiateResponseDto.class);
			paymentHistory.setPaymentReferenceTransactionId(initiateResponse.getPaymentReference());
			paymentHistory.setPaymentResponse(response.getResult().toString());
			paymentHistoryRepo.save(paymentHistory);
			logMetricsIface.publishMetricsUpdate();
			savePaymentHistory(paymentRequestDto, transactionId, paymentRequestDto.getPaymentCategory(), "SUCCESS",
					initiateResponse.getPaymentReference());

			logMetricsIfaceImpl.publishMetricsUpdate(); // ✅ PUBLISH AFTER SAVE

		} else {
			PaymentInitiateResponseDto initiateResponse = objectMapper.convertValue(response.getResult(),
					PaymentInitiateResponseDto.class);
			paymentHistory.setPaymentResponse(response.getResult().toString());
			paymentHistoryRepo.save(paymentHistory);
			logMetricsIface.publishMetricsUpdate();
			savePaymentHistory(paymentRequestDto, transactionId, paymentRequestDto.getPaymentCategory(), "FAILED",
					initiateResponse.getPaymentReference());
		}

		return response;
	}

	// top-up own wallet
	public ApiResponse walletTopUp(PaymentRequestDto paymentRequestDto) {
		String transactionId = AppUtils.generateUniqueId();
		CardWalletTopUpRequest cardWalletTopUpRequest = new CardWalletTopUpRequest();

		cardWalletTopUpRequest.setClientReference(transactionId);
		cardWalletTopUpRequest.setLanguage("en");

		cardWalletTopUpRequest.setWalletNumber(paymentRequestDto.getCustomer().getWalletNumber());
		cardWalletTopUpRequest.setAmount(paymentRequestDto.getAmount());
		cardWalletTopUpRequest.setCustomerCode(paymentRequestDto.getCustomer().getCustomerCode());

		// 3️⃣ Create PaymentHistory entity and save to DB
		PaymentHistory paymentHistory = new PaymentHistory();
		// paymentHistory.setSubscriberUid(paymentRequestDto.getSubscriberUid());
		paymentHistory.setEmail(paymentRequestDto.getEmail());
		paymentHistory.setMobile(paymentRequestDto.getMobile());
		paymentHistory.setEnglishName(paymentRequestDto.getEnglishName());
		paymentHistory.setCustomerType(paymentRequestDto.getCustomerType());
		// paymentHistory.setCurrency(paymentRequestDto.getCurrency());
		// paymentHistory.setPaymentInfo(paymentRequestDto.getPayments().toString());
		paymentHistory.setDescription(paymentRequestDto.getDescription());
		paymentHistory.setTransactionId(transactionId);
		paymentHistory.setPaymentReferenceTransactionId(null); // Will update later after gateway response
		paymentHistory.setAmount(paymentRequestDto.getAmount());
		paymentHistory.setStatus("INITIATED");
		paymentHistory.setTransactionType("CREDIT");
		paymentHistory.setPaymentMethod(paymentRequestDto.getPaymentCategory().toUpperCase());
		paymentHistory.setCreatedOn(AppUtils.getCurrentDateTime());
		paymentHistory.setOrganizationId(paymentRequestDto.getOrganizationId());
		// paymentHistory.setUpdatedAt(AppUtils.getCurrentDateTime());
		paymentHistory.setPaymentCategory("WALLET-TOP-UP");
		paymentHistoryRepo.save(paymentHistory);
		logMetricsIface.publishMetricsUpdate();

		ApiResponse response = payNovaWalletServiceIface.initiateCardWalletTopUp(cardWalletTopUpRequest);
		System.out.println("walletTopUp response :: " + response);

		if (response != null && response.isSuccess() && response.getResult() != null) {
			PaymentInitiateResponseDto initiateResponse = objectMapper.convertValue(response.getResult(),
					PaymentInitiateResponseDto.class);
			paymentHistory.setPaymentReferenceTransactionId(initiateResponse.getPaymentReference());
		}

		paymentHistoryRepo.save(paymentHistory);
		logMetricsIface.publishMetricsUpdate();
		return response;
	}

	public ApiResponse payWalletToWalletTransfer(PaymentRequestDto paymentRequestDto) {
		String transactionId = AppUtils.generateUniqueId();
		PayNovaPaymentRequestDto dto = new PayNovaPaymentRequestDto();
		// 3️⃣ Create PaymentHistory entity and save to DB
		PaymentHistory paymentHistory = new PaymentHistory();
		dto.setClientReference(transactionId);
		dto.setClientExtra("notes");
		dto.setLanguage("en");

		// Payments + Services
		List<Payments> paymentsList = new ArrayList<>();

		if (paymentRequestDto.getPayments() != null && paymentRequestDto.getPayments().size() != 0) {
			for (Payments p : paymentRequestDto.getPayments()) {
				List<Services> pServices = new ArrayList<>();
				Payments newPayment = new Payments();

				for (Services s : p.getServices()) {
					if (s.getCode().toLowerCase().contains("vat")) {
						paymentHistory.setVat(s.getAmount());
					} else {
						paymentHistory.setTransactionFee(s.getAmount());
					}
					s.setCode(merchantWalletNumber);
					s.setOnHold(Boolean.FALSE);
					s.setDeductCharges(s.getDeductCharges() != null ? s.getDeductCharges() : Boolean.TRUE);
					pServices.add(s);

				}
				newPayment.setServices(pServices);
				paymentsList.add(newPayment);

			}
		}

		dto.setCustomer(paymentRequestDto.getCustomer());
		dto.setPayments(paymentsList);
		paymentHistory.setEmail(paymentRequestDto.getEmail());
		paymentHistory.setMobile(paymentRequestDto.getMobile());
		paymentHistory.setEnglishName(paymentRequestDto.getEnglishName());
		paymentHistory.setCustomerType(paymentRequestDto.getCustomerType());
		// paymentHistory.setCurrency(paymentRequestDto.getCurrency());
		paymentHistory.setPaymentInfo(paymentRequestDto.getPayments().toString());
		paymentHistory.setDescription(paymentRequestDto.getDescription());
		paymentHistory.setTransactionId(transactionId);
		paymentHistory.setPaymentReferenceTransactionId(null); // Will update later after gateway response
		paymentHistory.setAmount(paymentRequestDto.getAmount());
		paymentHistory.setStatus("INITIATED");
		paymentHistory.setTransactionType("DEBIT");
		paymentHistory.setPaymentMethod(paymentRequestDto.getPaymentCategory().toUpperCase());
		paymentHistory.setCreatedOn(AppUtils.getCurrentDateTime());
		paymentHistory.setOrganizationId(paymentRequestDto.getOrganizationId());
		// paymentHistory.setUpdatedAt(AppUtils.getCurrentDateTime());
		paymentHistory.setPaymentCategory("WALLET-TO-WALLET-TRANSFER");
		paymentHistoryRepo.save(paymentHistory);
		logMetricsIface.publishMetricsUpdate();

		ApiResponse response = payNovaWalletServiceIface.walletToWalletTransfer(dto);
		System.out.println("walletPayment response :: " + response);

		if (response != null && response.isSuccess() && response.getResult() != null) {
			PaymentInitiateResponseDto initiateResponse = objectMapper.convertValue(response.getResult(),
					PaymentInitiateResponseDto.class);
			paymentHistory.setPaymentReferenceTransactionId(initiateResponse.getPaymentReference());
			paymentHistory.setPaymentResponse(response.getResult().toString());
			paymentHistoryRepo.save(paymentHistory);
			logMetricsIface.publishMetricsUpdate();
			savePaymentHistory(paymentRequestDto, transactionId, paymentRequestDto.getPaymentCategory(), "SUCCESS",
					initiateResponse.getPaymentReference());

			logMetricsIfaceImpl.publishMetricsUpdate(); // ✅ PUBLISH AFTER SAVE

		} else {
			PaymentInitiateResponseDto initiateResponse = objectMapper.convertValue(response.getResult(),
					PaymentInitiateResponseDto.class);
			paymentHistory.setPaymentResponse(response.getResult().toString());
			paymentHistoryRepo.save(paymentHistory);
			logMetricsIface.publishMetricsUpdate();
			savePaymentHistory(paymentRequestDto, transactionId, paymentRequestDto.getPaymentCategory(), "FAILED",
					initiateResponse.getPaymentReference());
		}

		return response;
	}

	@Override
	public ApiResponse getLatestTransactionsByUser(String emailId) {
		try {
			List<PaymentHistory> payments = paymentHistoryRepo.findLatestStatusByEmail(emailId);
			ApiResponse response = new ApiResponse(true, "Record fetched successfully", payments);
			if (payments.isEmpty()) {
				return new ApiResponse(false, "No records found", null);
			}
			return response;
		} catch (Exception e) {
			log.info(CLASS + " Sending PayNova request to {}", e.getMessage());
			return new ApiResponse(false, "Something went wrong. Please try after sometime " + e.getMessage(), null);
		}
	}


	public void savePaymentHistory(PaymentRequestDto paymentRequestDto, String transactionId, String paymentCategory,
			String status, String paymentReferenceTransactionId) {
		PaymentHistory paymentHistory = new PaymentHistory();
		// paymentHistory.setSubscriberUid(paymentRequestDto.getSubscriberUid());
		paymentHistory.setEmail(paymentRequestDto.getEmail());
		paymentHistory.setMobile(paymentRequestDto.getMobile());
		paymentHistory.setEnglishName(paymentRequestDto.getEnglishName());
		paymentHistory.setCustomerType(paymentRequestDto.getCustomerType());
		// paymentHistory.setCurrency(paymentRequestDto.getCurrency());
		paymentHistory.setPaymentInfo(paymentRequestDto.getPayments().toString());
		paymentHistory.setDescription(paymentRequestDto.getDescription());
		paymentHistory.setTransactionId(transactionId);
		paymentHistory.setPaymentReferenceTransactionId(paymentReferenceTransactionId); // Will update later after
																						// gateway response
		paymentHistory.setAmount(paymentRequestDto.getAmount());
		paymentHistory.setStatus(status);
		paymentHistory.setPaymentMethod(paymentRequestDto.getPaymentCategory().toUpperCase());
		paymentHistory.setCreatedOn(AppUtils.getCurrentDateTime());
		paymentHistory.setOrganizationId(paymentRequestDto.getOrganizationId());
		paymentHistory.setPaymentCategory(paymentCategory);

		if (paymentCategory.toUpperCase().equalsIgnoreCase("WALLET_TOP_UP")) {
			paymentHistory.setTransactionType("CREDIT");
		} else {
			paymentHistory.setTransactionType("DEBIT");
		}

		paymentHistoryRepo.save(paymentHistory);
		logMetricsIface.publishMetricsUpdate();

	}

	@Override
	public ApiResponse getPaymentRecordByPaymentReferenceTransactionId(String paymentReferenceTransactionId) {
		try {
			if (paymentReferenceTransactionId == null || paymentReferenceTransactionId.isEmpty()) {
				return new ApiResponse(false, "no record found", null);
			}
			List<PaymentHistory> record = paymentHistoryRepo
					.findSuccessAndFailedByPaymentReferenceTransactionId(paymentReferenceTransactionId);

			if (record.isEmpty()) {
				List<PaymentHistory> initiatePaymentRecord = paymentHistoryRepo
						.findInitiatePaymentRecordByReferenceTransactionId(paymentReferenceTransactionId);
				if (initiatePaymentRecord.isEmpty())
					return new ApiResponse(false, "no record found", null);
				else
					return new ApiResponse(true, "Success", initiatePaymentRecord);
			}
			return new ApiResponse(true, "Success", record);
		} catch (Exception e) {
			//e.printStackTrace();
			return new ApiResponse(false, "something went wrong. please try after somethime", null);
		}
	}

	@Override
	public ApiResponse getPaymentRecordByTransactionId(String transactionId) {
		try {
			if (transactionId == null || transactionId.isEmpty()) {
				return new ApiResponse(false, "no record found", null);
			}
			List<PaymentHistory> record = paymentHistoryRepo.findSuccessAndFailedByTransactionId(transactionId);
			if (record.isEmpty()) {
				List<PaymentHistory> initiatedPaymentRecord = paymentHistoryRepo
						.findInitiatedPaymentRecordByTransactionId(transactionId);
				if (initiatedPaymentRecord.isEmpty())
					return new ApiResponse(false, "no record found", null);
				return new ApiResponse(true, "Success", initiatedPaymentRecord);
			}
			return new ApiResponse(true, "Success", record);
		} catch (Exception e) {
			// Exception handling removed
			return new ApiResponse(false, "something went wrong. please try after somethime", null);
		}
	}

	public ApiResponse getPayments(PaymentHistoryFilterRequest request) {

		Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

		Page<PaymentHistory> result = paymentHistoryRepo.filterPayments(request.getTransactionId(), request.getStatus(),
				request.getTransactionType(), request.getOrganizationId(), pageable);

		PageMeta pageMeta = new PageMeta(result.getSize(), result.getNumber(), result.getTotalElements(),
				result.getTotalPages());

		PagedResponse<PaymentHistory> response = new PagedResponse<>(result.getContent(), pageMeta);

		return new ApiResponse(true, "Payment history fetched successfully", response);
	}


}
