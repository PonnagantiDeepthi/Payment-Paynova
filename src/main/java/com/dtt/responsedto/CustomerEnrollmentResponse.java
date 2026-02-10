package com.dtt.responsedto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerEnrollmentResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("wallet_number")
	private Long walletNumber;
	@JsonProperty("response_code")
	private int responseCode;
	@JsonProperty("response_datetime")
	private String responseDatetime;
	@JsonProperty("response_description")
	private String responseDescription;
	private String identifier;

	public Long getWalletNumber() {
		return walletNumber;
	}

	public void setWalletNumber(Long walletNumber) {
		this.walletNumber = walletNumber;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDatetime() {
		return responseDatetime;
	}

	public void setResponseDatetime(String responseDatetime) {
		this.responseDatetime = responseDatetime;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public String toString() {
		return "[walletNumber=" + walletNumber + ", responseCode=" + responseCode
				+ ", responseDatetime=" + responseDatetime + ", responseDescription=" + responseDescription
				+ ", identifier=" + identifier + "]";
	}

}
