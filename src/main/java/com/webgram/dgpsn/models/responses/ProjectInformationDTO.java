package com.webgram.dgpsn.models.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
//exclure les propriétés ayant des valeurs nulles / vides ou par défaut.
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectInformationDTO {
    private Long id;
    private String nom;
    private String etat;
    private String statut;
    private Double taux;
    private Double cible;
    private List<ProblemeInformationDTO> problemeInformationDTOList;
}
