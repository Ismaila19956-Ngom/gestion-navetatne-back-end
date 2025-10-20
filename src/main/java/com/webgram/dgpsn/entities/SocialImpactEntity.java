package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "social_impact")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialImpactEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "social_imp_id")
    private Long id;

    @Column(name = "social_imp_code")
    private String code;

    @Column(name = "social_imp_libelle")
    private String libelle;

    @Column(name = "social_imp_nbPersAff")
    private Number nbPersonnesAffectees;

    @Column(name = "social_imp_nbMenaAff")
    private Number nbMenagesAffectees;

    @Column(name = "social_imp_startDate")
    private Date startDate;

    @Column(name = "social_imp_endDate")
    private Date endDate;

    @Column(name = "social_imp_source")
    private String source;

    @Column(name = "social_imp_natureImpact")
    private String natureImpact;

    @Column(name = "social_imp_importanceImpact")
    private String importanceImpact;

    @Column(name = "social_imp_comment", columnDefinition = "TEXT")
    private String comment;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "social_impact_phase",
            joinColumns = @JoinColumn(name="social_impact_id"),
            inverseJoinColumns = @JoinColumn(name="phase_id"))
    private Set<LabelEntity> phases = new HashSet<>();

    @ManyToOne(optional = true)
    @JoinColumn(name = "social_impact_linked_label_categorie")
    private LabelEntity categorie;

    @ManyToOne(optional = true)
    @JoinColumn(name = "social_impact_linked_label_typeImpact")
    private LabelEntity typeImpact;

    @ManyToOne
    @JoinColumn(name = "social_impact_linked_projet")
    private ManagementUnitEntity projet;


}
