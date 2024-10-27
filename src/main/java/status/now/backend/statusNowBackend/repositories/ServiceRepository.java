package status.now.backend.statusNowBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.now.backend.statusNowBackend.Entity.ServicesEntity;

@Repository
public interface ServiceRepository extends JpaRepository<ServicesEntity, Long> {
}