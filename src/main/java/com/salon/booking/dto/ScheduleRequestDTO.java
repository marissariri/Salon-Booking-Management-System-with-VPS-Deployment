package com.salon.booking.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleRequestDTO {
    @NotNull(message = "Service ID is required")
    private String serviceId;
    
    @NotNull(message = "Beautician ID is required")
    private String beauticianId;
    
    @NotNull(message = "Schedule date is required")
    private LocalDate scheduleDate;
    
    @NotNull(message = "Start time is required")
    private LocalTime startTime;
    
    @NotNull(message = "End time is required")
    private LocalTime endTime;
    
    @NotNull(message = "Max slot is required")
    private Integer maxSlot;
}
