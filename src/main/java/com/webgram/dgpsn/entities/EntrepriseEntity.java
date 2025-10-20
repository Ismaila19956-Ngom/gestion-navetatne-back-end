package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "entreprise")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EntrepriseEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "denomination")
    private String denomination;

    @Column(name = "capitalsocial")
    private String capitalsocial;

    @Column(name = "participationetat")
    private String participationetat;

    @Column(name = "droitvote")
    private String droitvote;

    @Column(name = "vnp")
    private String vnp;

    @Column(name = "vcp")
    private String vcp;

    @Column(name = "vmp")
    private String vmp;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "responsable")
    private String responsable;

    @Column(name = "telephoneresponsable")
    private String telephoneresponsable;

    @Column(name = "emailresponsable")
    private String emailresponsable;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "website")
    private String website;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "secteuractivite")
    private LabelEntity secteuractivite;

    @ManyToOne
    @JoinColumn(name = "formejuridique")
    private LabelEntity formejuridique;

    @ManyToOne
    @JoinColumn(name = "region")
    private CadreLogiqueEntity region;

    @ManyToOne
    @JoinColumn(name = "departement")
    private CadreLogiqueEntity departement;

}
