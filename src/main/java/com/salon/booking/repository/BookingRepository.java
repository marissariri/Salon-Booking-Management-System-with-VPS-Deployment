package com.salon.booking.repository;

import com.salon.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findByUserId(Long userId);
}
