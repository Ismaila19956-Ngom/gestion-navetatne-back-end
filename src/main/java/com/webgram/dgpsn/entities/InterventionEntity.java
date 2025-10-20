package com.webgram.dgpsn.entities;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "intervention")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterventionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;

    private LocalDate date;

    private LocalTime heure;

    private String contactDeclarant;

    private String typeEvenement;

    private String localisation;

    @Column(columnDefinition = "TEXT")
    private String etatLieux;

    private String niveauIntervention;

    @Column(columnDefinition = "TEXT")
    private String actionsGestionLocale;

    private String declenchementPlanUrgence;

    @ManyToOne
    @JoinColumn(name = "declaration_id")
    private DeclarationEntity declaration;
}