package status.now.backend.statusNowBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import status.now.backend.statusNowBackend.Entity.IncidentEntity;
import status.now.backend.statusNowBackend.service.IncidentManagementService;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    @Autowired
    private IncidentManagementService incidentManagementService;

    @GetMapping
    public ResponseEntity<List<IncidentEntity>> getAllIncidents() {
        List<IncidentEntity> incidents = incidentManagementService.getAllIncidents();
        return new ResponseEntity<>(incidents, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<IncidentEntity> createIncident(@RequestBody IncidentEntity incident) {
        IncidentEntity createdIncident = incidentManagementService.createIncident(incident);
        return new ResponseEntity<>(createdIncident, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncidentEntity> updateIncident(@PathVariable Long id, @RequestBody IncidentEntity incidentDetails) {
        try {
            IncidentEntity updatedIncident = incidentManagementService.updateIncident(id, incidentDetails);
            return new ResponseEntity<>(updatedIncident, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/resolve")
    public ResponseEntity<Void> resolveIncident(@PathVariable Long id) {
        try {
            incidentManagementService.resolveIncident(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}