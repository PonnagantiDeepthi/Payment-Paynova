package com.dtt.requestdto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WalletPurchaseConfirmRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("client_identifier")
    private String clientIdentifier;

    @JsonProperty("customer_identifier")
    private String customerIdentifier; // docs use customer_identifier or customer_code

    @JsonProperty("payment_reference")
    private String paymentReference;

    @JsonProperty("wallet_number")
    private Long walletNumber;

	public String getClientIdentifier() {
		return clientIdentifier;
	}

	public void setClientIdentifier(String clientIdentifier) {
		this.clientIdentifier = clientIdentifier;
	}

	public String getCustomerIdentifier() {
		return customerIdentifier;
	}

	public void setCustomerIdentifier(String customerIdentifier) {
		this.customerIdentifier = customerIdentifier;
	}

	public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public Long getWalletNumber() {
		return walletNumber;
	}

	public void setWalletNumber(Long walletNumber) {
		this.walletNumber = walletNumber;
	}

	@Override
	public String toString() {
		return "WalletPurchaseConfirmRequest [clientIdentifier=" + clientIdentifier + ", customerIdentifier="
				+ customerIdentifier + ", paymentReference=" + paymentReference + ", walletNumber=" + walletNumber
				+ "]";
	}

}
