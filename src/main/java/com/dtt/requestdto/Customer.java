package com.dtt.requestdto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("customer_code")
	private String customerCode;
	
	@JsonProperty("wallet_number")
	private Long walletNumber;

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
		return "[customerCode=" + customerCode + ", walletNumber=" + walletNumber + "]";
	}
}
