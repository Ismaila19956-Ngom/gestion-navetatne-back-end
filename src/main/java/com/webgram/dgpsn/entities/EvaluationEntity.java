package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "Evaluation")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EvaluationEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -1234567890123456789L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "eval_id")
    private Long id;

    @Column(name = "eval_libelle", nullable = false)
    private String libelle;

    @Column(name = "eval_annee", nullable = false)
    private Integer annee;

    @Column(name = "eval_date_debut", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "eval_date_fin", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateFin;
}