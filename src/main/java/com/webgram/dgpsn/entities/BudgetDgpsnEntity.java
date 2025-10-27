package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "BudgetDgpsn")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class BudgetDgpsnEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -1234567890123456789L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "budget_dgpsn_id")
    private Long id;

    @Column(name = "budget_dgpsn_code")
    private String code;

    @Column(name = "budget_dgpsn_libelle")
    private String libelle;

    @Column(name = "budget_dgpsn_montant")
    private Double montant;

    @Column(name = "budget_dgpsn_annee")
    private Integer annee;

}