package com.dtt.responsedto;

public class SevenDayTrendDto {

    private String date;
    private Double success_amount;
    private Double failed_amount;

    public SevenDayTrendDto(String string, double v, double v1) {
        this.date = date;
        this.success_amount = v;
        this.failed_amount = v1;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getSuccess_amount() {
        return success_amount;
    }

    public void setSuccess_amount(Double success_amount) {
        this.success_amount = success_amount;
    }

    public Double getFailed_amount() {
        return failed_amount;
    }

    public void setFailed_amount(Double failed_amount) {
        this.failed_amount = failed_amount;
    }

    @Override
    public String toString() {
        return "SevenDayTrendDto{" +
                "date='" + date + '\'' +
                ", success_amount=" + success_amount +
                ", failed_amount=" + failed_amount +
                '}';
    }
}
