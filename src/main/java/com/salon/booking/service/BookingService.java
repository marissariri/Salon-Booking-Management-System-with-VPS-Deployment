package com.salon.booking.service;

import com.salon.booking.dto.BookingRequestDTO;
import com.salon.booking.dto.BookingResponseDTO;
import com.salon.booking.entity.*;
import com.salon.booking.exception.ResourceNotFoundException;
import com.salon.booking.exception.SlotFullException;
import com.salon.booking.repository.BookingRepository;
import com.salon.booking.repository.PaymentRepository;
import com.salon.booking.repository.ScheduleRepository;
import com.salon.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Schedule schedule = scheduleRepository.findByIdWithPessimisticLock(request.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));

        // 1. Cek Ketersediaan Jadwal (Concurrency Check)
        if (schedule.getAvailableSlot() <= 0) {
            log.warn("Booking failed: Schedule {} is already full for user {}", schedule.getId(), user.getId());
            throw new SlotFullException("Maaf, slot jadwal ini baru saja penuh. Silakan pilih jadwal lain.");
        }

        // 2. Kurangi Slot Sementara
        schedule.setAvailableSlot(schedule.getAvailableSlot() - 1);
        scheduleRepository.save(schedule);

        // 3. Buat Booking dengan status PENDING_PAYMENT
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSchedule(schedule);
        booking.setServiceName(schedule.getBeautyService().getServiceName());
        booking.setBookingDate(schedule.getScheduleDate());
        booking.setBookingStatus(Booking.BookingStatus.PENDING_PAYMENT);
        booking.setNotes(request.getNotes());
        booking = bookingRepository.save(booking);

        // 4. Buat tagihan Payment awal
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(schedule.getBeautyService().getPrice());
        payment.setPaymentStatus(Payment.PaymentStatus.PENDING);
        paymentRepository.save(payment);

        // 5. Siapkan Response
        BookingResponseDTO response = new BookingResponseDTO();
        response.setBookingId(booking.getId());
        response.setServiceName(booking.getServiceName());
        response.setBeauticianName(schedule.getBeautician().getFullName());
        response.setBookingDate(booking.getBookingDate());
        response.setBookingStatus(booking.getBookingStatus().name());
        response.setMessage("Booking berhasil diamankan. Silakan lanjutkan ke pembayaran.");

        log.info("Booking created successfully with ID: {} for user: {}", booking.getId(), user.getId());

        return response;
    }

    @Transactional
    public void cancelBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (booking.getBookingStatus() == Booking.BookingStatus.CANCELLED) {
            throw new IllegalStateException("Booking is already cancelled");
        }

        if (booking.getBookingStatus() == Booking.BookingStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel a completed booking");
        }

        // Restore available slot
        Schedule schedule = booking.getSchedule();
        schedule.setAvailableSlot(schedule.getAvailableSlot() + 1);
        scheduleRepository.save(schedule);

        // Update booking status
        booking.setBookingStatus(Booking.BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        log.info("Booking {} has been cancelled and slot restored.", bookingId);
    }
}
