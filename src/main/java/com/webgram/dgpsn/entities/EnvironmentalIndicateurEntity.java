package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "environmental_indicateur")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnvironmentalIndicateurEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "env_ind_id")
    private Long id;

    @Column(name = "env_ind_code")
    private String code;

    @Column(name = "env_ind_libelle")
    private String libelle;

    @Column(name = "env_ind_valeur")
    private String valeur;

    @Column(name = "env_ind_startDate")
    private Date startDate;

    @Column(name = "env_ind_endDate")
    private Date endDate;

    @Column(name = "env_ind_source")
    private String source;

    @Column(name = "env_ind_comment", columnDefinition = "TEXT")
    private String comment;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "envir_indicateur_phase",
            joinColumns = @JoinColumn(name="envir_indicateur_id"),
            inverseJoinColumns = @JoinColumn(name="phase_id"))
    private Set<LabelEntity> phases = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "environ_indicateur_linked_indicateur")
    private IndicatorEntity indicateur;

    @ManyToOne
    @JoinColumn(name = "environ_indicateur_linked_projet")
    private ManagementUnitEntity projet;


}
