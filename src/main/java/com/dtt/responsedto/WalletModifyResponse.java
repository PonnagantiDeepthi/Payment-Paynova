package com.dtt.responsedto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WalletModifyResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("response_code")
    private Integer responseCode;

    @JsonProperty("response_description")
    private String responseDescription;

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

	@Override
	public String toString() {
		return "[responseCode=" + responseCode + ", responseDescription=" + responseDescription
				+ "]";
	}

}
