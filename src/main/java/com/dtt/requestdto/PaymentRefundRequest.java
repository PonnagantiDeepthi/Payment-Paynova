package com.dtt.requestdto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentRefundRequest implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 25051825926811055L;
	
	@JsonProperty("clientIdentifier")
	private String clientIdentifier;
	
	@JsonProperty("paymentReference")
    private String paymentReference;
	
	@JsonProperty("clientReference")
    private String clientReference;
	
	@JsonProperty("clientExtra")
    private String clientExtra;
	
	@JsonProperty("clientWebhook")
    private String clientWebhook;
	
	@JsonProperty("language")
    private String language;

    public PaymentRefundRequest() {
    }

    public String getClientIdentifier() {
        return clientIdentifier;
    }
    public void setClientIdentifier(String clientIdentifier) {
        this.clientIdentifier = clientIdentifier;
    }

    public String getPaymentReference() {
        return paymentReference;
    }
    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
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

	@Override
	public String toString() {
		return "PaymentRefundRequest [clientIdentifier=" + clientIdentifier + ", paymentReference=" + paymentReference
				+ ", clientReference=" + clientReference + ", clientExtra=" + clientExtra + ", clientWebhook="
				+ clientWebhook + ", language=" + language + "]";
	}
}
