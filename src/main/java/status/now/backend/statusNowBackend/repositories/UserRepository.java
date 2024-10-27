package status.now.backend.statusNowBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import status.now.backend.statusNowBackend.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
