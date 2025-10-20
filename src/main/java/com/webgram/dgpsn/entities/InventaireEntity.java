package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "Inventaire")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class InventaireEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "inventaire_id")
    private Long id;

    @Column(name = "inventaire_nom_periode", nullable = false)
    private String nomPeriode;

    @Column(name = "inventaire_date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "inventaire_date_fin", nullable = false)
    private LocalDate dateFin;

    @Column(name = "inventaire_etat", nullable = false)
    private String etat;

    @Column(name = "inventaire_commentaires")
    private String commentaires;
}