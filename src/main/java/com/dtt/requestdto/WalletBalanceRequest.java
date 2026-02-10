package com.dtt.requestdto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WalletBalanceRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("client_identifier")
    private String clientIdentifier;

    @JsonProperty("customer_code")
    private String customerCode;

    @JsonProperty("wallet_number")
    private Long walletNumber;

	public String getClientIdentifier() {
		return clientIdentifier;
	}

	public void setClientIdentifier(String clientIdentifier) {
		this.clientIdentifier = clientIdentifier;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Long getWalletNumber() {
		return walletNumber;
	}

	public void setWalletNumber(Long walletNumber) {
		this.walletNumber = walletNumber;
	}

	@Override
	public String toString() {
		return "WalletBalanceRequest [clientIdentifier=" + clientIdentifier + ", customerCode=" + customerCode
				+ ", walletNumber=" + walletNumber + "]";
	}

}
