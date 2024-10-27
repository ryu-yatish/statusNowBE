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
@Table(name = "MAINTENANCE_SCHEDULES")
public class MaintenanceSchedule extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private LocalDateTime scheduledTime;
    private LocalDateTime endTime;
    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServicesEntity service; // Associate maintenance schedule with a specific service

    // Getters and Setters
}