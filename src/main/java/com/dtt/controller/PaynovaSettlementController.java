package com.dtt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtt.requestdto.TransactionSettlementPostRequest;
import com.dtt.requestdto.TransactionSettlementRequest;
import com.dtt.responsedto.ApiResponse;
import com.dtt.service.iface.PaynovaSettlementIface;

@RestController
@RequestMapping("/paynova")
public class PaynovaSettlementController {

	@Autowired
	PaynovaSettlementIface paynovaSettlementIface;

	@PostMapping("/api/transaction-settlement/Summary")
	public ApiResponse getSummary(@RequestBody TransactionSettlementRequest request) {
		return paynovaSettlementIface.getSummary(request);
	}

	@PostMapping("/api/transaction-settlement/post")
	public ApiResponse postSettlement(@RequestBody TransactionSettlementPostRequest request) {
		return paynovaSettlementIface.postSettlement(request);
	}

	@PostMapping("/ClientIntegrationApi/api/transaction-settlement/details")
	public ApiResponse getDetails(@RequestBody TransactionSettlementRequest request) {
		return paynovaSettlementIface.getDetails(request);
	}

}
