package status.now.backend.statusNowBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.now.backend.statusNowBackend.Entity.IncidentEntity;
import status.now.backend.statusNowBackend.Entity.ServicesEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<IncidentEntity, Long> {
    int countByServiceAndStatus(ServicesEntity service, String status);
    List<IncidentEntity> findByServiceId(Long serviceId);
    List<IncidentEntity> findByServiceAndCreatedAtBetween(ServicesEntity service, LocalDateTime startTime, LocalDateTime endTime);
}