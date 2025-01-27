package org.romanconversion.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CustomMetrics {

    private final Counter conversionCounter;

    public CustomMetrics(MeterRegistry meterRegistry) {
        // Define a counter to count the number of successful conversions
        this.conversionCounter = Counter.builder("conversion.success.count")
                .description("Number of successful Roman numeral conversions")
                .register(meterRegistry);

    }

    // Method to increment the conversion counter
    public void incrementConversionCounter() {
        conversionCounter.increment();
    }
}

