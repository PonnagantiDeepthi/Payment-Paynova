package com.dtt.responsedto;

public class ProviderBucketDto {

    private String organization_id;
    private Double wallet_topup_amount;
    private Integer transactions;

    public ProviderBucketDto(String s, Double v, Integer i) {
        this.organization_id = s;
        this.wallet_topup_amount = v;
        this.transactions = i;
    }

    public String getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(String organization_id) {
        this.organization_id = organization_id;
    }

    public Double getWallet_topup_amount() {
        return wallet_topup_amount;
    }

    public void setWallet_topup_amount(Double wallet_topup_amount) {
        this.wallet_topup_amount = wallet_topup_amount;
    }

    public Integer getTransactions() {
        return transactions;
    }

    public void setTransactions(Integer transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "ProviderBucketDto{" +
                "organization_id='" + organization_id + '\'' +
                ", wallet_topup_amount=" + wallet_topup_amount +
                ", transactions=" + transactions +
                '}';
    }
}
