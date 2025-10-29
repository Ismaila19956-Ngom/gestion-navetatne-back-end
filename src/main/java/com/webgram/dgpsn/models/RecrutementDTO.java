package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.webgram.dgpsn.annotations.JournalAttribute;
import com.webgram.dgpsn.entities.enums.SouceBudget;
import com.webgram.dgpsn.entities.enums.TypeContrat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
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
public class RecrutementDTO implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JournalAttribute
    private Long id;
    @JournalAttribute
    private String libelle;
    @JournalAttribute
    private TypeContrat typeContrat;

    private Date dateRecrutement;
    // ✅ Liste des caractéristiques
    private List<CaracteristiqueExigeDTO> caracteristiques;

}
