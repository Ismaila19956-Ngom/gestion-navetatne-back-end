package sn.naavetane.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import sn.naavetane.backend.entities.audits.Auditable;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "matchs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchEntity extends Auditable<Long> implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "equipe1", nullable = false)
    private String equipe1;

    @Column(name = "equipe2", nullable = false)
    private String equipe2;

    @Column(name = "heure", nullable = false)
    private String heure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "journee_id", nullable = false)
    private JourneeEntity journee;
}
