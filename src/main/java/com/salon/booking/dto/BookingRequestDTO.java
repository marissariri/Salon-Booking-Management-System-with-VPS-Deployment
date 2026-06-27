package com.salon.booking.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class BookingRequestDTO {
    @NotNull(message = "Schedule ID is required")
    private String scheduleId;

    private String notes;
}
