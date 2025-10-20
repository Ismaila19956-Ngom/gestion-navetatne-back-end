package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "tdr")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TdrEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tdr_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tdr_instruction_id", nullable = false)
    private InstructionEntity instruction;

    @Column(name = "tdr_intitule", columnDefinition = "TEXT")
    private String intitule;

    @Column(name = "tdr_type_evaluation")
    private String typeEvaluation;

    @Column(name = "tdr_consultant")
    private String consultant;

    @Column(name = "tdr_date_depot")
    private LocalDate dateDepot;

    @Column(name = "tdr_date_validation_prevue")
    private LocalDate dateValidationPrevue;

    @Column(name = "tdr_budget_previsionnel")
    private Double budgetPrevisionnel;
    
    @Column(name = "tdr_objectifs", columnDefinition = "TEXT")
    private String objectifs;

    @Column(name = "tdr_portee_etude", columnDefinition = "TEXT")
    private String porteeEtude;
    
    @Column(name = "tdr_observations", columnDefinition = "TEXT")
    private String observations;
}