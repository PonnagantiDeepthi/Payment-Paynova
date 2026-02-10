package com.dtt.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.*;

@Entity
@Table(name = "paynova_return_transaction")
public class PaynovaReturnTransaction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "payment_transaction_reference", nullable = false, length = 100)
	private String paymentTransactionReference;

	@Column(name = "client_reference", length = 100)
	private String clientReference;

	@Column(name = "payment_reference", length = 100)
	private String paymentReference;

	@Column(name = "payment_transaction_instrument")
	private Integer paymentTransactionInstrument;

	@Column(name = "payment_transaction_instrument_name", length = 100)
	private String paymentTransactionInstrumentName;

	@Column(name = "status")
	private Integer status;

	@Column(name = "status_name", length = 100)
	private String statusName;

	@Column(name = "service_amount", precision = 10, scale = 0)
	private BigDecimal serviceAmount;

	@Column(name = "total_charges", precision = 10, scale = 0)
	private BigDecimal totalCharges;

	@Column(name = "total_amount", precision = 10, scale = 0)
	private BigDecimal totalAmount;

	@Column(name = "card_number", length = 50)
	private String cardNumber;

	@Column(name = "payer_customer_code", length = 50)
	private String payerCustomerCode;

	@Column(name = "transaction_date")
	private LocalDateTime transactionDate;

	@Column(name = "reconciliation_date")
	private LocalDate reconciliationDate;

	@Column(name = "response_code")
	private Integer responseCode;

	@Column(name = "response_date_time")
	private LocalDateTime responseDateTime;

	@Column(name = "response_description", length = 255)
	private String responseDescription;

	@Column(name = "client_extra", length = 100)
	private String clientExtra;

	@Lob
	@Column(name = "raw_payload")
	private String rawPayload;

	@Column(name = "source", length = 100)
	private String source;

	@Column(name = "created_on")
	private Instant createdOn;

	@Column(name = "updated_on")
	private Instant updatedOn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPaymentTransactionReference() {
		return paymentTransactionReference;
	}

	public void setPaymentTransactionReference(String paymentTransactionReference) {
		this.paymentTransactionReference = paymentTransactionReference;
	}

	public String getClientReference() {
		return clientReference;
	}

	public void setClientReference(String clientReference) {
		this.clientReference = clientReference;
	}

	public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public Integer getPaymentTransactionInstrument() {
		return paymentTransactionInstrument;
	}

	public void setPaymentTransactionInstrument(Integer paymentTransactionInstrument) {
		this.paymentTransactionInstrument = paymentTransactionInstrument;
	}

	public String getPaymentTransactionInstrumentName() {
		return paymentTransactionInstrumentName;
	}

	public void setPaymentTransactionInstrumentName(String paymentTransactionInstrumentName) {
		this.paymentTransactionInstrumentName = paymentTransactionInstrumentName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public BigDecimal getServiceAmount() {
		return serviceAmount;
	}

	public void setServiceAmount(BigDecimal serviceAmount) {
		this.serviceAmount = serviceAmount;
	}

	public BigDecimal getTotalCharges() {
		return totalCharges;
	}

	public void setTotalCharges(BigDecimal totalCharges) {
		this.totalCharges = totalCharges;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getPayerCustomerCode() {
		return payerCustomerCode;
	}

	public void setPayerCustomerCode(String payerCustomerCode) {
		this.payerCustomerCode = payerCustomerCode;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	public LocalDate getReconciliationDate() {
		return reconciliationDate;
	}

	public void setReconciliationDate(LocalDate reconciliationDate) {
		this.reconciliationDate = reconciliationDate;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public LocalDateTime getResponseDateTime() {
		return responseDateTime;
	}

	public void setResponseDateTime(LocalDateTime responseDateTime) {
		this.responseDateTime = responseDateTime;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public String getClientExtra() {
		return clientExtra;
	}

	public void setClientExtra(String clientExtra) {
		this.clientExtra = clientExtra;
	}

	public String getRawPayload() {
		return rawPayload;
	}

	public void setRawPayload(String rawPayload) {
		this.rawPayload = rawPayload;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Instant createdOn) {
		this.createdOn = createdOn;
	}

	public Instant getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Instant updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Override
	public String toString() {
		return " [id=" + id + ", paymentTransactionReference=" + paymentTransactionReference + ", clientReference="
				+ clientReference + ", paymentReference=" + paymentReference + ", paymentTransactionInstrument="
				+ paymentTransactionInstrument + ", paymentTransactionInstrumentName="
				+ paymentTransactionInstrumentName + ", status=" + status + ", statusName=" + statusName
				+ ", serviceAmount=" + serviceAmount + ", totalCharges=" + totalCharges + ", totalAmount=" + totalAmount
				+ ", cardNumber=" + cardNumber + ", payerCustomerCode=" + payerCustomerCode + ", transactionDate="
				+ transactionDate + ", reconciliationDate=" + reconciliationDate + ", responseCode=" + responseCode
				+ ", responseDateTime=" + responseDateTime + ", responseDescription=" + responseDescription
				+ ", clientExtra=" + clientExtra + ", rawPayload=" + rawPayload + ", source=" + source + ", createdOn="
				+ createdOn + ", updatedOn=" + updatedOn + "]";
	}

}
