package status.now.backend.statusNowBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import status.now.backend.statusNowBackend.Entity.MaintenanceSchedule;

import status.now.backend.statusNowBackend.service.MaintenanceScheduleManagementService;
import status.now.backend.statusNowBackend.service.ServiceManagementService;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceScheduleController {

    @Autowired
    private MaintenanceScheduleManagementService maintenanceScheduleManagementService;

    @Autowired
    private ServiceManagementService serviceManagementService;

    @GetMapping
    public ResponseEntity<List<MaintenanceSchedule>> getAllSchedules() {
        List<MaintenanceSchedule> schedules = maintenanceScheduleManagementService.getAllSchedules();
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MaintenanceSchedule> createSchedule(@RequestBody MaintenanceSchedule schedule) {
        if(schedule.getService()!=null && schedule.getService().getId()!=null){
            MaintenanceSchedule createdSchedule = maintenanceScheduleManagementService.createSchedule(schedule);
            return new ResponseEntity<>(createdSchedule, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // Add additional endpoints for update and delete as needed
}