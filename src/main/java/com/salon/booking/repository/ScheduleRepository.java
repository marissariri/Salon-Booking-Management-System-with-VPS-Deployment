package com.salon.booking.repository;

import com.salon.booking.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Lock;
import jakarta.persistence.LockModeType;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {
    List<Schedule> findByScheduleDateAndAvailableSlotGreaterThan(LocalDate date, Integer minSlot);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Schedule s WHERE s.id = :id")
    Optional<Schedule> findByIdWithPessimisticLock(@Param("id") String id);
}
