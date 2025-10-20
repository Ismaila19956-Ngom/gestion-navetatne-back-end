package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "passation_plan")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PassationPlanEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pass_id")
    private Long id;

    @Column(name = "libelle", unique=false)
    private String libelle;

    @Column(name = "reference", unique=false)
    private String reference;

    @Column(name = "yearPublication")
    @Temporal(TemporalType.DATE)
    private Date yearPublication;

    @ManyToOne
    @JoinColumn(name = "passationPlan_linked_structure")
    private StructureEntity structure;

    @ManyToOne
    @JoinColumn(name = "passationPlan_linked_projet")
    private ManagementUnitEntity managementUnit;

    @Column(name = "pass_description", columnDefinition = "TEXT")
    private String description;






}
