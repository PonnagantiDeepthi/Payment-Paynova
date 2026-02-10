package com.dtt.responsedto;

import java.io.Serializable;
import java.math.BigDecimal;

public class RateCardResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SystemServiceResponseDto systemService;
    private Integer minVolume;
    private Integer maxVolume;
    private BigDecimal priceTransaction;
	public SystemServiceResponseDto getSystemService() {
		return systemService;
	}
	public void setSystemService(SystemServiceResponseDto systemService) {
		this.systemService = systemService;
	}
	public Integer getMinVolume() {
		return minVolume;
	}
	public void setMinVolume(Integer minVolume) {
		this.minVolume = minVolume;
	}
	public Integer getMaxVolume() {
		return maxVolume;
	}
	public void setMaxVolume(Integer maxVolume) {
		this.maxVolume = maxVolume;
	}
	public BigDecimal getPriceTransaction() {
		return priceTransaction;
	}
	public void setPriceTransaction(BigDecimal priceTransaction) {
		this.priceTransaction = priceTransaction;
	}
	@Override
	public String toString() {
		return "[systemService=" + systemService + ", minVolume=" + minVolume + ", maxVolume="
				+ maxVolume + ", priceTransaction=" + priceTransaction + "]";
	} 

}
