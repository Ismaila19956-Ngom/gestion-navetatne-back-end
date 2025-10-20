package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.StatutType;
import com.webgram.dgpsn.entities.enums.TypePollution;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "pullution_manager")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class PollutiontManagerEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pollu_manager_id")
    private Long id;
    @Temporal(TemporalType.DATE)
    @Column(name = "pollu_manager_dateTransport")
    private Date dateTransport;
    @Column(name = "pollu_manager_dateMouvement")
    private Date dateMouvement;
    @Column(name = "pollu_manager_quantite")
    private Double quantite;
    @Column(name = "pollu_manager_autorisation")
    private String autorisation;
    @Enumerated(EnumType.STRING)
    @Column(name = "pollu_manager_TypePollution")
    private TypePollution typePollution;
    @Enumerated(EnumType.STRING)
    @Column(name = "pollu_rejet_type")
    private StatutType typeStatut;
    @Column(name = "pollu_manager_comment")
    private String comment;



    @ManyToOne()
    @JoinColumn(name = "pollu_manager_plastique")
    private LabelEntity plastique;

    @ManyToOne()
    @JoinColumn(name = "pollu_manager_produit")
    private LabelEntity produit;

    @ManyToOne()
    @JoinColumn(name = "pollu_manager_dechet")
    private LabelEntity dechet;

    @ManyToOne()
    @JoinColumn(name = "pollu_manager_origine")
    private LabelEntity origine;

    @ManyToOne()
    @JoinColumn(name = "pollu_manager_destination")
    private LabelEntity destination;

    @ManyToOne()
    @JoinColumn(name = "pollu_manager_methodeElimination")
    private LabelEntity methodeElimination;

    @ManyToOne()
    @JoinColumn(name = "pollu_manager_siteElimination")
    private LabelEntity siteElimination;



}
