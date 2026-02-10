package com.dtt.service.iface;
import com.dtt.requestdto.CardWalletTopUpRequest;
import com.dtt.requestdto.PayNovaPaymentRequestDto;
import com.dtt.requestdto.WalletBalanceRequest;
import com.dtt.requestdto.WalletCreateRequest;
import com.dtt.requestdto.WalletModifyRequest;

import com.dtt.responsedto.ApiResponse;


public interface PayNovaWalletServiceIface {

	public ApiResponse createWallet(WalletCreateRequest request);

	public ApiResponse getWalletBalance(WalletBalanceRequest request);

	public ApiResponse modifyWallet(WalletModifyRequest request);

	public ApiResponse initiateCardWalletTopUp(CardWalletTopUpRequest request);

	public ApiResponse customerWalletToMerchantWalletPurchaseInitiate(PayNovaPaymentRequestDto request);

	public ApiResponse walletPurchaseConfirm(String customerCode, Long walletNumber, String paymentReference);

	public ApiResponse customerWalletToMerchantWalletTransferInitiate(PayNovaPaymentRequestDto request);

	public ApiResponse walletTransferConfrim(String customerCode, Long walletNumber, String paymentReference);

	public ApiResponse walletToWalletTransfer(PayNovaPaymentRequestDto request);
	
	public ApiResponse getAccountBalance(String customerCode);

	public ApiResponse getOrganizationWalletBalance(String ouid);

	ApiResponse getWalletNumberByOuid(String ouid);

}
