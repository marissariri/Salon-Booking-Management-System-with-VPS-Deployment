package com.salon.booking.repository;

import com.salon.booking.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByScheduleDateAndAvailableSlotGreaterThan(LocalDate date, Integer minSlot);
}
