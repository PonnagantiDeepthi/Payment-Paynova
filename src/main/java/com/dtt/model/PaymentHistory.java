package com.dtt.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment_history")
public class PaymentHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "email", nullable = false, length = 100)
	private String email;

	@Column(name = "mobile", length = 100)
	private String mobile;

	@Column(name = "full_name", nullable = false, length = 100)
	private String englishName;

	@Column(name = "customer_type", nullable = false, length = 100)
	private String customerType;
	
	@Column(name="transaction_type", length=10)
	private String transactionType;

	@Column(name = "organization_id", length = 100)
	private String organizationId;

	@Column(name = "payment_info")
	private String paymentInfo;

	@Column(name = "description", length = 100)
	private String description;

	@Column(name = "transaction_id", nullable = false, length = 100)
	private String transactionId;

	@Column(name = "payment_reference_transaction_id", length = 100)
	private String paymentReferenceTransactionId;

	// @Column(name = "amount", nullable = false)
	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "status", nullable = false, length = 20)
	private String status;

	@Column(name = "payment_method", length = 50)
	private String paymentMethod;

	@Column(name = "created_on", nullable = false)
	private Instant createdOn;

	@Column(name = "payment_category", length = 50)
	private String paymentCategory;

	@Column(name = "transaction_fee")
	private BigDecimal transactionFee= BigDecimal.ZERO;

	@Column(name = "vat")
	private BigDecimal vat= BigDecimal.ZERO;

	@Column(name = "platform_fee")
	private BigDecimal platformFee = BigDecimal.ZERO;

	@Column(name = "payment_response")
	private String paymentResponse;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPaymentReferenceTransactionId() {
		return paymentReferenceTransactionId;
	}

	public void setPaymentReferenceTransactionId(String paymentReferenceTransactionId) {
		this.paymentReferenceTransactionId = paymentReferenceTransactionId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Instant createdOn) {
		this.createdOn = createdOn;
	}

	public String getPaymentCategory() {
		return paymentCategory;
	}

	public void setPaymentCategory(String paymentCategory) {
		this.paymentCategory = paymentCategory;
	}

	public String getPaymentResponse() {
		return paymentResponse;
	}

	public void setPaymentResponse(String paymentResponse) {
		this.paymentResponse = paymentResponse;
	}

	public BigDecimal getTransactionFee() {
		return transactionFee;
	}

	public void setTransactionFee(BigDecimal transactionFee) {
		this.transactionFee = transactionFee;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public BigDecimal getPlatformFee() {
		return platformFee;
	}

	public void setPlatformFee(BigDecimal platformFee) {
		this.platformFee = platformFee;
	}

	@Override
	public String toString() {
		return " [id=" + id + ", email=" + email + ", mobile=" + mobile + ", englishName=" + englishName
				+ ", customerType=" + customerType + ", transactionType=" + transactionType + ", organizationId="
				+ organizationId + ", paymentInfo=" + paymentInfo + ", description=" + description + ", transactionId="
				+ transactionId + ", paymentReferenceTransactionId=" + paymentReferenceTransactionId + ", amount="
				+ amount + ", status=" + status + ", paymentMethod=" + paymentMethod + ", createdOn=" + createdOn
				+ ", paymentCategory=" + paymentCategory + ", transactionFee=" + transactionFee + ", vat=" + vat
				+ ", platformFee=" + platformFee + ", paymentResponse=" + paymentResponse + "]";
	}

	

	
}
