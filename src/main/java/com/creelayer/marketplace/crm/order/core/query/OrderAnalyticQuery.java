package com.creelayer.marketplace.crm.order.core.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class OrderAnalyticQuery {

    public enum Period {
        DAY, WEEK, MONTH
    }

    public UUID realm;

    public Period period;

    public LocalDate from;

    public LocalDate to;

}
