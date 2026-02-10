package com.dtt.requestdto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.annotation.Nonnull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentRequestDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Nonnull
	@JsonProperty("amount")
	private BigDecimal amount;

	// @Nonnull
	// private String currency;

	// @Nonnull
	
	private String subscriberUid;

	@JsonProperty("organizationId")
	private String organizationId;

	@JsonProperty("description")
	private String description;

	@Nonnull
	@JsonProperty("customerType")
	private String customerType;

	@Nonnull
	@JsonProperty("englishName")
	private String englishName;

	@Nonnull
	@JsonProperty("mobile")
	private String mobile;

	@Nonnull
	@JsonProperty("email")
	private String email;

	@Nonnull
	@JsonProperty("payments")
	private List<Payments> payments; // ✅ FIXED (List)

	@JsonProperty("customer")
	private Customer customer;

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	private String paymentCategory;

	// getters & setters

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getSubscriberUid() {
		return subscriberUid;
	}

	public void setSubscriberUid(String subscriberUid) {
		this.subscriberUid = subscriberUid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Payments> getPayments() {
		return payments;
	}

	public void setPayments(List<Payments> payments) {
		this.payments = payments;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getPaymentCategory() {
		return paymentCategory;
	}

	public void setPaymentCategory(String paymentCategory) {
		this.paymentCategory = paymentCategory;
	}

	@Override
	public String toString() {
		return "[amount=" + amount + ", subscriberUid=" + subscriberUid + ", organizationId=" + organizationId
				+ ", description=" + description + ", customerType=" + customerType + ", englishName=" + englishName
				+ ", mobile=" + mobile + ", email=" + email + ", payments=" + payments + ", customer=" + customer
				+ ", paymentCategory=" + paymentCategory + "]";
	}

}
