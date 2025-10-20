package com.webgram.dgpsn.entities;

import lombok.*;
import jakarta.persistence.*;

@Table(name = "repertoire_declaration")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepertoireDeclarationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mission;

    private String structure;

    private String telephone;

    private String email;

    @ManyToOne
    @JoinColumn(name = "declaration_id")
    private DeclarationEntity declaration;
}