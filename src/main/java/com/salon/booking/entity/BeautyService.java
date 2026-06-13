package com.salon.booking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

@Entity
@Table(name = "services")
@Getter
@Setter
public class BeautyService {
    @Id
    private String id;

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray(), 10);
        }
    }

    @Column(name = "service_name", nullable = false, length = 100)
    private String serviceName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, columnDefinition = "ENUM('ACTIVE', 'INACTIVE')")
    private Status status = Status.ACTIVE;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;
    
    public enum Status {
        ACTIVE, INACTIVE
    }
}
