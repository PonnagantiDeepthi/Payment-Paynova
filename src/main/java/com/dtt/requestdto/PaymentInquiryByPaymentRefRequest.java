package com.dtt.requestdto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentInquiryByPaymentRefRequest implements Serializable{

   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("client_identifier")
	private String clientIdentifier;
	
	@JsonProperty("payment_reference")
    private String paymentReference;

    public PaymentInquiryByPaymentRefRequest() {
    }

    public PaymentInquiryByPaymentRefRequest(String clientIdentifier, String paymentReference) {
        this.clientIdentifier = clientIdentifier;
        this.paymentReference = paymentReference;
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

	@Override
	public String toString() {
		return "PaymentInquiryByPaymentRefRequest [clientIdentifier=" + clientIdentifier + ", paymentReference="
				+ paymentReference + "]";
	}
}
