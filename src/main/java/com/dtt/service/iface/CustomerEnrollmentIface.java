package com.dtt.service.iface;

import com.dtt.requestdto.CustomerEnrollmentRequest;
import com.dtt.responsedto.ApiResponse;

public interface CustomerEnrollmentIface {
	
	public ApiResponse enrollCustomer(CustomerEnrollmentRequest request);
	
	public ApiResponse modifyCustomer(CustomerEnrollmentRequest request);
	
	public ApiResponse getCustomerDetailsByEmailId(String email);
}
