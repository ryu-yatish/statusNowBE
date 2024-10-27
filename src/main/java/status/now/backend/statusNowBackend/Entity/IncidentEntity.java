package status.now.backend.statusNowBackend.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "INCIDENTS")
public class IncidentEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String status; // e.g., "OPEN", "IN_PROGRESS", "RESOLVED"


    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServicesEntity service; // Associate incident with a specific service

}