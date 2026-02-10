package com.dtt.service.impl;

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

import com.dtt.requestdto.TransactionSettlementPostRequest;
import com.dtt.requestdto.TransactionSettlementRequest;
import com.dtt.responsedto.ApiResponse;
import com.dtt.service.iface.PaynovaSettlementIface;
import com.dtt.utils.HmacUtil;

@Service
public class PaynovaSettlementImpl implements PaynovaSettlementIface {

	private static final Logger log = LoggerFactory.getLogger(PaynovaSettlementImpl.class);

	@Autowired
	RestTemplate restTemplate;

	@Value("${paynova.client-identifier}")
	String clientIdentifier;

	@Value("${paynova.base-url}")
	private String baseUrl;

	@Value("${paynova.base-url-2}")
	private String baseUrl2;

	@Value("${paynova.signature-key}")
	private String signatureKey;

	@Override
	public ApiResponse getSummary(TransactionSettlementRequest dto) {
		log.info("getSummary :: {}", dto);

		try {
			if (dto.getClientIdentifier() == null) {
				dto.setClientIdentifier(clientIdentifier);
			}

			String signingString = dto.getClientIdentifier() + dto.getReconciliationDate() + dto.getInstrument();

			String signature = HmacUtil.hmacSha256Hex(signingString, signatureKey);

			String url = baseUrl + "/api/transaction-settlement/Summary";

			HttpHeaders headers = new HttpHeaders();
			headers.set("X-Signature", signature);
			headers.setContentType(MediaType.APPLICATION_JSON);

			// 🔥 GET request → no body
			HttpEntity<TransactionSettlementRequest> entity = new HttpEntity<>(dto, headers);

			ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);

			return new ApiResponse(true, "Success", response.getBody());

		} catch (Exception e) {
			log.error("PayNova Summary failed", e);
			return new ApiResponse(false, "Something went wrong", null);
		}
	}

	@Override
	public ApiResponse postSettlement(TransactionSettlementPostRequest request) {
		log.info("postSettlement ::" + request);
		String signingString;
		try {

			if (request.getClientIdentifier() == null) {
				request.setClientIdentifier(clientIdentifier);
				signingString = clientIdentifier + request.getReconciliationDate() + request.getInstrument()
						+ request.getTotalAmount() + request.getTotalCount();
			} else {
				signingString = request.getClientIdentifier() + request.getReconciliationDate()
						+ request.getInstrument() + request.getTotalAmount() + request.getTotalCount();
			}

			String signature = HmacUtil.hmacSha256Hex(signingString, signatureKey);

			String url = baseUrl + "/api/transaction-settlement/post";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("X-Signature", signature);

			HttpEntity<TransactionSettlementPostRequest> entity = new HttpEntity<>(request, headers);

			ResponseEntity<Object> response = restTemplate.postForEntity(url, entity, Object.class);

			return new ApiResponse(true, "Success", response);
		} catch (Exception e) {
			//e.printStackTrace();
			return new ApiResponse(false, "Something went wrong. Please try aftersome", null);
		}
	}

	@Override
	public ApiResponse getDetails(TransactionSettlementRequest request) {
		log.info("getDetails ::" + request);
		String signingString;
		try {
			// String s = client_identifier + reconciliation_date + instrument

			if (request.getClientIdentifier() == null) {
				request.setClientIdentifier(clientIdentifier);
				signingString = clientIdentifier + request.getReconciliationDate() + request.getInstrument();
			} else {
				signingString = request.getClientIdentifier() + request.getReconciliationDate()
						+ request.getInstrument();
			}

			String signature = HmacUtil.hmacSha256Hex(signingString, signatureKey);

			String url = baseUrl2 + "/api/transaction-settlement/details";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("X-Signature", signature);

			HttpEntity<TransactionSettlementRequest> entity = new HttpEntity<>(request, headers);

			ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);

			return new ApiResponse(true, "Success", response);
		} catch (Exception e) {
			
			return new ApiResponse(false, "Something went wrong. Please try aftersome", null);
		}
	}

}
