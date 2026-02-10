package com.dtt.responsedto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SystemServiceResponseDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
    private String projectName;
    private String serviceType;
    private BigDecimal pricePerTransaction;   // or BigDecimal
    private BigDecimal platformFeePercent;
    private BigDecimal governmentVatPercent;
    private Boolean vatIncludedInPricing;
    private String serviceName;
    private String serviceCode;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public BigDecimal getPricePerTransaction() {
		return pricePerTransaction;
	}
	public void setPricePerTransaction(BigDecimal pricePerTransaction) {
		this.pricePerTransaction = pricePerTransaction;
	}
	public BigDecimal getPlatformFeePercent() {
		return platformFeePercent;
	}
	public void setPlatformFeePercent(BigDecimal platformFeePercent) {
		this.platformFeePercent = platformFeePercent;
	}
	public BigDecimal getGovernmentVatPercent() {
		return governmentVatPercent;
	}
	public void setGovernmentVatPercent(BigDecimal governmentVatPercent) {
		this.governmentVatPercent = governmentVatPercent;
	}
	public Boolean getVatIncludedInPricing() {
		return vatIncludedInPricing;
	}
	public void setVatIncludedInPricing(Boolean vatIncludedInPricing) {
		this.vatIncludedInPricing = vatIncludedInPricing;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	@Override
	public String toString() {
		return "[id=" + id + ", projectName=" + projectName + ", serviceType=" + serviceType
				+ ", pricePerTransaction=" + pricePerTransaction + ", platformFeePercent=" + platformFeePercent
				+ ", governmentVatPercent=" + governmentVatPercent + ", vatIncludedInPricing=" + vatIncludedInPricing
				+ ", serviceName=" + serviceName + ", serviceCode=" + serviceCode + "]";
	}


}
