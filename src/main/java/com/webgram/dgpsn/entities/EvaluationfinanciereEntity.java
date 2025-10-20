package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;

import java.util.Date;

@Entity
@Table(name = "evaluationfinanciere")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EvaluationfinanciereEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "chiffreaffaire")
    private Double chiffreaffaire;

    @Column(name = "datedebut")
    @Temporal(TemporalType.DATE)
    private Date datedebut;

    @Column(name = "datefin")
    @Temporal(TemporalType.DATE)
    private Date datefin;

    @Column(name = "resultatnetreel")
    private Double resultatnetreel;

    @Column(name = "resultatnetprevu")
    private Double resultatnetprevu;

    @Column(name = "margebruteexpreel")
    private Double margebruteexpreel;

    @Column(name = "margebrutedexpprevue")
    private Double margebrutedexpprevue;

    @Column(name = "capacitdautofinancereel")
    private Double capacitdautofinancereel;

    @Column(name = "cafprevue")
    private Double cafprevue;

    @Column(name = "fondsderoulementreel")
    private Double fondsderoulementreel;

    @Column(name = "frngprevue")
    private Double frngprevue;

    @Column(name = "ratiolgreel")
    private Double ratiolgreel;

    @Column(name = "gearingreel")
    private Double gearingreel;

    @Column(name = "gearingprevue")
    private Double gearingprevue;

    @Column(name = "roerel")
    private Double roerel;

    @Column(name = "roeprvue")
    private Double roeprvue;

    @Column(name = "cashflowreel")
    private Double cashflowreel;

    @Column(name = "cashflowprevu")
    private Double cashflowprevu;

    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "notation")
    private Integer notation;

    @ManyToOne
    @JoinColumn(name = "linked_entreprise")
    private EntrepriseEntity entreprise;

}