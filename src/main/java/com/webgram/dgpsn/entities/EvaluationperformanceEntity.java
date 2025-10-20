package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;

import java.util.Date;

@Entity
@Table(name = "evaluationperformance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EvaluationperformanceEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "datededbut")
    @Temporal(TemporalType.DATE)
    private Date datededbut;

    @Column(name = "datedefin")
    @Temporal(TemporalType.DATE)
    private Date datedefin;

    @Column(name = "pointsforts")
    private String pointsforts;

    @Column(name = "pointsfaibles")
    private String pointsfaibles;

    @Column(name = "apprciationgouvernance")
    private String apprciationgouvernance;

    @Column(name = "respectprocdures")
    private String respectprocdures;

    @Column(name = "statut")
    private String statut;

    @Column(name = "notation")
    private Integer notation;

    @Column(name = "recommandations")
    private String recommandations;

    @Column(name = "plandaction")
    private String plandaction;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "typedvaluation")
    private TypedvaluationEntity typedvaluation;

    @ManyToOne
    @JoinColumn(name = "risque")
    private RisqueEntity risque;

    @ManyToOne
    @JoinColumn(name = "linked_entreprise")
    private EntrepriseEntity entreprise;

}