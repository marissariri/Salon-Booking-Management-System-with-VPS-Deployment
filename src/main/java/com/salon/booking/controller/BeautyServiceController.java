package com.salon.booking.controller;

import com.salon.booking.entity.BeautyService;
import com.salon.booking.service.MasterBeautyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/services")
@RequiredArgsConstructor
public class BeautyServiceController {

    private final MasterBeautyService service;

    @GetMapping
    public List<BeautyService> getAll() {
        return service.getAllServices();
    }

    @GetMapping("/{id}")
    public BeautyService getById(@PathVariable Long id) {
        return service.getServiceById(id);
    }

    @PostMapping
    public ResponseEntity<BeautyService> create(@RequestBody BeautyService beautyService) {
        return new ResponseEntity<>(service.createService(beautyService), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public BeautyService update(@PathVariable Long id, @RequestBody BeautyService beautyService) {
        return service.updateService(id, beautyService);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
