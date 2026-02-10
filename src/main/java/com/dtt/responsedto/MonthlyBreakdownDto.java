package com.dtt.responsedto;

import java.math.BigDecimal;

public class MonthlyBreakdownDto {
    private String month;
    private BigDecimal verification_fee;
    private BigDecimal vat;
    private BigDecimal platform_fee;
    private BigDecimal total;
    private BigDecimal totalDebit;
    private BigDecimal totalCredit;

    public MonthlyBreakdownDto(String yearMonth, BigDecimal v, BigDecimal v1, BigDecimal v2, BigDecimal v3, BigDecimal totalDebit,
                               BigDecimal totalCredit) {
        this.month = yearMonth;
        this.verification_fee = v;
        this.vat = v1;
        this.platform_fee = v2;
        this.total = v3;
        this.totalDebit = totalDebit;
        this.totalCredit = totalCredit;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getVerification_fee() {
        return verification_fee;
    }

    public void setVerification_fee(BigDecimal verification_fee) {
        this.verification_fee = verification_fee;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public BigDecimal getPlatform_fee() {
        return platform_fee;
    }

    public void setPlatform_fee(BigDecimal platform_fee) {
        this.platform_fee = platform_fee;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(BigDecimal totalDebit) {
        this.totalDebit = totalDebit;
    }

    public BigDecimal getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(BigDecimal totalCredit) {
        this.totalCredit = totalCredit;
    }

    @Override
    public String toString() {
        return "{" +
                "month='" + month + '\'' +
                ", verification_fee=" + verification_fee +
                ", vat=" + vat +
                ", platform_fee=" + platform_fee +
                ", total=" + total +
                ", totalDebit=" + totalDebit +
                ", totalCredit=" + totalCredit +
                '}';
    }
}
