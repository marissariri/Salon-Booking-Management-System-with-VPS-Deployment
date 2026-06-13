package com.salon.booking.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookingDashboardDTO {
    private String id;
    private String userName;
    private String serviceName;
    private LocalDate bookingDate;
    private String bookingStatus;
    private LocalDateTime createdAt;
}
