package com.dtt.model;

import java.io.Serializable;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "customer_wallet")
public class CustomerEnrollment implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
	@JoinColumn(name = "project_id", referencedColumnName = "client_project_id", nullable = false)
    private ClientProjects clientProjects;

    // Request Fields
    @Column(name = "client_identifier", nullable = false, length = 50)
    private String clientIdentifier;

    @Column(name = "customer_code", nullable = false, length = 50)
    private String customerCode;

    @Column(name = "customer_type", nullable = false, length = 20)
    private String customerType;

    @Column(name = "arabic_name", nullable = false, length = 100)
    private String arabicName;

    @Column(name = "english_name", nullable = false, length = 100)
    private String englishName;

    @Column(name = "identification_number", nullable = false, length = 50)
    private String identificationNumber;
    
    @Column(name = "stakeholder_type", length = 25)
    private String stakeHolderType;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "mobile", length = 20)
    private String mobile;

    @Column(name = "password", length = 255)
    private String password;

    // Response Fields
    @Column(name = "wallet_number")
    private Long walletNumber;

    @Column(name = "paynova_identifier", length = 100)
    private String paynovaIdentifier;

    @Column(name = "created_on", nullable = false, updatable = false)
    private Instant createdOn;

    @Column(name = "updated_on")
    private Instant updatedOn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientIdentifier() {
		return clientIdentifier;
	}

	public void setClientIdentifier(String clientIdentifier) {
		this.clientIdentifier = clientIdentifier;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getArabicName() {
		return arabicName;
	}

	public void setArabicName(String arabicName) {
		this.arabicName = arabicName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getWalletNumber() {
		return walletNumber;
	}

	public void setWalletNumber(Long walletNumber) {
		this.walletNumber = walletNumber;
	}

	public String getPaynovaIdentifier() {
		return paynovaIdentifier;
	}

	public void setPaynovaIdentifier(String paynovaIdentifier) {
		this.paynovaIdentifier = paynovaIdentifier;
	}

//	public Integer getResponseCode() {
//		return responseCode;
//	}
//
//	public void setResponseCode(Integer responseCode) {
//		this.responseCode = responseCode;
//	}
//
//	public String getResponseDescription() {
//		return responseDescription;
//	}
//
//	public void setResponseDescription(String responseDescription) {
//		this.responseDescription = responseDescription;
//	}
//
//	public String getResponseDatetime() {
//		return responseDatetime;
//	}
//
//	public void setResponseDatetime(String responseDatetime) {
//		this.responseDatetime = responseDatetime;
//	}

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

	public String getStakeHolderType() {
		return stakeHolderType;
	}

	public void setStakeHolderType(String stakeHolderType) {
		this.stakeHolderType = stakeHolderType;
	}

	public ClientProjects getClientProjects() {
		return clientProjects;
	}

	public void setClientProjects(ClientProjects clientProjects) {
		this.clientProjects = clientProjects;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", clientProjects=" + clientProjects + ", clientIdentifier="
				+ clientIdentifier + ", customerCode=" + customerCode + ", customerType=" + customerType
				+ ", arabicName=" + arabicName + ", englishName=" + englishName + ", identificationNumber="
				+ identificationNumber + ", stakeHolderType=" + stakeHolderType + ", address=" + address + ", email="
				+ email + ", mobile=" + mobile + ", password=" + password + ", walletNumber=" + walletNumber
				+ ", paynovaIdentifier=" + paynovaIdentifier + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn
				+ "]";
	}
    // Getters & Setters (same as before)
}
