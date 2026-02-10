package com.dtt.responsedto;

import java.math.BigDecimal;

public class paymentsMetricDto {
    private BigDecimal totalCollection;

    private BigDecimal verificationFee;

    private BigDecimal platformFee;

    private BigDecimal vatCollected;

    private Integer debitTransactions;

    private BigDecimal thisMonthRevenue;

    private BigDecimal totalTopups;

    private BigDecimal thisMonthTotalTopups;

    private Integer activeProviders;

    public BigDecimal getTotalCollection() {
        return totalCollection;
    }

    public void setTotalCollection(BigDecimal totalCollection) {
        this.totalCollection = totalCollection;
    }

    public BigDecimal getVerificationFee() {
        return verificationFee;
    }

    public void setVerificationFee(BigDecimal verificationFee) {
        this.verificationFee = verificationFee;
    }

    public BigDecimal getPlatformFee() {
        return platformFee;
    }

    public void setPlatformFee(BigDecimal platformFee) {
        this.platformFee = platformFee;
    }

    public BigDecimal getVatCollected() {
        return vatCollected;
    }

    public void setVatCollected(BigDecimal vatCollected) {
        this.vatCollected = vatCollected;
    }

    public Integer getDebitTransactions() {
        return debitTransactions;
    }

    public void setDebitTransactions(Integer debitTransactions) {
        this.debitTransactions = debitTransactions;
    }

    public BigDecimal getThisMonthRevenue() {
        return thisMonthRevenue;
    }

    public void setThisMonthRevenue(BigDecimal thisMonthRevenue) {
        this.thisMonthRevenue = thisMonthRevenue;
    }

    public BigDecimal getTotalTopups() {
        return totalTopups;
    }

    public void setTotalTopups(BigDecimal totalTopups) {
        this.totalTopups = totalTopups;
    }

    public Integer getActiveProviders() {
        return activeProviders;
    }

    public void setActiveProviders(Integer activeProviders) {
        this.activeProviders = activeProviders;
    }

    public BigDecimal getThisMonthTotalTopups() {
        return thisMonthTotalTopups;
    }

    public void setThisMonthTotalTopups(BigDecimal thisMonthTotalTopups) {
        this.thisMonthTotalTopups = thisMonthTotalTopups;
    }

    @Override
    public String toString() {
        return "{" +
                "totalCollection=" + totalCollection +
                ", verificationFee=" + verificationFee +
                ", platformFee=" + platformFee +
                ", vatCollected=" + vatCollected +
                ", debitTransactions=" + debitTransactions +
                ", thisMonthRevenue=" + thisMonthRevenue +
                ", totalTopups=" + totalTopups +
                ", thisMonthTotalTopups=" + thisMonthTotalTopups +
                ", activeProviders=" + activeProviders +
                '}';
    }
}
