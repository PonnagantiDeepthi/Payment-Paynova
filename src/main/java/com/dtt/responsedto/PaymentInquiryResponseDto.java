package com.dtt.responsedto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PaymentInquiryResponseDto implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6234492042085894193L;
	private List<PaymentLineDto> payments;
    private String paymentReference;
    private BigDecimal totalAmount;
    private BigDecimal totalCharges;
    private BigDecimal amount;
    private Integer responseCode;
    private String responseDatetime;
    private String responseDescription;

    public List<PaymentLineDto> getPayments() {
        return payments;
    }
    public void setPayments(List<PaymentLineDto> payments) {
        this.payments = payments;
    }

    public String getPaymentReference() {
        return paymentReference;
    }
    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalCharges() {
        return totalCharges;
    }
    public void setTotalCharges(BigDecimal totalCharges) {
        this.totalCharges = totalCharges;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getResponseCode() {
        return responseCode;
    }
    public void setResponseCode(Integer responseCode) {
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
	@Override
	public String toString() {
		return "[payments=" + payments + ", paymentReference=" + paymentReference
				+ ", totalAmount=" + totalAmount + ", totalCharges=" + totalCharges + ", amount=" + amount
				+ ", responseCode=" + responseCode + ", responseDatetime=" + responseDatetime + ", responseDescription="
				+ responseDescription + "]";
	}
}

