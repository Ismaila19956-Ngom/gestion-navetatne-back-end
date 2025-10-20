package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.StructureProjectType;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "partner_projet")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class StructureProjectEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "par_id")
    private Long id;
    @Column(name = "par_montant")
    private Double montant;
    @Column(name = "par_start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "par_end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "par_linked_structure_project_type")
    @Enumerated(EnumType.STRING)
    private StructureProjectType structureProjectType;
    @ManyToOne
    @JoinColumn(name = "partner_linked_projet")
    private ManagementUnitEntity projet;
    @ManyToOne
    @JoinColumn(name = "partner_linked_structure")
    private StructureEntity structure;
}
