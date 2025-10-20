package com.webgram.dgpsn.entities;

import lombok.*;
import jakarta.persistence.*;

@Table(name = "information_declaration")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InformationDeclarationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String referenceDec;

    private String traitementInformation;

    private String observation;

    private String compteRendu;

    private String evaluation;

    @ManyToOne
    @JoinColumn(name = "declaration_id")
    private DeclarationEntity declaration;
}