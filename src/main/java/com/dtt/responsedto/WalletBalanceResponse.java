package com.dtt.responsedto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WalletBalanceResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("response_code")
    private Integer responseCode;

    @JsonProperty("response_description")
    private String responseDescription;

    @JsonProperty("wallet_number")
    private Long walletNumber;

    @JsonProperty("customer_code")
    private String customerCode;

    @JsonProperty("balance")
    private Double balance;          // some docs use 'balance' or 'wallet_balance'

    @JsonProperty("is_active")
    private Boolean isActive;

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public Long getWalletNumber() {
		return walletNumber;
	}

	public void setWalletNumber(Long walletNumber) {
		this.walletNumber = walletNumber;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "[responseCode=" + responseCode + ", responseDescription=" + responseDescription
				+ ", walletNumber=" + walletNumber + ", customerCode=" + customerCode + ", balance=" + balance
				+ ", isActive=" + isActive + "]";
	}

}
