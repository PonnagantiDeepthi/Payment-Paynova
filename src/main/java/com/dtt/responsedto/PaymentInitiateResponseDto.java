package com.dtt.responsedto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentInitiateResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("response_code")
	private int responseCode;
	
	@JsonProperty("response_description")
	private String responseDescription;
	
	@JsonProperty("payment_url")
	private String paymentUrl;
	
	@JsonProperty("total_amount")
	private BigDecimal totalAmount;
	
	@JsonProperty("payment_reference")
	private String paymentReference;
	
	@JsonProperty("total_Charges")
	private double totalCharges;
	
	@JsonProperty("amount")
	private BigDecimal amount;
	
	@JsonProperty("response_datetime")
	private String responseDatetime;

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

	public String getPaymentUrl() {
		return paymentUrl;
	}

	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public double getTotalCharges() {
		return totalCharges;
	}

	public void setTotalCharges(double totalCharges) {
		this.totalCharges = totalCharges;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getResponseDatetime() {
		return responseDatetime;
	}

	public void setResponseDatetime(String responseDatetime) {
		this.responseDatetime = responseDatetime;
	}

	@Override
	public String toString() {
		return "[responseCode=" + responseCode + ", responseDescription="
				+ responseDescription + ", paymentUrl=" + paymentUrl + ", totalAmount=" + totalAmount
				+ ", paymentReference=" + paymentReference + ", totalCharges=" + totalCharges + ", amount=" + amount
				+ ", responseDatetime=" + responseDatetime + "]";
	}
	

}
