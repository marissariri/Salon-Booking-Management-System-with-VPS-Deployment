package com.salon.booking.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BookingResponseDTO {
    private String bookingId;
    private String serviceName;
    private String beauticianName;
    private LocalDate bookingDate;
    private String bookingStatus;
    private String message;
}
