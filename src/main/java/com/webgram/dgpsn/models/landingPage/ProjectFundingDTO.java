package com.webgram.dgpsn.models.landingPage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

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
public class ProjectFundingDTO implements Serializable {
    private Long id;
    private String libelle;
    private String description;
    private Date dateDebut;
    private Date dateFin;
    private Double budget;
    private String secteur;
    private String sousSecteur;
    private String ministere;
    private Double average;
}