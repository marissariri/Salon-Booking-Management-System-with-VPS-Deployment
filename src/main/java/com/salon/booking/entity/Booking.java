package com.salon.booking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

@Entity
@Table(name = "bookings")
@Getter
@Setter
public class Booking {
    @Id
    private String id;

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray(), 10);
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @Column(name = "service_name", nullable = false, length = 100)
    private String serviceName;

    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status", length = 20, columnDefinition = "ENUM('PENDING_PAYMENT', 'CONFIRMED', 'CANCELLED', 'COMPLETED')")
    private BookingStatus bookingStatus = BookingStatus.PENDING_PAYMENT;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    public enum BookingStatus {
        PENDING_PAYMENT, CONFIRMED, CANCELLED, COMPLETED
    }
}
