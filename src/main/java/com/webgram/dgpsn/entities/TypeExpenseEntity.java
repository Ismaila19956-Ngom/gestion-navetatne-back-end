package com.webgram.dgpsn.entities;

// L'entite TYPE_EXPENSE represente maintenant categorie depenses

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "type_expense")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeExpenseEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exp_id")
    private Long id;

    @Column(name = "exp_code", unique=true)
    private String code;

    @Column(name = "exp_libelle")
    private String libelle;

    @ManyToOne()
    @JoinColumn(name = "ind_linked_categorieExpense")
    private LabelEntity categorieDepense;


}
