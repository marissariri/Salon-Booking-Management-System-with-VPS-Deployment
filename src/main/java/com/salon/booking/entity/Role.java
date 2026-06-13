package com.salon.booking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
    @Id
    private String id;

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray(), 10);
        }
    }

    @Column(name = "role_name", nullable = false, unique = true, length = 50)
    private String roleName;
}
