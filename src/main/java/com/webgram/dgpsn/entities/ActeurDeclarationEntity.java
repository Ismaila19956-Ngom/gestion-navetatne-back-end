package com.webgram.dgpsn.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "acteur_declaration")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActeurDeclarationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zoneCompetence;

    private String service;

    private String prenom;

    private String nom;

    private String telBureau;

    private String telPortable;

    private String email;

    @ManyToOne
    @JoinColumn(name = "declaration_id")
    private DeclarationEntity declaration;
}