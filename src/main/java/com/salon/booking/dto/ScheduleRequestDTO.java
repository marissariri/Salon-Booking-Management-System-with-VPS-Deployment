package com.salon.booking.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleRequestDTO {
    private String serviceId;
    private String beauticianId;
    private LocalDate scheduleDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer maxSlot;
}
