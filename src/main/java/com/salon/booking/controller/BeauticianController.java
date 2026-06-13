package com.salon.booking.controller;

import com.salon.booking.dto.BeauticianRequestDTO;
import com.salon.booking.dto.BeauticianResponseDTO;
import com.salon.booking.service.BeauticianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beauticians")
@RequiredArgsConstructor
public class BeauticianController {

    private final BeauticianService service;

    @GetMapping
    public List<BeauticianResponseDTO> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<BeauticianResponseDTO> create(@RequestBody BeauticianRequestDTO beautician) {
        return new ResponseEntity<>(service.create(beautician), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public BeauticianResponseDTO update(@PathVariable String id, @RequestBody BeauticianRequestDTO beautician) {
        return service.update(id, beautician);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
