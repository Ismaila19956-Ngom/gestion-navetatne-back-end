package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "instruction")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstructionEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ins_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ins_promoteur_id")
    private PromoteurEntity promoteur;

    @Column(name = "ins_intitule", columnDefinition = "TEXT")
    private String intitule;

    @Column(name = "ins_niveau_instruction")
    private String niveauInstruction;

    @Column(name = "ins_observations", columnDefinition = "TEXT")
    private String observations;

    @ManyToOne
    @JoinColumn(name = "ins_region_id")
    private CadreLogiqueEntity region;

    @Column(name = "ins_consultant")
    private String consultant;

    @Column(name = "ins_date_depot")
    private LocalDate dateDepot;
    
    @Column(name = "ins_date_visite")
    private LocalDate dateVisite;

    @Column(name = "ins_date_reponse")
    private LocalDate dateReponse;

    @Column(name = "ins_date_depot_tdr")
    private LocalDate dateDepotTDR;

    @Column(name = "ins_date_visite_tdr")
    private LocalDate dateVisiteTDR;

    @Column(name = "ins_date_validation_tdr")
    private LocalDate dateValidationTDR;

    @Column(name = "ins_date_depot_eie")
    private LocalDate dateDepotEIE;

    @Column(name = "ins_date_validation_rapport")
    private LocalDate dateValidationRapport;
    
    @Column(name = "ins_date_audience_publique")
    private LocalDate dateAudiencePublique;

    @Column(name = "ins_date_depot_rapport_final")
    private LocalDate dateDepotRapportFinal;

    @Column(name = "ins_date_delivrance_attestation")
    private LocalDate dateDelivranceAttestation;

    @Column(name = "ins_date_delivrance_arrete")
    private LocalDate dateDelivranceArrete;
}