package com.dtt.requestdto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

///api/card/purchase/initiate DTO

@JsonIgnoreProperties(ignoreUnknown = true)
public class PayNovaPaymentRequestDto implements Serializable {

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

    @JsonProperty("language")
    private String language;

    @JsonProperty("client_webhook")
    private String clientWebhook;

    @JsonProperty("service_template")
    private String serviceTemplate;

    @JsonProperty("command")
    private String command;

    @JsonProperty("customer")
    private Customer customer;

    @JsonProperty("payments")
    private List<Payments> payments;

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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getClientWebhook() {
		return clientWebhook;
	}

	public void setClientWebhook(String clientWebhook) {
		this.clientWebhook = clientWebhook;
	}

	public String getServiceTemplate() {
		return serviceTemplate;
	}

	public void setServiceTemplate(String serviceTemplate) {
		this.serviceTemplate = serviceTemplate;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<Payments> getPayments() {
		return payments;
	}

	public void setPayments(List<Payments> payments) {
		this.payments = payments;
	}

	@Override
	public String toString() {
		return "{"
				+ "\"clientIdentifier\":\"" + clientIdentifier + "\","
				+ "\"clientReference\":\"" + clientReference + "\","
				+ "\"clientExtra\":\"" + clientExtra + "\","
				+ "\"language\":\"" + language + "\","
				+ "\"clientWebhook\":\"" + clientWebhook + "\","
				+ "\"serviceTemplate\":\"" + serviceTemplate + "\","
				+ "\"command\":\"" + command + "\","
				+ "\"customer\":" + customer + ","
				+ "\"payments\":" + payments
				+ "}";
	}
}
