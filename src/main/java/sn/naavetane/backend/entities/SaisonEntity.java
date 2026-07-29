package sn.naavetane.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import sn.naavetane.backend.entities.audits.Auditable;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "saisons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaisonEntity extends Auditable<Long> implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "libelle", unique = true, nullable = false)
    private String libelle;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = false;
}
