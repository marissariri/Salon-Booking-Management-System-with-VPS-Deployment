package com.salon.booking.service;

import com.salon.booking.entity.Beautician;
import com.salon.booking.exception.ResourceNotFoundException;
import com.salon.booking.repository.BeauticianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BeauticianService {

    private final BeauticianRepository repository;

    public List<Beautician> getAll() {
        return repository.findAll();
    }

    public Beautician getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beautician not found"));
    }

    public Beautician create(Beautician beautician) {
        return repository.save(beautician);
    }

    public Beautician update(Long id, Beautician details) {
        Beautician beautician = getById(id);
        beautician.setFullName(details.getFullName());
        beautician.setSpecialty(details.getSpecialty());
        beautician.setStatus(details.getStatus());
        return repository.save(beautician);
    }

    public void delete(Long id) {
        Beautician beautician = getById(id);
        repository.delete(beautician);
    }
}
