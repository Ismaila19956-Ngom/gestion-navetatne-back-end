package com.webgram.dgpsn.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "caracteristique_exige")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CaracteristiqueExigeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "caracteristique_exige_id") // Corrected ID column name to match entity
    private Long id;
    @Column(name = "exige")
    private Boolean exige;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recrutement_id")
    private RecrutementEntity recrutement;

    @ManyToOne
    @JoinColumn(name = "caracteristique_recrutement_id")
    private CaracteristiqueRecrutementEntity caracteristique;

}
