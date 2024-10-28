package status.now.backend.statusNowBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import status.now.backend.statusNowBackend.Entity.MaintenanceSchedule;
import status.now.backend.statusNowBackend.repositories.MaintenanceScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MaintenanceScheduleManagementService {

    @Autowired
    private MaintenanceScheduleRepository maintenanceScheduleRepository;

    public List<MaintenanceSchedule> getAllSchedules() {
        return maintenanceScheduleRepository.findAll();
    }
    public List<MaintenanceSchedule> getSchedulesByServiceId(Long serviceId) {
        return maintenanceScheduleRepository.findByServiceId(serviceId);
    }

    public MaintenanceSchedule createSchedule(MaintenanceSchedule schedule) {
        schedule.setCreatedAt(LocalDateTime.now());
        return maintenanceScheduleRepository.save(schedule);
    }

}