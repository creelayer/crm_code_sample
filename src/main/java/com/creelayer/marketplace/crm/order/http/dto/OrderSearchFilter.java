package com.creelayer.marketplace.crm.order.http.dto;

import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Setter
public class OrderSearchFilter {

    public enum PayoutStatus {
        NONE, REQUIRED, COMPLETE
    }

    public UUID customer;

    @Length(max = 255)
    public String search;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate from;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate to;

    public List<@Length(max = 255) String> status;

    public PayoutStatus payout;
}
