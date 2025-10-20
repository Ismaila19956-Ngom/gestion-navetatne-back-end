package com.webgram.dgpsn.entities;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;

@Table(name = "suiv_declaration")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuivDeclarationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ref;

    private LocalDate date;

    private String plaignant;

    private String contactPlaignant;

    @Column(columnDefinition = "TEXT")
    private String evenement;

    private String localisation;

    private String miseEnCause;

    private String contactMiseEnCause;

    @Column(columnDefinition = "TEXT")
    private String constat;

    private LocalDate dateConstat;

    @Column(columnDefinition = "TEXT")
    private String mesuresPrises;

    @Column(columnDefinition = "TEXT")
    private String observations;

    private String pv;

    private String autresPj;

    @ManyToOne
    @JoinColumn(name = "declaration_id")
    private DeclarationEntity declaration;
}