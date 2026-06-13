package com.salon.booking.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentDashboardDTO {
    private String id;
    private String bookingId;
    private BigDecimal amount;
    private String paymentStatus;
    private String paymentMethod;
    private LocalDateTime paymentDate;
}
