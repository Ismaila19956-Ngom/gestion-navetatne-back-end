package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "FundingSource")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class FundingSourceEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "act_id")
    private Long id;

    @Column(name = "src_montant")
    private Double montant;

    @Column(name = "src_comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "management_linked_source_financement")
    private ManagementUnitEntity managementUnit;
    @ManyToOne
    @JoinColumn(name = "budget_linked_source_financement")
    private BudgetEntity budget;

    @ManyToOne
    @JoinColumn(name = "structure_linked_source_financement")
    private StructureEntity structure;
}

