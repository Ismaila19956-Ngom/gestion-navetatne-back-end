package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.webgram.dgpsn.entities.CaracteristiqueRecrutementEntity;
import com.webgram.dgpsn.entities.LabelEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
//exclure les propriétés ayant des valeurs nulles / vides ou par défaut.
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaracteristiqueExigeDTO implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;

    private Long caracteristiqueId; // L'ID de la CaracteristiqueRecrutementEntity
    private String caracteristiqueLibelle; // Optionnel : pour l'affichage
    private boolean exige;

}
