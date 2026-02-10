package com.dtt.responsedto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ChartResponseDto {
    private List<SevenDayTrendDto> seven_day_trends;
    private List<MonthlyBreakdownDto> monthly_breakdown;
    private TopProvidersDto top_providers;

    @JsonProperty("payment_summary")
    private paymentsMetricDto paymentsMetric;

    public ChartResponseDto(List<SevenDayTrendDto> o, List<MonthlyBreakdownDto> monthlyBreakdown, TopProvidersDto topProviders,paymentsMetricDto paymentsMetric) {
        this.seven_day_trends =o;
        this.monthly_breakdown =monthlyBreakdown;
        this.top_providers = topProviders;
        this.paymentsMetric = paymentsMetric;
    }

    public List<SevenDayTrendDto> getSeven_day_trends() {
        return seven_day_trends;
    }

    public void setSeven_day_trends(List<SevenDayTrendDto> seven_day_trends) {
        this.seven_day_trends = seven_day_trends;
    }

    public List<MonthlyBreakdownDto> getMonthly_breakdown() {
        return monthly_breakdown;
    }

    public void setMonthly_breakdown(List<MonthlyBreakdownDto> monthly_breakdown) {
        this.monthly_breakdown = monthly_breakdown;
    }

    public TopProvidersDto getTop_providers() {
        return top_providers;
    }

    public void setTop_providers(TopProvidersDto top_providers) {
        this.top_providers = top_providers;
    }

    @JsonIgnore
    public paymentsMetricDto getPaymentsMetricDto() {
        return paymentsMetric;
    }

    public void setPaymentsMetricDto(paymentsMetricDto paymentsMetric) {
        this.paymentsMetric = paymentsMetric;
    }

    @Override
    public String toString() {
        return "ChartResponseDto{" +
                "seven_day_trends=" + seven_day_trends +
                ", monthly_breakdown=" + monthly_breakdown +
                ", top_providers=" + top_providers +
                ", paymentsMetric=" + paymentsMetric +
                '}';
    }
}
