package com.dtt.service.iface;

import com.dtt.responsedto.ApiResponse;

public interface LogMetricsIface {
    public ApiResponse getPaymentMetrics();

    public ApiResponse getTransactionCount();
}
