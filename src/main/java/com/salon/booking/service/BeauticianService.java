package com.salon.booking.service;

import com.salon.booking.dto.BeauticianRequestDTO;
import com.salon.booking.dto.BeauticianResponseDTO;
import com.salon.booking.entity.Beautician;
import com.salon.booking.exception.ResourceNotFoundException;
import com.salon.booking.repository.BeauticianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeauticianService {

    private final BeauticianRepository repository;

    public List<BeauticianResponseDTO> getAll() {
        return repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Beautician getById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beautician not found"));
    }

    public BeauticianResponseDTO create(BeauticianRequestDTO request) {
        Beautician beautician = new Beautician();
        beautician.setFullName(request.getFullName());
        beautician.setSpecialty(request.getSpecialty());
        beautician.setStatus(request.getStatus() != null ? Beautician.Status.valueOf(request.getStatus().toUpperCase()) : Beautician.Status.ACTIVE);
        return mapToDTO(repository.save(beautician));
    }

    public BeauticianResponseDTO update(String id, BeauticianRequestDTO request) {
        Beautician beautician = getById(id);
        beautician.setFullName(request.getFullName());
        beautician.setSpecialty(request.getSpecialty());
        if (request.getStatus() != null) {
            beautician.setStatus(Beautician.Status.valueOf(request.getStatus().toUpperCase()));
        }
        return mapToDTO(repository.save(beautician));
    }

    public void delete(String id) {
        Beautician beautician = getById(id);
        repository.delete(beautician);
    }

    private BeauticianResponseDTO mapToDTO(Beautician entity) {
        BeauticianResponseDTO dto = new BeauticianResponseDTO();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setSpecialty(entity.getSpecialty());
        dto.setStatus(entity.getStatus() != null ? entity.getStatus().name() : null);
        return dto;
    }
}
