package com.dtt.responsedto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WalletCreateResponse implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("response_code")
    private int responseCode;

    @JsonProperty("response_description")
    private String responseDescription;

    @JsonProperty("wallet_number")
    private Long walletNumber;

    @JsonProperty("customer_code")
    private String customerCode;

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
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

	@Override
	public String toString() {
		return "[responseCode=" + responseCode + ", responseDescription=" + responseDescription
				+ ", walletNumber=" + walletNumber + ", customerCode=" + customerCode + "]";
	}
}
