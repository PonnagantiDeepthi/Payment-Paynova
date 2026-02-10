package com.dtt.responsedto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PurchaseConfirmResponseDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal totalAmount;
    private BigDecimal totalCharges;
    private BigDecimal amount;
    private String paymentReference;
    private BigDecimal remainingBalance;
    private String status;
    private String reconciliationDate;
    private Integer responseCode;
    private String responseDatetime;
    private String responseDescription;
    // getters/setters
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
	public String getPaymentReference() {
		return paymentReference;
	}
	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}
	public BigDecimal getRemainingBalance() {
		return remainingBalance;
	}
	public void setRemainingBalance(BigDecimal remainingBalance) {
		this.remainingBalance = remainingBalance;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReconciliationDate() {
		return reconciliationDate;
	}
	public void setReconciliationDate(String reconciliationDate) {
		this.reconciliationDate = reconciliationDate;
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
		return "[totalAmount=" + totalAmount + ", totalCharges=" + totalCharges + ", amount="
				+ amount + ", paymentReference=" + paymentReference + ", remainingBalance=" + remainingBalance
				+ ", status=" + status + ", reconciliationDate=" + reconciliationDate + ", responseCode=" + responseCode
				+ ", responseDatetime=" + responseDatetime + ", responseDescription=" + responseDescription + "]";
	}
}
