package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "suivi_surveillance")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuiviSurveillanceEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ss_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ss_promoteur_id")
    private PromoteurEntity promoteur;

    @Column(name = "ss_intitule", columnDefinition = "TEXT")
    private String intitule;

    @Column(name = "ss_niveau_instruction")
    private String niveauInstruction;

    @Column(name = "ss_observations", columnDefinition = "TEXT")
    private String observations;

    @Column(name = "ss_consultant")
    private String consultant;

    @Column(name = "ss_date_suivi")
    private LocalDate dateSuivi;

    @Column(name = "ss_date_transmission_rapport")
    private LocalDate dateTransmissionRapport;
}