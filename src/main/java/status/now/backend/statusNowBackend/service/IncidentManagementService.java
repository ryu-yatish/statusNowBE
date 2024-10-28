package status.now.backend.statusNowBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import status.now.backend.statusNowBackend.Entity.IncidentEntity;
import status.now.backend.statusNowBackend.repositories.IncidentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncidentManagementService {

    @Autowired
    private IncidentRepository incidentRepository;

    public List<IncidentEntity> getAllIncidents() {
        return incidentRepository.findAll();
    }
    public List<IncidentEntity> getIncidentsByServiceId(Long serviceId) {
        return incidentRepository.findByServiceId(serviceId);
    }

    public IncidentEntity createIncident(IncidentEntity incident) {
        incident.setCreatedAt(LocalDateTime.now());
        incident.setStatus("OPEN"); // Default status
        return incidentRepository.save(incident);
    }

    public IncidentEntity updateIncident(Long id, IncidentEntity incidentDetails) {
        IncidentEntity incident = incidentRepository.findById(id).orElseThrow(() -> new RuntimeException("Incident not found"));
        incident.setDescription(incidentDetails.getDescription());
        incident.setUpdatedAt(LocalDateTime.now());
        return incidentRepository.save(incident);
    }

    public void resolveIncident(Long id) {
        IncidentEntity incident = incidentRepository.findById(id).orElseThrow(() -> new RuntimeException("Incident not found"));
        incident.setStatus("RESOLVED");
        incident.setUpdatedAt(LocalDateTime.now());
        incidentRepository.save(incident);
    }
}
