package com.dtt.requestdto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentHistoryFilterRequest implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("transactionId")
	private String transactionId;   // search box
	
	@JsonProperty("status")
    private String status;           // SUCCESS / FAILED
	
	@JsonProperty("transactionType")
    private String transactionType;  // CREDIT / DEBIT
	
	@JsonProperty("organizationId")
    private String organizationId;   // provider

	@JsonProperty("page")
    private int page = 0;
	
	@JsonProperty("size")
    private int size = 10;
    
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	@Override
	public String toString() {
		return "[transactionId=" + transactionId + ", status=" + status
				+ ", transactionType=" + transactionType + ", organizationId=" + organizationId + ", page=" + page
				+ ", size=" + size + "]";
	}
}
