package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.enums.Sexe;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
//exclure les propriétés ayant des valeurs nulles / vides ou par défaut.
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StartUpDTO implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;

    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    // Company Info fields
//    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private LabelDTO nomCompagnie;
//    private Long nomCompagnieId;
    private String nomCompagnie;
    private String region;
    private Double latitude;
    private Double longitude;
    private String adresse;
    private Date anneeCreation;
    private String email;
    private String siteWeb;
    private Boolean dirigeParFemme;
    private Boolean fondeeParFemme;
    private Boolean coFondeeParFemme;
    private Integer nombreEmployes;
    private String statutJuridique;
    private Boolean selection;
    private String prenom;
    private String nom;
    private Sexe genre;
    private String tel;
    private String emailSoumissionnaire;
    private String position;
    private String categorieTechnologique;
    private String stadeDeveloppement;
    private Boolean protectionIntellectuelle;
    private String marche;


}
