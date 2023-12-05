package com.creelayer.marketplace.crm.order.core.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class OrderAnalyticSearchQuery {

    public LocalDate from;

    public LocalDate to;

}
