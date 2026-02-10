package com.dtt.service.impl;

import com.dtt.event.MetricsUpdatedEvent;
import com.dtt.repo.PaymentHistoryRepo;
import com.dtt.responsedto.*;
import com.dtt.service.iface.LogMetricsIface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class LogMetricsIfaceImpl implements LogMetricsIface {

    @Autowired
    PaymentHistoryRepo paymentHistoryRepo;

    @Autowired
    private ApplicationEventPublisher eventPublisher;
    

    @Override
    public ApiResponse getPaymentMetrics() {
        BigDecimal paymentHistorySum = paymentHistoryRepo.getAllFeeSum();
        List<Object[]> resultList = paymentHistoryRepo.fetchSums();
        BigDecimal vat = BigDecimal.ZERO;
        BigDecimal platform = BigDecimal.ZERO;
        BigDecimal verification = BigDecimal.ZERO;

        if (resultList != null && !resultList.isEmpty()) {
            System.out.println(resultList.stream().toString());
            Object[] row = resultList.get(0); // This is the actual row of data

            // Now you can safely access index 0, 1, and 2
            vat = (row[0] != null) ? (BigDecimal) row[0] : BigDecimal.ZERO;
            platform = (row[1] != null) ? (BigDecimal) row[1] : BigDecimal.ZERO;
            verification = (row[2] != null) ? (BigDecimal) row[2] : BigDecimal.ZERO;
        }
        long debitTransaction = paymentHistoryRepo.countSuccessfulDebits();
        BigDecimal thisMonthRevenue = paymentHistoryRepo.sumAmountForCurrentMonth();
        BigDecimal totalTopUps = paymentHistoryRepo.sumCreditAmount();
        BigDecimal thisMonthTotalTopUps = paymentHistoryRepo.sumAmountForCurrentMonthTopup();
        long activeProviders = paymentHistoryRepo.countUniqueServiceProviders();

        paymentsMetricDto paymentsMetric = new paymentsMetricDto();
        paymentsMetric.setTotalCollection(paymentHistorySum);
        paymentsMetric.setVatCollected(vat);
        paymentsMetric.setVerificationFee(verification);
        paymentsMetric.setPlatformFee(platform);
        paymentsMetric.setDebitTransactions((int) debitTransaction);
        paymentsMetric.setThisMonthRevenue(thisMonthRevenue);
        paymentsMetric.setTotalTopups(totalTopUps);
        paymentsMetric.setThisMonthTotalTopups(thisMonthTotalTopUps);
        paymentsMetric.setActiveProviders((int) activeProviders);
        //----------------------------------------------------------------------
        // 1. Calculate the start date (11 months ago + current month = 12 months)
        YearMonth currentMonth = YearMonth.now();
        YearMonth startMonth = currentMonth.minusMonths(11);

// 2. Initialize a Map with the last 12 months set to zero
// Using LinkedHashMap ensures the "YYYY-MM" order is preserved
        Map<String, MonthlyBreakdownDto> monthlyMap = new LinkedHashMap<>();

        for (int i = 0; i < 12; i++) {
            YearMonth month = startMonth.plusMonths(i);
            String monthKey = month.toString(); // Formats as "2025-02"

            // Create a "Zero" DTO for every month
            //monthlyMap.put(monthKey, new MonthlyBreakdownDto(monthKey, 0.0, 0.0, 0.0, 0.0));
            monthlyMap.put(monthKey, new MonthlyBreakdownDto(monthKey, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO));
        }

// 3. Get actual data from DB
        Instant startDateInstant = startMonth.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
        List<Object[]> rawData = paymentHistoryRepo.findMonthlyBreakdown(startDateInstant);

// 4. Overwrite the "Zero" DTOs with actual DB data
        for (Object[] row : rawData) {
            int year = ((Number) row[0]).intValue();
            int monthValue = ((Number) row[1]).intValue();
            String monthKey = String.format("%d-%02d", year, monthValue);

            if (monthlyMap.containsKey(monthKey)) {
                monthlyMap.put(monthKey, new MonthlyBreakdownDto(
                        monthKey,
//                        ((Number) row[2]).doubleValue(), // verification
//                        ((Number) row[4]).doubleValue(), // vat
//                        ((Number) row[3]).doubleValue(), // platform
//                        ((Number) row[5]).doubleValue(),  // total

                        row[2] != null ? (BigDecimal) row[2] : BigDecimal.ZERO,
                        row[3] != null ? (BigDecimal) row[3] : BigDecimal.ZERO,
                        row[4] != null ? (BigDecimal) row[4] : BigDecimal.ZERO,
                        row[5] != null ? (BigDecimal) row[5] : BigDecimal.ZERO,
                        row[6] != null ? (BigDecimal) row[6] : BigDecimal.ZERO,
                        row[7] != null ? (BigDecimal) row[7] : BigDecimal.ZERO
                ));
            }
        }

// 5. Convert map values back to a list (sorted by date)
        List<MonthlyBreakdownDto> monthlyBreakdown = new ArrayList<>(monthlyMap.values());


        List<Object[]> providerData = paymentHistoryRepo.findTopProviders();

        List<ProviderBucketDto> buckets = providerData.stream().map(row -> {
            return new ProviderBucketDto(
                    (String) row[0],                  // organization_id
                    ((Number) row[1]).doubleValue(),  // wallet_topup_amount
                    ((Number) row[2]).intValue()      // transactions (count)
            );
        }).toList();

// Wrap in the container DTO to match your JSON structure
        TopProvidersDto topProviders = new TopProvidersDto(buckets);

        ChartResponseDto chartResponseDto = new ChartResponseDto(null, monthlyBreakdown, topProviders, paymentsMetric);


        return new ApiResponse(true, "Metrics", chartResponseDto);

    }

    @Override
    public ApiResponse getTransactionCount() {
        transactionCount transactionCount = new transactionCount();
        List<Object[]> results = paymentHistoryRepo.getTransactionCounts();

        long transactionsCount = 0;
        long creditCount = 0;
        long debitCount = 0;

        if (results != null && !results.isEmpty()) {
            // This is where you were missing a step:
            // You must get the first Object[] row out of the List
            Object[] row = results.get(0);

            transactionsCount = (row[0] != null) ? ((Number) row[0]).longValue() : 0L;
            creditCount       = (row[1] != null) ? ((Number) row[1]).longValue() : 0L;
            debitCount        = (row[2] != null) ? ((Number) row[2]).longValue() : 0L;
        }
        transactionCount.setTransactionsCount(transactionsCount);
        transactionCount.setCreditCount(creditCount);
        transactionCount.setDebitCount(debitCount);
        return new ApiResponse(true,"Metrics",transactionCount);
    }

//publishMetricsUpdate() add this where paymentHistory saved
    public void publishMetricsUpdate() {
        // 1. Get the latest metrics using your existing logic
        ApiResponse latestMetrics = this.getPaymentMetrics();

        // 2. Publish the event
        eventPublisher.publishEvent(new MetricsUpdatedEvent(this, latestMetrics));
    }


}
