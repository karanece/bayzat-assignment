package com.bayzdelivery.model;

import java.math.BigDecimal;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;

public interface DeliveryPerson {
    Long getId();

    String getName();

    @Value("#{target.commission}")
    BigDecimal getCommission();

    @Value("#{target.avg_commission}")
    BigDecimal getAvgCommission();

    @Value("#{target.start_time}")
    Instant getStartTime();

    @Value("#{target.end_time}")
    Instant getEndTime();

    // The below methods are added for unit test

    void setId(final Long Id);

    void setName(final String name);

    void setCommission(final BigDecimal commission);

    void setAvgCommission(final BigDecimal avgCommission);

    void setStartTime(final Instant startTime);

    void setEndTime(final Instant endTime);
}
