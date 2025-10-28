package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.enums.TypeLigneBugetaire;
import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "LigneBudgetaire")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class LigneBudgetaireEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ligne_budgetaire_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ligne_budgetaire_rubrique")
    private PlanComptableElementEntity rubrique;

    @Column(name = "ligne_budgetaire_montant")
    private Double montant;
    @Column(name = "ligne_budgetaire_type")
    @Enumerated(EnumType.STRING)
    private TypeLigneBugetaire typeLigneBugetaire;

    @Column(name = "ligne_budgetaire_commentaire", columnDefinition = "TEXT")
    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "budget_dgpsn_id")
    private BudgetDgpsnEntity budget;
}