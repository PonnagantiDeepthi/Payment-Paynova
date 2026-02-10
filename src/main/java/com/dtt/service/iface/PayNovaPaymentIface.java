package com.dtt.service.iface;

import com.dtt.requestdto.PayNovaPaymentRequestDto;
import com.dtt.responsedto.ApiResponse;

public interface PayNovaPaymentIface {
	
	public ApiResponse initiateCardPayment(PayNovaPaymentRequestDto dto);
	
	public ApiResponse initiatePayToWallet(PayNovaPaymentRequestDto request);
	
	public ApiResponse initiateCardToMerchantWalletTopUp(PayNovaPaymentRequestDto request);

}
