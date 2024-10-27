package status.now.backend.statusNowBackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import status.now.backend.statusNowBackend.Entity.IncidentEntity;
import status.now.backend.statusNowBackend.Entity.MaintenanceSchedule;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceMetrics {

    private Long serviceId;
    private String serviceName;
    private int activeIncidentsCount;
    private int scheduledMaintenanceCount;
    private double uptimePercentage;
    private List<IncidentEntity> incidents;  // List of incidents in the specified time frame
    private List<MaintenanceSchedule> maintenanceSchedules;  // List of maintenance schedules in the specified time frame

}
