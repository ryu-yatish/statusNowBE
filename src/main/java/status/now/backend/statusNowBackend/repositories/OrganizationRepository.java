package status.now.backend.statusNowBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.now.backend.statusNowBackend.Entity.OrganizationEntity;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Long> {
    OrganizationEntity findByName(String name); // Add this method
}
