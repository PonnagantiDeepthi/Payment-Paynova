package com.dtt.event;

import com.dtt.responsedto.ApiResponse;
import org.springframework.context.ApplicationEvent;

public class MetricsUpdatedEvent extends ApplicationEvent {
    private final ApiResponse metrics;

    public MetricsUpdatedEvent(Object source, ApiResponse metrics) {
        super(source);
        this.metrics = metrics;
    }

    public ApiResponse getMetrics() {
        return metrics;
    }
}
