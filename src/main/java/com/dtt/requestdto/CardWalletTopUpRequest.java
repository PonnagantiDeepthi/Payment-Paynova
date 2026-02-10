package com.dtt.requestdto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardWalletTopUpRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("client_identifier")
    private String clientIdentifier;

    @JsonProperty("client_reference")
    private String clientReference;

    @JsonProperty("client_extra")
    private String clientExtra;

    @JsonProperty("client_webhook")
    private String clientWebhook;

    @JsonProperty("language")
    private String language;

    @JsonProperty("wallet_number")
    private Long walletNumber;

    @JsonProperty("customer_code")
    private String customerCode;

    @JsonProperty("amount")
    private BigDecimal amount;

	public String getClientIdentifier() {
		return clientIdentifier;
	}

	public void setClientIdentifier(String clientIdentifier) {
		this.clientIdentifier = clientIdentifier;
	}

	public String getClientReference() {
		return clientReference;
	}

	public void setClientReference(String clientReference) {
		this.clientReference = clientReference;
	}

	public String getClientExtra() {
		return clientExtra;
	}

	public void setClientExtra(String clientExtra) {
		this.clientExtra = clientExtra;
	}

	public String getClientWebhook() {
		return clientWebhook;
	}

	public void setClientWebhook(String clientWebhook) {
		this.clientWebhook = clientWebhook;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "{"
				+ "\"clientIdentifier\":\"" + clientIdentifier + "\","
				+ "\"clientReference\":\"" + clientReference + "\","
				+ "\"clientExtra\":\"" + clientExtra + "\","
				+ "\"clientWebhook\":\"" + clientWebhook + "\","
				+ "\"language\":\"" + language + "\","
				+ "\"walletNumber\":\"" + walletNumber + "\","
				+ "\"customerCode\":\"" + customerCode + "\","
				+ "\"amount\":" + amount
				+ "}";

	}

}
