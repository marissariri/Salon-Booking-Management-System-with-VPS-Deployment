package com.salon.booking.service;

import com.salon.booking.entity.BeautyService;
import com.salon.booking.exception.ResourceNotFoundException;
import com.salon.booking.repository.BeautyServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MasterBeautyService {

    private final BeautyServiceRepository repository;

    public List<BeautyService> getAllServices() {
        return repository.findAll();
    }

    public BeautyService getServiceById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));
    }

    public BeautyService createService(BeautyService service) {
        return repository.save(service);
    }

    public BeautyService updateService(Long id, BeautyService serviceDetails) {
        BeautyService service = getServiceById(id);
        service.setServiceName(serviceDetails.getServiceName());
        service.setDescription(serviceDetails.getDescription());
        service.setPrice(serviceDetails.getPrice());
        service.setDurationMinutes(serviceDetails.getDurationMinutes());
        service.setStatus(serviceDetails.getStatus());
        return repository.save(service);
    }

    public void deleteService(Long id) {
        BeautyService service = getServiceById(id);
        repository.delete(service);
    }
}
