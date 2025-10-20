package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.Sexe;


import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "Startup")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StartUpEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "star_id")
    private Long id;

    // Company Info fields
//    @ManyToOne
//    @JoinColumn(name = "star_nom_compagnie")
//    private LabelEntity nomCompagnie;
    @Column(name = "star_nom_compagnie")
    private String nomCompagnie;
    @Column(name = "star_region")
    private String region;

    @Column(name = "star_latitude")
    private Double latitude;

    @Column(name = "star_longitude")
    private Double longitude;

    @Column(name = "star_adresse")
    private String adresse;

    @Column(name = "star_annee_creation")
    @Temporal(TemporalType.DATE)
    private Date anneeCreation;

    @Column(name = "star_email")
    private String email;

    @Column(name = "star_site_web")
    private String siteWeb;

    @Column(name = "star_dirige_par_femme")
    private Boolean dirigeParFemme;

    @Column(name = "star_fondee_par_femme")
    private Boolean fondeeParFemme;

    @Column(name = "star_co_fondee_par_femme")
    private Boolean coFondeeParFemme;

    @Column(name = "star_nombre_employes")
    private Integer nombreEmployes;

    @Column(name = "star_statut_juridique")
    private String statutJuridique;

    @Column(name = "star_selection")
    private Boolean selection;

    // Submitter Tech Info fields
    @Column(name = "star_prenom")
    private String prenom;

    @Column(name = "star_nom")
    private String nom;
    @Enumerated(EnumType.STRING)
    @Column(name = "star_genre")
    private Sexe genre;

    @Column(name = "star_telephone")
    private String tel;

    @Column(name = "star_email_soumissionnaire")
    private String emailSoumissionnaire;

    @Column(name = "star_position")
    private String position;

    @Column(name = "star_categorie_technologique")
    private String categorieTechnologique;

    @Column(name = "star_stade_developpement")
    private String stadeDeveloppement;

    @Column(name = "star_protection_intellectuelle")
    private Boolean protectionIntellectuelle;

    @Column(name = "star_marche")
    private String marche;


}