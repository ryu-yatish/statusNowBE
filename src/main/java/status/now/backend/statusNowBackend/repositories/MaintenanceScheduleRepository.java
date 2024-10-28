package status.now.backend.statusNowBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.now.backend.statusNowBackend.Entity.IncidentEntity;
import status.now.backend.statusNowBackend.Entity.MaintenanceSchedule;
import status.now.backend.statusNowBackend.Entity.ServicesEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MaintenanceScheduleRepository extends JpaRepository<MaintenanceSchedule, Long> {
    int countByService(ServicesEntity service);
    List<MaintenanceSchedule> findByServiceId(Long serviceId);

    List<MaintenanceSchedule> findByServiceAndScheduledTimeBetween(ServicesEntity service, LocalDateTime startTime, LocalDateTime endTime);
}