package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.enums.StatutType;
import com.webgram.dgpsn.entities.enums.TypeContrat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


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

    @ManyToOne
    @JoinColumn(name = "recru_type_contrat")
    private LabelEntity typeContrat;

    @Column(name = "recru_date")
    @Temporal(TemporalType.DATE)
    private Date dateRecrutement;

    @Column(name = "recru_type_statut")
    @Enumerated(EnumType.STRING)
    private StatutType statutType;

    @OneToMany(
            mappedBy = "recrutement",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<CaracteristiqueExigeEntity> caracteristiques = new HashSet<>(); // Initialisez toujours les collections !
}
