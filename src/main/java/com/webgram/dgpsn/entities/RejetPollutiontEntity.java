package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.StatutType;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "pullution_rejet")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class RejetPollutiontEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pollu_rejet_id")
    private Long id;
    @Temporal(TemporalType.DATE)
    @Column(name = "pollu_rejet_datePrelevement")
    private Date datePrelevement;
    @Column(name = "pollu_rejet_ph")
    private Integer ph;
    @Column(name = "pollu_rejet_dco")
    private Integer dco;
    @Column(name = "pollu_rejet_dbo")
    private Integer dbo;
    @Column(name = "pollu_rejet_volume")
    private Integer volume;
    @Enumerated(EnumType.STRING)
    @Column(name = "pollu_rejet_type")
    private StatutType typeStatut;
    @Column(name = "pollu_rejet_comment")
    private String comment;

    @ManyToOne()
    @JoinColumn(name = "pollu_rejet_Entreprise")
    private LabelEntity entreprise;



}
