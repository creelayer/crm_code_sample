package com.creelayer.marketplace.crm.order.http.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Setter
public class OrderAnalyticSearchFilter {


    @NotNull
    @Pattern(regexp="^(DAY|WEEK|MONTH)$",message="Invalid period")
    public String period;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate from;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate to;

}
