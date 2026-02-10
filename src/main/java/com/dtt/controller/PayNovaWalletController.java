/**
 * PayNovaWalletController
 *
 * This controller exposes all wallet-related operations for the PayNova integration.
 * It delegates all business logic to PayNovaWalletService and handles the following:
 *
 *  1. Create Wallet
 *  2. Get Wallet Balance
 *  3. Modify Wallet
 *  4. Card Wallet Top-Up
 *  5. Wallet Purchase Initiation
 *  6. Wallet Purchase Confirmation
 *  7. Wallet-to-Wallet Transfer
 *
 * Each API receives a request DTO, forwards it to the service layer, and returns a 
 * standardized ApiResponse wrapper to the client. 
 *
 * Author: Vijay Kumar
 * Created On: 13-Nov-2025
 * Description: REST controller for PayNova wallet operations.
 */
package com.dtt.controller;

import com.dtt.requestdto.*;
import com.dtt.responsedto.ApiResponse;
import com.dtt.service.iface.PayNovaWalletServiceIface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("/paynova/api/wallet")  //for dalil
//@RequestMapping("/api/wallet")  //for 162
public class PayNovaWalletController {

	@Autowired
	private PayNovaWalletServiceIface walletService;

	/**
	 * Create a new PayNova wallet for a customer.
	 *
	 * @param request WalletCreateRequest containing customer and wallet details.
	 * @return ApiResponse<WalletCreateResponse> with wallet creation status.
	 */
	@PostMapping("/create")
	public ApiResponse createWallet(@RequestBody WalletCreateRequest request) {
		return walletService.createWallet(request);
	}

	/**
	 * Fetch the current balance of a PayNova wallet.
	 *
	 * @param request WalletBalanceRequest with wallet number and identifiers.
	 * @return ApiResponse<WalletBalanceResponse> with wallet balance details.
	 */
	@PostMapping("/balance")
	public ApiResponse getWalletBalance(@RequestBody WalletBalanceRequest request) {
		return walletService.getWalletBalance(request);
	}

	/**
	 * Modify an existing wallet’s attributes (activation, suspension, update
	 * limits, etc.).
	 *
	 * @param request WalletModifyRequest containing modifications.
	 * @return ApiResponse<WalletModifyResponse> with status of wallet modification.
	 */
	@PostMapping("/modify")
	public ApiResponse modifyWallet(@RequestBody WalletModifyRequest request) {
		return walletService.modifyWallet(request);
	}

	/**
	 * Initiate a card-based wallet top-up operation.
	 *
	 * @param request CardWalletTopUpRequest with card and wallet details.
	 * @return ApiResponse<CardWalletTopUpResponse> with PayNova top-up response.
	 */
	@PostMapping("/card-topup")
	public ApiResponse initiateCardWalletTopUp(@RequestBody CardWalletTopUpRequest request) {
		return walletService.initiateCardWalletTopUp(request);
	}

	/**
	 * Initiate a wallet-based purchase transaction.
	 *
	 * @param request WalletPurchaseInitiateRequest containing purchase details.
	 * @return ApiResponse<WalletPurchaseInitiateResponse> with initiation response.
	 */
	@PostMapping("/purchase/initiate")
	public ApiResponse initiatePayToWallet(@RequestBody PayNovaPaymentRequestDto request) {
		return walletService.customerWalletToMerchantWalletPurchaseInitiate(request);
	}

	/**
	 * Confirm a previously initiated wallet purchase transaction.
	 *
	 //* @param request WalletPurchaseConfirmRequest containing transaction reference
	 *                details.
	 * @return ApiResponse<WalletPurchaseInitiateResponse> with confirmation result.
	 */
	@PostMapping("/purchase/confirm")
	public ApiResponse confirmWalletPurchase(@RequestParam String customerCode, @RequestParam Long walletNumber,
			@RequestParam String paymentReference) {
		return walletService.walletPurchaseConfirm(customerCode, walletNumber, paymentReference);
	}

	/**
	 * Perform a wallet-to-wallet transfer between two PayNova wallets.
	 *
	 * @param request WalletToWalletRequest containing source and target wallet
	 *                details.
	 * @return ApiResponse<WalletPurchaseInitiateResponse> with transfer result.
	 */
	@PostMapping("/transfer")
	public ApiResponse walletToWalletTransfer(@RequestBody PayNovaPaymentRequestDto request) {
		return walletService.walletToWalletTransfer(request);
	}
	
	@GetMapping("/account/balance/{customerCode}")
	public ApiResponse getAccountBalance(@PathVariable("customerCode") String customerCode) {
		return walletService.getAccountBalance(customerCode);
	}

	@GetMapping("/check/balance")
	public ApiResponse getWalletBalance(@RequestParam("ouid") String ouid) {
		return walletService.getOrganizationWalletBalance(ouid);
	}

	@GetMapping(value = "/check/balance/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ApiResponse> getWalletBalanceStream(@RequestParam("ouid") String ouid) {

		try {
			return Flux.interval(Duration.ofSeconds(5))
					.map(tick -> {
						return walletService.getOrganizationWalletBalance(ouid);
					})
					.onErrorReturn(new ApiResponse(false,"Error Fetching Wallet Balance",null));
		} catch (Exception e) {
			// Exception caught and handled
			return Flux.just(new ApiResponse(false,"Error Fetching Wallet Balance",null));
		}
	}

	@GetMapping("/check/{ouid}")
	public ApiResponse getWalletNumberByOuid(@PathVariable("ouid") String ouid) {
		return walletService.getWalletNumberByOuid(ouid);
	}
}
