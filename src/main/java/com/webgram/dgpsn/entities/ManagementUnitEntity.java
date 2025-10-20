package com.webgram.dgpsn.entities;

import jakarta.persistence.*;
import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.Formula;
import com.webgram.dgpsn.entities.enums.TypeProjet;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "management_unite")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ManagementUnitEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "prj_id")
    private Long id;

    @Column(name = "prj_code", unique=true)
    private String code;

    @Column(name = "prj_poids")
    private Long poids;

    @ManyToOne
    private AgentEntity responsible;

    @Column(name = "prj_name")
    private String name;

    @Column(name = "prj_path")
    private String path;

    @Column(name = "prj_type")
    @Enumerated(EnumType.STRING)
    private TypeProjet type;

    @Column(name = "prj_formula")
    @Enumerated(EnumType.STRING)
    private Formula formula;

    @Column(name = "prj_expected_start_date")
    @Temporal(TemporalType.DATE)
    private Date expectedStartDate;

    @Column(name = "prj_date_end_prevue")
    @Temporal(TemporalType.DATE)
    private Date expectedEndDate;

    @Column(name = "prj_actual_start_date")
    @Temporal(TemporalType.DATE)
    private Date actualStartDate;

    @Column(name = "prj_actual_end_date")
    @Temporal(TemporalType.DATE)
    private Date actualEndDate;

    @Column(name = "prj_budget")
    private Double budget;

    @Column(name = "prj_budgetInvest")
    private Double budgetInsvest;

    @Column(name = "prj_description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "prj_overall_objective", columnDefinition = "TEXT")
    private String overallObjective;

    @Column(name = "prj_publish")
    private boolean publish;

    @ManyToOne
    private LabelEntity axe;

    @Column(name = "prl_tag" , columnDefinition = "TEXT")
    private String tag;

    @Column(name = "prj_disorganized")
    private boolean disorganized = false;

    @Column(name = "prj_actif")
    private Boolean actif;

    @Column(name = "prj_anneeDebut")
    private Integer anneeDebut;

    @Column(name = "prj_anneeFin")
    private Integer anneeFin;

    @Column(name = "prj_nomenclature")
    private String nomenclature;

    @ManyToOne
    private ManagementUnitEntity parent;


    @ManyToMany
    @JoinTable(name = "management_unit_subsector",
            joinColumns = {@JoinColumn(name = "management_unit_id")},
            inverseJoinColumns = {@JoinColumn(name = "subsector_id")})
    private List<SubSectorEntity> subSectors;

    @ManyToMany
    @JoinTable(name = "management_unit_execution_zone",
            joinColumns = {@JoinColumn(name = "management_unit_id")},
            inverseJoinColumns = {@JoinColumn(name = "execution_zone_id")})
    private List<LabelEntity> executionZones;

    @ManyToMany
    @JoinTable(name = "management_unit_beneficiary",
            joinColumns = {@JoinColumn(name = "management_unit_id")},
            inverseJoinColumns = {@JoinColumn(name = "beneficiary_id")})
    private List<LabelEntity> beneficiaries;

    @ManyToOne
    private StructureEntity structure;



}
