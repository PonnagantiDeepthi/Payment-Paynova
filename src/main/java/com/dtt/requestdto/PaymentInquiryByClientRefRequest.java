package com.dtt.requestdto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentInquiryByClientRefRequest implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("client_identifier")
	private String clientIdentifier;
	
	@JsonProperty("client_reference")
    private String clientReference;

    public PaymentInquiryByClientRefRequest() {
    }

    public PaymentInquiryByClientRefRequest(String clientIdentifier, String clientReference) {
        this.clientIdentifier = clientIdentifier;
        this.clientReference = clientReference;
    }

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

	@Override
	public String toString() {
		return "PaymentInquiryByClientRefRequest [clientIdentifier=" + clientIdentifier + ", clientReference="
				+ clientReference + "]";
	}
}
