package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "gouvernance_conformites")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GouvernanceConformiteEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gouv_conf_id")
    private Long id;

    @Column(name = "gouv_conf_code")
    private String code;

    @Column(name = "gouv_conf_libelle")
    private String libelle;

    @Column(name = "gouv_conf_startDate")
    private Date startDate;

    @Column(name = "gouv_conf_endDate")
    private Date endDate;

    @Column(name = "gouv_conf_source")
    private String source;

    @Column(name = "gouv_conf_etat")
    private String etat;

    @Column(name = "gouv_conf_comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "gouv_conf_resume", columnDefinition = "TEXT")
    private String resume;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "gouv_conformite_phase",
            joinColumns = @JoinColumn(name="gouv_conformite_id"),
            inverseJoinColumns = @JoinColumn(name="phase_id"))
    private Set<LabelEntity> phases = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "gouv_conformite_linked_label_categorie")
    private LabelEntity categorie;

    @ManyToOne
    @JoinColumn(name = "gouv_conformite_linked_label_typeReference")
    private LabelEntity typeReference;

    @ManyToOne
    @JoinColumn(name = "gouv_conformite_linked_projet")
    private ManagementUnitEntity projet;



}
