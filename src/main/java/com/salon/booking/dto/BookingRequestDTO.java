package com.salon.booking.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class BookingRequestDTO {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Schedule ID is required")
    private Long scheduleId;

    private String notes;
}
