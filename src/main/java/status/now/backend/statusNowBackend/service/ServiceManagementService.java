package status.now.backend.statusNowBackend.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import status.now.backend.statusNowBackend.DTO.ServiceMetrics;
import status.now.backend.statusNowBackend.Entity.IncidentEntity;
import status.now.backend.statusNowBackend.Entity.MaintenanceSchedule;
import status.now.backend.statusNowBackend.Entity.ServicesEntity;
import status.now.backend.statusNowBackend.repositories.IncidentRepository;
import status.now.backend.statusNowBackend.repositories.MaintenanceScheduleRepository;
import status.now.backend.statusNowBackend.repositories.ServiceRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceManagementService {

    @Autowired
    private ServiceRepository serviceRepository;


    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private MaintenanceScheduleRepository maintenanceScheduleRepository;

    public List<ServicesEntity> getAllServices() {
        return serviceRepository.findAll();
    }

    public Optional<ServicesEntity> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    public ServicesEntity createService(ServicesEntity service) {
        return serviceRepository.save(service);
    }

    public ServicesEntity updateService(Long id, ServicesEntity serviceDetails) {
        ServicesEntity service = serviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Service not found"));
        service.setName(serviceDetails.getName());
        service.setType(serviceDetails.getType());
        service.setUrl(serviceDetails.getUrl());
        service.setDescription(serviceDetails.getDescription());
        return serviceRepository.save(service);
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }

    public ServiceMetrics getServiceMetrics(Long serviceId, LocalDateTime startTime, LocalDateTime endTime) {
        Optional<ServicesEntity> serviceOpt = serviceRepository.findById(serviceId);
        if (serviceOpt.isPresent()) {
            ServicesEntity service = serviceOpt.get();

            // Calculate active incidents count
            int activeIncidentsCount = incidentRepository.countByServiceAndStatus(service, "OPEN");

            // Count scheduled maintenance
            int scheduledMaintenanceCount = maintenanceScheduleRepository.countByService(service);

            // Calculate uptime percentage (dummy value for demonstration)
            double uptimePercentage = calculateUptimePercentage(service, startTime, endTime);

            // Fetch incidents in the specified time frame
            List<IncidentEntity> incidents = incidentRepository.findByServiceAndCreatedAtBetween(service, startTime, endTime);

            // Fetch maintenance schedules in the specified time frame
            List<MaintenanceSchedule> maintenanceSchedules = maintenanceScheduleRepository.findByServiceAndScheduledTimeBetween(service, startTime, endTime);

            return new ServiceMetrics(service.getId(), service.getName(), activeIncidentsCount, scheduledMaintenanceCount,
                    uptimePercentage, incidents, maintenanceSchedules);
        } else {
            throw new RuntimeException("Service not found");
        }
    }

    // Dummy implementation to calculate uptime percentage
    private double calculateUptimePercentage(ServicesEntity service, LocalDateTime startTime, LocalDateTime endTime) {
        // Implement actual logic to calculate uptime based on incidents and maintenance schedules
        // This is just a placeholder value
        return 99.9; // Example uptime
    }
}