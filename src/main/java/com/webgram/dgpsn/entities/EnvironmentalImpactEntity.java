package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "environmental_impact")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnvironmentalImpactEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "env_imp_id")
    private Long id;

    @Column(name = "env_imp_code")
    private String code;

    @Column(name = "env_imp_libelle")
    private String libelle;

    @Column(name = "env_imp_startDate")
    private Date startDate;

    @Column(name = "env_imp_endDate")
    private Date endDate;

    @Column(name = "env_imp_source")
    private String source;

    @Column(name = "env_imp_natureImpact")
    private String natureImpact;

    @Column(name = "env_imp_importanceImpact")
    private String importanceImpact;

    @Column(name = "env_imp_comment", columnDefinition = "TEXT")
    private String comment;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "envir_impact_phase",
            joinColumns = @JoinColumn(name="envir_impact_id"),
            inverseJoinColumns = @JoinColumn(name="phase_id"))
    private Set<LabelEntity> phases = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "environ_impact_linked_label_categorie")
    private LabelEntity categorie;

    @ManyToOne
    @JoinColumn(name = "environ_impact_linked_projet")
    private ManagementUnitEntity projet;


}
