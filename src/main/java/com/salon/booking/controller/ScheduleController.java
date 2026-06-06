package com.salon.booking.controller;

import com.salon.booking.dto.ScheduleRequestDTO;
import com.salon.booking.entity.Schedule;
import com.salon.booking.service.ScheduleAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleAdminService service;

    @GetMapping
    public List<Schedule> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<Schedule> create(@RequestBody ScheduleRequestDTO scheduleRequestDTO) {
        return new ResponseEntity<>(service.create(scheduleRequestDTO), HttpStatus.CREATED);
    }
}
