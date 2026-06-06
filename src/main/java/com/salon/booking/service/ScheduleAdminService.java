package com.salon.booking.service;

import com.salon.booking.dto.ScheduleRequestDTO;
import com.salon.booking.entity.Beautician;
import com.salon.booking.entity.BeautyService;
import com.salon.booking.entity.Schedule;
import com.salon.booking.exception.ResourceNotFoundException;
import com.salon.booking.repository.BeauticianRepository;
import com.salon.booking.repository.BeautyServiceRepository;
import com.salon.booking.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleAdminService {

    private final ScheduleRepository scheduleRepository;
    private final BeautyServiceRepository serviceRepository;
    private final BeauticianRepository beauticianRepository;

    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    public Schedule create(ScheduleRequestDTO request) {
        BeautyService service = serviceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));
                
        Beautician beautician = beauticianRepository.findById(request.getBeauticianId())
                .orElseThrow(() -> new ResourceNotFoundException("Beautician not found"));

        Schedule schedule = new Schedule();
        schedule.setBeautyService(service);
        schedule.setBeautician(beautician);
        schedule.setScheduleDate(request.getScheduleDate());
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        schedule.setMaxSlot(request.getMaxSlot());
        schedule.setAvailableSlot(request.getMaxSlot());

        return scheduleRepository.save(schedule);
    }
}
