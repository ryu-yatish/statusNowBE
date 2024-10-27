package status.now.backend.statusNowBackend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import status.now.backend.statusNowBackend.DTO.ServiceMetrics;
import status.now.backend.statusNowBackend.Entity.ServicesEntity;
import status.now.backend.statusNowBackend.service.ServiceManagementService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServicesController {

    @Autowired
    private ServiceManagementService serviceManagementService;

    @GetMapping
    public ResponseEntity<List<ServicesEntity>> getAllServices() {
        List<ServicesEntity> services = serviceManagementService.getAllServices();
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicesEntity> getServiceById(@PathVariable Long id) {
        return serviceManagementService.getServiceById(id)
                .map(service -> new ResponseEntity<>(service, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ServicesEntity> createService(@RequestBody ServicesEntity service) {
        ServicesEntity createdService = serviceManagementService.createService(service);
        return new ResponseEntity<>(createdService, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicesEntity> updateService(@PathVariable Long id, @RequestBody ServicesEntity serviceDetails) {
        try {
            ServicesEntity updatedService = serviceManagementService.updateService(id, serviceDetails);
            return new ResponseEntity<>(updatedService, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        try {
            serviceManagementService.deleteService(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{serviceId}/metrics")
    public ResponseEntity<ServiceMetrics> getServiceMetrics(@PathVariable Long serviceId,
                                                            @RequestParam String startTime,
                                                            @RequestParam String endTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime start = LocalDateTime.parse(startTime, formatter);
            LocalDateTime end = LocalDateTime.parse(endTime, formatter);

            ServiceMetrics metrics = serviceManagementService.getServiceMetrics(serviceId, start, end);
            return ResponseEntity.ok(metrics);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("public/{serviceId}/metrics")
    public ResponseEntity<ServiceMetrics> getServiceMetricsPublic(@PathVariable Long serviceId,
                                                            @RequestParam String startTime,
                                                            @RequestParam String endTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime start = LocalDateTime.parse(startTime, formatter);
            LocalDateTime end = LocalDateTime.parse(endTime, formatter);

            ServiceMetrics metrics = serviceManagementService.getServiceMetrics(serviceId, start, end);
            return ResponseEntity.ok(metrics);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}