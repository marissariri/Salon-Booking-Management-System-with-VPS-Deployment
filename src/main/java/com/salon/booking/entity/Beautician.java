package com.salon.booking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

@Entity
@Table(name = "beauticians")
@Getter
@Setter
public class Beautician {
    @Id
    private String id;

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray(), 10);
        }
    }

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(length = 100)
    private String specialty;

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
