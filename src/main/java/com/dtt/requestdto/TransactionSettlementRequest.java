package com.dtt.requestdto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionSettlementRequest {

    @JsonProperty("client_identifier")
    private String clientIdentifier;

    @JsonProperty("instrument")
    private String instrument;   // Card / Wallet / All

    @JsonProperty("reconciliation_date")
    private String reconciliationDate; // yyyy-MM-dd

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

    @Override
    public String toString() {
        return "{" +
                "\"client_identifier\":\"" + clientIdentifier + "\"," +
                "\"instrument\":\"" + instrument + "\"," +
                "\"reconciliation_date\":\"" + reconciliationDate + "\"" +
                "}";
    }
}

