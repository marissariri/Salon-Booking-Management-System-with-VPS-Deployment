package com.salon.booking.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class PaymentRequestDTO {
    @NotNull(message = "Booking ID is required")
    private Long bookingId;

    private String paymentMethod;
    
    @NotNull(message = "Amount is required")
    private BigDecimal amount;
    
    // Status to simulate payment gateway response
    private boolean paymentSuccess;
}
