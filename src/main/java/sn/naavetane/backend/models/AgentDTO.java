package sn.naavetane.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import sn.naavetane.backend.entities.enums.Sexe;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import sn.naavetane.backend.entities.LabelEntity;
import sn.naavetane.backend.entities.enums.SituationMatrimoniale;


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
public class AgentDTO implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;

    private Date dateNaissance;
    private Sexe sexe;
    private SituationMatrimoniale situationMatrimoniale;
    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String matricule;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String telephone;
    private String photoProfil;
    private Date dateCreation;




    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LabelEntity fonction;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private StructureDTO structure;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private DirectionDTO direction;

    private String src;
    private Long fonctionId;
    private Long structureId;
    private Long directionId;
}
