package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.StatutType;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "pullution_milieux")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class MilieuxPollutiontEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pollu_rejet_id")
    private Long id;
    @Temporal(TemporalType.DATE)
    @Column(name = "pollu_rejet_datePrelevement")
    private Date datePrelevement;
    @Column(name = "pollu_milieux_turbidite")
    private Integer turbidite;
    @Column(name = "pollu_milieux_oxygene")
    private Integer oxygene;
    @Column(name = "pollu_milieux_nitrate")
    private Integer nitrate;

    @Column(name = "pollu_milieux_comment")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "pollu_milieux_type")
    private StatutType typeStatut;

    @ManyToOne()
    @JoinColumn(name = "pollu_milieux_pointPrelevement")
    private LabelEntity pointPrelevement;

    @ManyToOne()
    @JoinColumn(name = "pollu_milieux_conditionMeteo")
    private LabelEntity condMeteo;



}
