package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.enums.TypeContrat;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;


@Table(name = "recrutement")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecrutementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "recru_libelle")
    private String libelle;

    @Column(name = "recru_type_contrat")
    private TypeContrat typeContrat;

    @Column(name = "recru_date")
    @Temporal(TemporalType.DATE)
    private Date dateRecrutement;

    @OneToMany(
            mappedBy = "recrutement",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<CaracteristiqueExigeEntity> caracteristiques = new HashSet<>(); // Initialisez toujours les collections !

    @OneToMany(
            mappedBy = "recrutement",
            cascade = CascadeType.ALL
    )
    private List<CandidatEntity> candidats = new ArrayList<>();
}
