package com.salon.booking.controller;

import com.salon.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/bookings")
@RequiredArgsConstructor
public class AdminBookingController {

    private final BookingService bookingService;

    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> cancelBookingManual(@PathVariable String id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok("Booking " + id + " has been successfully cancelled by Admin.");
    }
}
