package com.webgram.dgpsn.entities;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;

@Table(name = "rapport")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RapportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    @Column(columnDefinition = "TEXT")
    private String contenu;

    private Boolean satisfait;

    @ManyToOne
    @JoinColumn(name = "declaration_id")
    private DeclarationEntity declaration;
}
