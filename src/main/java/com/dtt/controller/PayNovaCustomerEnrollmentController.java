/**
 * PayNovaCustomerEnrollmentController
 *
 * This controller handles the customer enrollment process for PayNova.
 * It accepts CustomerEnrollmentRequest payloads and delegates the processing
 * to PaynovaCustomerService, which interacts with PayNova's enrollment API.
 *
 * Author: Vijay Kumar
 * Created On: 13-Nov-2025
 * Description: REST API endpoint for enrolling a new PayNova customer.
 */
package com.dtt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtt.requestdto.CustomerEnrollmentRequest;
import com.dtt.responsedto.ApiResponse;
import com.dtt.service.iface.CustomerEnrollmentIface;

@RestController
@RequestMapping("/paynova/api")
//@RequestMapping("/api")
public class PayNovaCustomerEnrollmentController {

	@Autowired
	private CustomerEnrollmentIface paynovaService;

	/**
	 * Enroll a new customer into PayNova.
	 *
	 * @param request CustomerEnrollmentRequest containing customer details.
	 * @return ApiResponse<CustomerEnrollmentResponse> containing enrollment
	 *         response received from PayNova.
	 */
	@PostMapping("/post/customer/enroll")
	public ApiResponse enrollCustomer(@RequestBody CustomerEnrollmentRequest request) {
		return paynovaService.enrollCustomer(request);
	}
	
	/**
	 * Modify an existing PayNova customer details.
	 *
	 * @param request CustomerModificationRequest containing updated customer
	 *                fields.
	 * @return ApiResponse<CustomerModificationResponse> containing the result and
	 *         PayNova API response data.
	 */
	@PostMapping("/post/customer/modify")
	public ApiResponse modifyCustomer(@RequestBody CustomerEnrollmentRequest request) {
		return paynovaService.modifyCustomer(request);
	}
	
	@GetMapping("/get/customer/email/{email}")
	public ApiResponse getCustomerDetailsByEmailId(@PathVariable("email") String email) {
		return paynovaService.getCustomerDetailsByEmailId(email);
	}
}



