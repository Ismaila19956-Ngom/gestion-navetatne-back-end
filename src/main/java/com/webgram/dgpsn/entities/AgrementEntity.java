package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "agrement")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgrementEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "agr_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "agr_promoteur_id", nullable = false)
    private PromoteurEntity promoteur;

    @Column(name = "agr_objet")
    private String objet;

    @Column(name = "agr_niveau_instruction")
    private String niveauInstruction;

    @Column(name = "agr_observations", columnDefinition = "TEXT")
    private String observations;

    @Column(name = "agr_status", columnDefinition = "TEXT")
    private String status;

    @Column(name = "agr_date_depot")
    private LocalDate dateDepot;

    @Column(name = "agr_date_visite")
    private LocalDate dateVisite;

    @Column(name = "agr_date_delivrance_attestation")
    private LocalDate dateDelivranceAttestation;

    @Column(name = "agr_date_delivrance_arrete")
    private LocalDate dateDelivranceArrete;
}