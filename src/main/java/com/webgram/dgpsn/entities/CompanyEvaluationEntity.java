package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "CompanyEvaluation")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CompanyEvaluationEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comp_eval_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "star_id", nullable = false)
    private StartUpEntity startup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eval_id", nullable = false)
    private EvaluationEntity evaluation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id", nullable = false)
    private LabelEntity category;

    @Column(name = "comp_eval_montant", nullable = false)
    private Double montant;

    @Column(name = "comp_eval_pourcentage_avancement", nullable = false)
    private Integer pourcentageAvancement;
}