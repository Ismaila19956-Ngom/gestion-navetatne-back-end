package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "budget_activity")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class BudgetActivityEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bud_id")
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "bud_year")
    private Integer year;

    @Column(name = "bud_amount")
    private Double amount;

    @Column(name = "mil_comment" , columnDefinition = "TEXT")
    private String comment;

//    @Column(name = "bud_allocation")
//    @Temporal(TemporalType.DATE)
//    private Date allocation;
//    @Column(name = "bud_amount")
//    private Double amount;
//    @Column(name = "mil_comment" , columnDefinition = "TEXT")
//    private String comment;
    @ManyToOne
    @JoinColumn(name = "budget_linked_typeBudget")
    private LabelEntity typeBudget;
    @ManyToOne
    @JoinColumn(name = "budget_linked_projet")
    private ManagementUnitEntity projet;
}
