package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "expense_activity")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class ExpenseActivityEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exp_id")
    private Long id;

    @Column(name = "exp_label")
    private String label;

    @Column(name = "exp_date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "exp_Unit_amount")
    private String unitAmount;

    @Column(name = "exp_quantity")
    private String quantity;

    @Column(name = "exp_totalAmount")
    private Double totalAmount;

    @Column(name = "exp_comment" , columnDefinition = "TEXT")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "exp_linked_categorieDepense")
    private LabelEntity categorieDepense;

    @ManyToOne
    @JoinColumn(name = "exp_linked_typeDepense")
    private TypeExpenseEntity typeDepense;


    @ManyToOne
    @JoinColumn(name = "budget_linked_projet")
    private ManagementUnitEntity projet;

    @ManyToOne
    @JoinColumn(name = "exp_linked_budget")
    private BudgetActivityEntity budget;
}
