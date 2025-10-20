package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "BudgetPassation")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BudgetPassationEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "budget_passation_id")
    private Long id;

    @Column(name = "budget_passation_program", nullable = false)
    private String program;

    @Column(name = "budget_passation_libelle_section", nullable = false)
    private String libelleSection;

    @Column(name = "budget_passation_categorie", nullable = false)
    private String categorie;

    @Column(name = "budget_passation_chapitre", nullable = false)
    private String chapitre;

    @Column(name = "budget_passation_libelle_chapitre", nullable = false)
    private String libelleChapitre;

    @Column(name = "budget_passation_credits", nullable = false)
    private Double credits;

    @Column(name = "budget_passation_annees", nullable = false)
    private String anneeCredits;
}