package com.dtt.requestdto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountBalanceDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("customer_code")
	private String customerCode;
	
	@JsonProperty("client_identifier")
	private String clientIdentifier;

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getClientIdentifier() {
		return clientIdentifier;
	}

	public void setClientIdentifier(String clientIdentifier) {
		this.clientIdentifier = clientIdentifier;
	}

	@Override
	public String toString() {
		return "[customerCode=" + customerCode + ", clientIdentifier=" + clientIdentifier + "]";
	}
	

}
