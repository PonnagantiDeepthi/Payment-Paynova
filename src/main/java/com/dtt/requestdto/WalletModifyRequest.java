package com.dtt.requestdto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WalletModifyRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("client_identifier")
    private String clientIdentifier;

    @JsonProperty("customer_code")
    private String customerCode;

    @JsonProperty("wallet_number")
    private Long walletNumber;

    @JsonProperty("description_en")
    private String descriptionEn;

    @JsonProperty("description_ar")
    private String descriptionAr;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("is_main")
    private Boolean isMain;

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

	public Long getWalletNumber() {
		return walletNumber;
	}

	public void setWalletNumber(Long walletNumber) {
		this.walletNumber = walletNumber;
	}

	public String getDescriptionEn() {
		return descriptionEn;
	}

	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}

	public String getDescriptionAr() {
		return descriptionAr;
	}

	public void setDescriptionAr(String descriptionAr) {
		this.descriptionAr = descriptionAr;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsMain() {
		return isMain;
	}

	public void setIsMain(Boolean isMain) {
		this.isMain = isMain;
	}

	@Override
	public String toString() {
		return "WalletModifyRequest [clientIdentifier=" + clientIdentifier + ", customerCode=" + customerCode
				+ ", walletNumber=" + walletNumber + ", descriptionEn=" + descriptionEn + ", descriptionAr="
				+ descriptionAr + ", isActive=" + isActive + ", isMain=" + isMain + "]";
	}

}
