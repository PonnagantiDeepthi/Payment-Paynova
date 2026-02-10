package com.dtt.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.dtt.responsedto.ApiResponse;
import com.dtt.utils.HmacUtil;

@Service
public class PayNovaClient {

	private static final Logger log = LoggerFactory.getLogger(PayNovaClient.class);

	@Autowired
	private RestTemplate restTemplate;
	private final String baseUrl;
	private final String signatureKey;

	public PayNovaClient(RestTemplate restTemplate, @Value("${paynova.base-url}") String baseUrl,
			@Value("${paynova.signature-key}") String signatureKey) {
		this.restTemplate = restTemplate;
		this.baseUrl = baseUrl;
		this.signatureKey = signatureKey;
	}

	public ApiResponse initiatePayment(String path, Object body, Class responseType, String signingString) {
		String url = baseUrl + path;
		String signature = HmacUtil.hmacSha256Hex(signingString, signatureKey);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-Signature", signature);
		HttpEntity<Object> entity = new HttpEntity<>(body, headers);
		System.out.println("PayNovaClient :: initiatePayment :: generic :: " + body);
		System.out.println("PayNovaClient :: signature :::" + signature);

		try {
			log.info("Sending PayNova request to {}", url);
			ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, responseType);

			return new ApiResponse(true, "Success", response.getBody());
			//return apiResponse;
		} catch (HttpClientErrorException e) {
			log.error("Client error: {}", e.getResponseBodyAsString());
			ApiResponse apiResponse = new ApiResponse(false, "Invalid request to PayNova: " + e.getStatusCode(), null);
			return apiResponse;
		} catch (HttpServerErrorException e) {
			log.error("Server error from PayNova: {}", e.getResponseBodyAsString());
			ApiResponse apiResponse = new ApiResponse(false, "PayNova server error: " + e.getStatusCode(), null);
			return apiResponse;
		} catch (ResourceAccessException e) {
			log.error("Connection error: {}", e.getMessage());
			ApiResponse apiResponse = new ApiResponse(false, "Connection error while contacting PayNova", null);
			return apiResponse;
		} catch (RestClientException e) {
			log.error("RestClientException: {}", e.getMessage());
			ApiResponse apiResponse = new ApiResponse(false, "Unexpected error while calling PayNova", null);
			return apiResponse;
		} catch (Exception e) {
			log.error("Unhandled exception while calling PayNova", e);
			ApiResponse apiResponse = new ApiResponse(false, "Something went wrong while processing the payment", null);
			return apiResponse;
		}
	}

}
