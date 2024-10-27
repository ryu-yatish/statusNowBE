package status.now.backend.statusNowBackend.Entity;


import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SERVICES")
public class ServicesEntity extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type; // e.g., "Website", "API", "Database"
    private String url; // Optional, for API and Website
    private String description; // Optional

    // Getters and Setters
    // Constructor(s)
}