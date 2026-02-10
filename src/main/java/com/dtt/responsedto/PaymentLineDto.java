package com.dtt.responsedto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PaymentLineDto implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3089137849298262123L;
	private String paymentTransactionReference;
    private BigDecimal serviceAmount;
    private BigDecimal totalAmount;
    private BigDecimal totalCharges;
    private String status;

    public String getPaymentTransactionReference() {
        return paymentTransactionReference;
    }
    public void setPaymentTransactionReference(String paymentTransactionReference) {
        this.paymentTransactionReference = paymentTransactionReference;
    }

    public BigDecimal getServiceAmount() {
        return serviceAmount;
    }
    public void setServiceAmount(BigDecimal serviceAmount) {
        this.serviceAmount = serviceAmount;
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

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
	@Override
	public String toString() {
		return "PaymentLineDto [paymentTransactionReference=" + paymentTransactionReference + ", serviceAmount="
				+ serviceAmount + ", totalAmount=" + totalAmount + ", totalCharges=" + totalCharges + ", status="
				+ status + "]";
	}
}
