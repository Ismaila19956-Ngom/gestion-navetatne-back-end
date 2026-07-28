package sn.naavetane.backend.entities.audits;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import sn.naavetane.backend.entities.audits.Auditable;

import java.util.UUID;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditEntity extends Auditable<String> {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "acteur", nullable = false)
    private String acteur;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "ressource")
    private String ressource;

    @Column(name = "details", columnDefinition = "TEXT")
    private String details;

    @Column(name = "adresse_ip")
    private String adresseIp;
}
