package com.dtt.service.iface;

import com.dtt.requestdto.TransactionSettlementPostRequest;
import com.dtt.requestdto.TransactionSettlementRequest;
import com.dtt.responsedto.ApiResponse;

public interface PaynovaSettlementIface {

	ApiResponse getSummary(TransactionSettlementRequest dto);

	ApiResponse postSettlement(TransactionSettlementPostRequest request);

	ApiResponse getDetails(TransactionSettlementRequest request);
}
