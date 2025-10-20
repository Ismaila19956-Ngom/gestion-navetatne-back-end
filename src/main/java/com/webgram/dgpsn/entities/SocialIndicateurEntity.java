package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "social_indicateur")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialIndicateurEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "social_ind_id")
    private Long id;

    @Column(name = "social_ind_code")
    private String code;

    @Column(name = "social_ind_libelle")
    private String libelle;

    @Column(name = "social_ind_valeur")
    private String valeur;

    @Column(name = "social_ind_startDate")
    private Date startDate;

    @Column(name = "social_ind_endDate")
    private Date endDate;

    @Column(name = "social_ind_source")
    private String source;

    @Column(name = "social_ind_comment", columnDefinition = "TEXT")
    private String comment;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "social_indicateur_phase",
            joinColumns = @JoinColumn(name="social_indicateur_id"),
            inverseJoinColumns = @JoinColumn(name="phase_id"))
    private Set<LabelEntity> phases = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "social_indicateur_linked_indicateur")
    private IndicatorEntity indicateur;

    @ManyToOne
    @JoinColumn(name = "social_indicateur_linked_projet")
    private ManagementUnitEntity projet;


}
