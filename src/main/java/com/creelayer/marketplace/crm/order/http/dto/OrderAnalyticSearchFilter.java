package com.creelayer.marketplace.crm.order.http.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Setter
public class OrderAnalyticSearchFilter {

    public enum Period {
        DAY, WEEK, MONTH
    }

    @NotNull
    public Period period;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate from;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate to;

}
