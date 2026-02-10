package com.dtt.service.iface;




import com.dtt.requestdto.PaymentHistoryFilterRequest;
import com.dtt.requestdto.PaymentRequestDto;
import com.dtt.responsedto.ApiResponse;

public interface PaymentServiceIface {
	
	ApiResponse makePayment(PaymentRequestDto paymentRequestDto);
	
	ApiResponse getLatestTransactionsByUser(String userId);
	
	ApiResponse getPaymentRecordByPaymentReferenceTransactionId(String paymentReferenceTransactionId);
	
	ApiResponse getPaymentRecordByTransactionId(String transactionId);
	
	//ApiResponses<Page<PaymentHistory>> getPayments(PaymentHistoryFilterRequest request);
	
	ApiResponse getPayments(PaymentHistoryFilterRequest request);

}
