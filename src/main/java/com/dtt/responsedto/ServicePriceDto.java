package com.dtt.responsedto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ServicePriceDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal amount;
    private String code;
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return "[amount=" + amount + ", code=" + code + "]";
	}

}
