package com.dtt.responsedto;

public class transactionCount {
    private long transactionsCount;
    private long creditCount;
    private long debitCount;

    public long getTransactionsCount() {
        return transactionsCount;
    }

    public void setTransactionsCount(long transactionsCount) {
        this.transactionsCount = transactionsCount;
    }

    public long getCreditCount() {
        return creditCount;
    }

    public void setCreditCount(long creditCount) {
        this.creditCount = creditCount;
    }

    public long getDebitCount() {
        return debitCount;
    }

    public void setDebitCount(long debitCount) {
        this.debitCount = debitCount;
    }

    @Override
    public String toString() {
        return "transactionCount{" +
                "transactionsCount=" + transactionsCount +
                ", creditCount=" + creditCount +
                ", debitCount=" + debitCount +
                '}';
    }
}
