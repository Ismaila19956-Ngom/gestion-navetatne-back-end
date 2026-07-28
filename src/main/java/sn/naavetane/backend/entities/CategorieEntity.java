package sn.naavetane.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import sn.naavetane.backend.entities.audits.Auditable;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "categories_billet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategorieEntity extends Auditable<Long> implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prix", nullable = false)
    private Double prix;

    @Column(name = "places_total", nullable = true)
    private Integer placesTotal;

    @Column(name = "places_restantes", nullable = false)
    private Integer placesRestantes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "journee_id", nullable = true)
    private JourneeEntity journee;
}
