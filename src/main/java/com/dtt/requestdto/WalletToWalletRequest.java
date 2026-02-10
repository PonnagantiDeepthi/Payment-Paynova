package com.dtt.requestdto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WalletToWalletRequest implements Serializable{

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

    private String language;

    @JsonProperty("customer")
    private Object customer; // include customer_code & wallet_number as in docs

    @JsonProperty("payments")
    private List<Object> payments;

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

	public Object getCustomer() {
		return customer;
	}

	public void setCustomer(Object customer) {
		this.customer = customer;
	}

	public List<Object> getPayments() {
		return payments;
	}

	public void setPayments(List<Object> payments) {
		this.payments = payments;
	}

	@Override
	public String toString() {
		return "WalletToWalletRequest [clientIdentifier=" + clientIdentifier + ", clientReference=" + clientReference
				+ ", clientExtra=" + clientExtra + ", clientWebhook=" + clientWebhook + ", language=" + language
				+ ", customer=" + customer + ", payments=" + payments + "]";
	}

}
