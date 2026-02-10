package com.dtt.service.impl;

import com.dtt.requestdto.CustomerEnrollmentRequest;
import com.dtt.responsedto.ApiResponse;
import com.dtt.responsedto.CustomerEnrollmentResponse;
import com.dtt.model.ClientProjects;
import com.dtt.model.CustomerEnrollment;
import com.dtt.repo.ClientProjectsRepo;
import com.dtt.repo.CustomerEnrollmentRepository;
import com.dtt.service.iface.CustomerEnrollmentIface;
import com.dtt.utils.AppUtils;
import com.dtt.utils.HmacUtil;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerEnrollmentImpl implements CustomerEnrollmentIface {

	private static final Logger log = LoggerFactory.getLogger(CustomerEnrollmentImpl.class);

	@Value("${paynova.base-url}")
	private String baseUrl;

	@Value("${paynova.signature-key}")
	private String signatureKey;

	@Value("${paynova.client-identifier}")
	private String clientIdentifier;

	@Autowired
	CustomerEnrollmentRepository customerEnrollmentRepository;
	
	@Autowired
	ClientProjectsRepo clientProjectsRepo;

	private final RestTemplate restTemplate;
	// private final ObjectMapper objectMapper = new ObjectMapper();

	public CustomerEnrollmentImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate() {
        int length = 10 + RANDOM.nextInt(3); // 8, 9, or 10
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

	@Override
	public ApiResponse enrollCustomer(CustomerEnrollmentRequest request) {
		String endpoint = baseUrl + "/api/customer/enrollment";

		CustomerEnrollment customerEnrollment = new CustomerEnrollment();

		try {
			log.info("PayNova customer enrollment ::" + request);
			
			if(request.getClientProjectsId() == null || request.getClientIdentifier().trim().isEmpty()) {
				return new ApiResponse(false, "Project Id can't be null or empty ::", null);
			}
			
			ClientProjects clientProjects = clientProjectsRepo.findByClientProjectId(request.getClientProjectsId());
			if(clientProjects == null) {
				return new ApiResponse(false, "Project id not found :" + request.getClientProjectsId(), null);
			}
			
			CustomerEnrollment cusEnrollment  = customerEnrollmentRepository.findByIdentificationNumber(request.getCustomer().getIdentificationNumber());
			
			if(cusEnrollment != null) {
				return new ApiResponse(false, "Wallet already exists for the given organization.", cusEnrollment);
			}

			if (request.getCustomer().getCustomerCode() == null || request.getCustomer().getCustomerCode().isEmpty()) {
				request.getCustomer().setCustomerCode(generate());
			}

			List<CustomerEnrollment> existing = customerEnrollmentRepository
					.findByEmailOrCustomerCodeOrIdentificationNumber(request.getCustomer().getEmail(),
							request.getCustomer().getCustomerCode(), request.getCustomer().getIdentificationNumber());

			String email = request.getCustomer().getEmail();
			String customerCode = request.getCustomer().getCustomerCode();
			String identifier = request.getCustomer().getIdentificationNumber();

			Map<String, String> result = existing.stream()
					.flatMap(c -> Stream.of(
							Optional.ofNullable(c.getEmail()).filter(email::equals)
									.map(v -> Map.entry("email", "Email already used")),

							Optional.ofNullable(c.getCustomerCode()).filter(customerCode::equals)
									.map(v -> Map.entry("customerCode", "Customer code already used")),

							Optional.ofNullable(c.getIdentificationNumber()).filter(identifier::equals)
									.map(v -> Map.entry("identifier", "Identifier already used"))))
					.flatMap(Optional::stream)
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a // keep first if
																									// duplicate key
					));

			if (!result.isEmpty()) {
				// Combine messages like: "Email already used, Customer code already used"
				String message = String.join(", ", result.values());
				return new ApiResponse(false, message, null);
			}

			String dataToSign = clientIdentifier + request.getCustomer().getCustomerCode()
					+ request.getCustomer().getCustomerType();

			String signature = HmacUtil.hmacSha256Hex(dataToSign, signatureKey);

			customerEnrollment.setClientProjects(clientProjects);
			
			customerEnrollment.setClientIdentifier(clientIdentifier);
			
			customerEnrollment.setCustomerCode(request.getCustomer().getCustomerCode());
			customerEnrollment.setCustomerType(request.getCustomer().getCustomerType());
			customerEnrollment.setArabicName(request.getCustomer().getArabicName());
			customerEnrollment.setEnglishName(request.getCustomer().getEnglishName());
			customerEnrollment.setIdentificationNumber(request.getCustomer().getIdentificationNumber());
			customerEnrollment.setAddress(request.getCustomer().getAddress());
			customerEnrollment.setEmail(request.getCustomer().getEmail());
			customerEnrollment.setMobile(request.getCustomer().getMobile());
			customerEnrollment.setPassword(request.getCustomer().getPassword());
			customerEnrollment.setStakeHolderType(request.getCustomer().getStakeholderType());
			customerEnrollment.setCreatedOn(AppUtils.getCurrentDateTime());
			customerEnrollment.setUpdatedOn(AppUtils.getCurrentDateTime());
			request.setClientIdentifier(clientIdentifier);

			// DO NOT send mobile number to PayNova
			request.getCustomer().setMobile(null);
			// DO NOT send email number to PayNova
			request.getCustomer();// .setEmail(null);
			// DO NOT send password number to PayNova
			request.getCustomer().setPassword(null);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("X-Signature", signature);

			log.info("enrollment customer request::" + request + "\n URL ::" + endpoint);
			HttpEntity<CustomerEnrollmentRequest> entity = new HttpEntity<>(request, headers);
			ResponseEntity<CustomerEnrollmentResponse> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST,
					entity, CustomerEnrollmentResponse.class);

			log.info("Customer Enrollment Response ::" + responseEntity);
			CustomerEnrollmentResponse response = responseEntity.getBody();

			if (response != null) {
				customerEnrollment.setWalletNumber(response.getWalletNumber());
				customerEnrollment.setPaynovaIdentifier(response.getIdentifier());
				// customerEnrollment.setResponseCode(response.getResponseCode());
				// customerEnrollment.setResponseDescription(response.getResponseDescription());
				// customerEnrollment.setResponseDatetime(response.getResponseDatetime());
			}
			customerEnrollmentRepository.save(customerEnrollment);

			if (response != null && response.getResponseCode() == 0) {

				return new ApiResponse(true, "Success", response);
			}

			String message = (response != null ? response.getResponseDescription() : "Unknown error");
			return new ApiResponse(false, message, response);

		} catch (HttpStatusCodeException ex) {
			log.error("PayNova API returned error: {} - {}", ex.getStatusCode(), ex.getResponseBodyAsString());

			return new ApiResponse(false, "PayNova returned error: " + ex.getStatusCode(), null);

		} catch (RestClientException ex) {
			log.error("RestClientException while calling PayNova", ex);

			return new ApiResponse(false, "Connection or timeout error: " + ex.getMessage(), null);

		} catch (Exception ex) {
			log.error("Unexpected exception during PayNova enrollment", ex);

			return new ApiResponse(false, "Exception occurred: " + ex.getMessage(), null);
		}
	}

	@Override
	public ApiResponse modifyCustomer(CustomerEnrollmentRequest request) {
		String endpoint = baseUrl + "/api/customer/modify";
		// PaynovaCustomerLog logEntity = new PaynovaCustomerLog();

		try {
			log.info("Initiating PayNova customer modification for code: {}", request);

			// Signature: client_identifier + customer_code + customer_type
			String dataToSign = clientIdentifier + request.getCustomer().getCustomerCode()
					+ request.getCustomer().getCustomerType();

			String signature = HmacUtil.hmacSha256Hex(dataToSign, signatureKey);

			CustomerEnrollment customerEnrollment = customerEnrollmentRepository
					.findByIdentificationNumber(request.getCustomer().getIdentificationNumber());
			if (customerEnrollment == null) {
				return new ApiResponse(false, "no record found", null);
			} else {
				
				ClientProjects clientProjects = clientProjectsRepo.findByClientProjectId(request.getClientProjectsId());
				if(clientProjects == null) {
					return new ApiResponse(false, "Project id not found :" + request.getClientProjectsId(), null);
				}
				
				customerEnrollment.setClientIdentifier(clientIdentifier);
				customerEnrollment.setCustomerCode(request.getCustomer().getCustomerCode());
				customerEnrollment.setCustomerType(request.getCustomer().getCustomerType());
				customerEnrollment.setArabicName(request.getCustomer().getArabicName());
				customerEnrollment.setEnglishName(request.getCustomer().getEnglishName());
				customerEnrollment.setIdentificationNumber(request.getCustomer().getIdentificationNumber());
				customerEnrollment.setAddress(request.getCustomer().getAddress());
				customerEnrollment.setEmail(request.getCustomer().getEmail());
				customerEnrollment.setMobile(request.getCustomer().getMobile());
				customerEnrollment.setPassword(request.getCustomer().getPassword());
				customerEnrollment.setCreatedOn(AppUtils.getCurrentDateTime());
				customerEnrollment.setUpdatedOn(AppUtils.getCurrentDateTime());
				customerEnrollment.setClientProjects(clientProjects);

				request.setClientIdentifier(clientIdentifier);

				// DO NOT send mobile number to PayNova
				request.getCustomer().setMobile(null);
				// DO NOT send email number to PayNova
				request.getCustomer().setEmail(null);
				// DO NOT send password number to PayNova
				request.getCustomer().setPassword(null);

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.add("X-Signature", signature);

//				logEntity.setCustomerCode(request.getCustomer().getCustomerCode());
//				logEntity.setCustomerType(request.getCustomer().getCustomerType());
//				logEntity.setRequestPayload(objectMapper.writeValueAsString(request));

				HttpEntity<CustomerEnrollmentRequest> entity = new HttpEntity<>(request, headers);
				ResponseEntity<CustomerEnrollmentResponse> responseEntity = restTemplate.exchange(endpoint,
						HttpMethod.POST, entity, CustomerEnrollmentResponse.class);

				CustomerEnrollmentResponse response = responseEntity.getBody();
				log.info(" customer enrollment response ::" + response);				

				if (response != null && response.getResponseCode() == 0) {
					customerEnrollmentRepository.save(customerEnrollment);
					return new ApiResponse(true, "Success", response);
				}

				String message = (response != null ? response.getResponseDescription() : "Unknown error");
				return new ApiResponse(false, message, response);
			}

		} catch (HttpStatusCodeException ex) {
			log.error("PayNova API returned error: {} - {}", ex.getStatusCode(), ex.getResponseBodyAsString());

			return new ApiResponse(false, "PayNova returned error: " + ex.getStatusCode(), null);

		} catch (RestClientException ex) {
			log.error("RestClientException while calling PayNova", ex);

			return new ApiResponse(false, "Connection or timeout error: " + ex.getMessage(), null);

		} catch (Exception ex) {
			log.error("Unexpected exception during PayNova customer modification", ex);

			return new ApiResponse(false, "Exception occurred: " + ex.getMessage(), null);
		}
	}

	@Override
	public ApiResponse getCustomerDetailsByEmailId(String email) {
		CustomerEnrollment customer = customerEnrollmentRepository.findByEmail(email);
		if (customer == null) {
			return new ApiResponse(false, "Customer not found for email: " + email, null);
		}
		return new ApiResponse(true, "Success", customer);
	}

}
