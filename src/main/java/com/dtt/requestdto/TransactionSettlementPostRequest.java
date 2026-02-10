package com.dtt.requestdto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionSettlementPostRequest {

    @JsonProperty("client_identifier")
    private String clientIdentifier;

    @JsonProperty("instrument")
    private String instrument;   // Card / Wallet /All

    @JsonProperty("reconciliation_date")
    private String reconciliationDate; // yyyy-MM-dd

    @JsonProperty("total_count")
    private Integer totalCount;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    // getters & setters

    public String getClientIdentifier() {
        return clientIdentifier;
    }

    public void setClientIdentifier(String clientIdentifier) {
        this.clientIdentifier = clientIdentifier;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getReconciliationDate() {
        return reconciliationDate;
    }

    public void setReconciliationDate(String reconciliationDate) {
        this.reconciliationDate = reconciliationDate;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "{" +
                "\"client_identifier\":\"" + clientIdentifier + "\"," +
                "\"instrument\":\"" + instrument + "\"," +
                "\"reconciliation_date\":\"" + reconciliationDate + "\"," +
                "\"total_count\":" + totalCount + "," +
                "\"total_amount\":" + totalAmount +
                "}";
    }
}

