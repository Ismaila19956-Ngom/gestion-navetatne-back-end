package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.enums.Sexe;
import com.webgram.dgpsn.entities.enums.StatutDeclaration;

import jakarta.persistence.*;
import java.time.LocalDate;

@Table(name = "declaration")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeclarationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;

    private LocalDate dateReception;

    private String prenom;
    private String nom;
    private String age;
    private String telephone;

    private String localisation;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String operateur;

    @Enumerated(EnumType.STRING)
    private StatutDeclaration statut;

    @Enumerated(EnumType.STRING)
    private Sexe sexe;
}
