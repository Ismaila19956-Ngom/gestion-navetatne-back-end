package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "Decaissement")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DecaissementEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "decaissement_id")
    private Long id;

    @Column(name = "decaissement_mois", nullable = false)
    private String mois;

    @Column(name = "decaissement_activite", nullable = false)
    private String activite;

    @Column(name = "decaissement_montant_prev", nullable = false)
    private Double montantPrev;

    @Column(name = "decaissement_montant_decaisse", nullable = false)
    private Double montantDecaisse;

    @Column(name = "decaissement_ecart")
    private Double ecart;

    @Column(name = "decaissement_observation")
    private String observation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordonnancement_id", nullable = false)
    private OrdonnancementEntity ordonnancement;
}