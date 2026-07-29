package sn.naavetane.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import sn.naavetane.backend.entities.audits.Auditable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "journees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JourneeEntity extends Auditable<Long> implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "date_journee", nullable = false)
    private LocalDate date;

    @Column(name = "stade", nullable = false)
    private String stade;

    @Column(name = "statut")
    private String statut = "PROGRAMMEE";

    @Column(name = "saison")
    private String saison;

    @OneToMany(mappedBy = "journee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchEntity> matchs = new ArrayList<>();

    @OneToMany(mappedBy = "journee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategorieEntity> categories = new ArrayList<>();

    public void addMatch(MatchEntity match) {
        matchs.add(match);
        match.setJournee(this);
    }

    public void addCategorie(CategorieEntity categorie) {
        categories.add(categorie);
        categorie.setJournee(this);
    }
}
