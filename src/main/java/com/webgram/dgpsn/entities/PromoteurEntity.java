package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "promoteur")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromoteurEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_entreprise", nullable = false)
    private String nomEntreprise;

    @Column(name = "personne_contact")
    private String personneContact;

    @Column(name = "fonction_contact")
    private String fonctionContact;

    @Column(name = "adresse_siege", columnDefinition = "TEXT")
    private String adresseSiege;

    @Column(name = "adresse_site", columnDefinition = "TEXT")
    private String adresseSite;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @Column(name = "bureau_etudes")
    private String bureauEtudes;
}