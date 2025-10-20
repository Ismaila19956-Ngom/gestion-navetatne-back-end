package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "environmental_conformites")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnvironmentalConformiteEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "env_conf_id")
    private Long id;

    @Column(name = "env_conf_code")
    private String code;

    @Column(name = "env_conf_libelle")
    private String libelle;

    @Column(name = "env_conf_startDate")
    private Date startDate;

    @Column(name = "env_conf_endDate")
    private Date endDate;

    @Column(name = "env_conf_source")
    private String source;

    @Column(name = "env_conf_etat")
    private String etat;

    @Column(name = "env_conf_comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "env_conf_resume", columnDefinition = "TEXT")
    private String resume;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "envir_conformite_phase",
            joinColumns = @JoinColumn(name="envir_conformite_id"),
            inverseJoinColumns = @JoinColumn(name="phase_id"))
    private Set<LabelEntity> phases = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "environ_conformite_linked_label_categorie")
    private LabelEntity categorie;

    @ManyToOne
    @JoinColumn(name = "environ_conformite_linked_label_typeReference")
    private LabelEntity typeReference;

    @ManyToOne
    @JoinColumn(name = "environ_conformite_linked_projet")
    private ManagementUnitEntity projet;



}
