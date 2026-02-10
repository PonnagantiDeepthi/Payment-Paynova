package com.dtt.requestdto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Services implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("amount")
	private BigDecimal amount;

	@JsonProperty("code")
	private String code;

	@JsonProperty("deduct_charges")
	private Boolean deductCharges;

	@JsonProperty("is_on_hold")
	private Boolean onHold;

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

	public Boolean getDeductCharges() {
		return deductCharges;
	}

	public void setDeductCharges(Boolean deductCharges) {
		this.deductCharges = deductCharges;
	}

	public Boolean getOnHold() {
		return onHold;
	}

	public void setOnHold(Boolean onHold) {
		this.onHold = onHold;
	}

	@Override
	public String toString() {
		return "{" + "\"amount\":" + amount + "," + "\"code\":\"" + code + "\"," + "\"deductCharges\":" + deductCharges
				+ "," + "\"onHold\":" + onHold + "}";
	}

}
