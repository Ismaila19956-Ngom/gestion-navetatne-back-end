package com.webgram.dgpsn.entities;

import jakarta.persistence.*;
import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "budget_entreprise")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BudgetEntrepriseEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "anneefiscale")
    private String anneefiscale;

    @Column(name = "montantalloue")
    private Double montantalloue;

    @Column(name = "projetassocisier")
    private String projetassocisier;

    @Column(name = "datelimitedutilisation")
    @Temporal(TemporalType.DATE)
    private Date datelimitedutilisation;

    @Column(name = "statutdubudget")
    private String statutdubudget;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "categoriebudgetaire")
    private CategoriebudgetaireEntity categoriebudgetaire;

    @ManyToOne
    @JoinColumn(name = "periode")
    private PeriodeEntity periode;

    @ManyToOne
    @JoinColumn(name = "periodicite")
    private PeriodiciteEntity periodicite;

    @ManyToOne
    @JoinColumn(name = "linked_entreprise")
    private EntrepriseEntity entreprise;

}
