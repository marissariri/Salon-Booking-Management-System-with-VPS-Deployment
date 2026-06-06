package com.salon.booking.controller;

import com.salon.booking.entity.Beautician;
import com.salon.booking.service.BeauticianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/beauticians")
@RequiredArgsConstructor
public class BeauticianController {

    private final BeauticianService service;

    @GetMapping
    public List<Beautician> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<Beautician> create(@RequestBody Beautician beautician) {
        return new ResponseEntity<>(service.create(beautician), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Beautician update(@PathVariable Long id, @RequestBody Beautician beautician) {
        return service.update(id, beautician);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
