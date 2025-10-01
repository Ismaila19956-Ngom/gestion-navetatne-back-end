package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.webgram.dgpsn.entities.WorkflowStepEntity;
import com.webgram.dgpsn.entities.enums.WorkflowType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowValidationHistoriqueDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private Date date;
    private Boolean validation;
    private String commentaire;
    private WorkflowType workflowType;
    private Long entityId;
    @Schema(description = "Année associée à la validation")
    private Integer year;
    @Schema(description = "Mois associé à la validation")
    private Integer month;
    @Schema(description = "date de calcul associé à la validation")
    private LocalDate dateCalcul;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private WorkflowStepEntity etape;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private UserEntity user;
    private UserData user;

    private Long etapeId;

    @Data
    @Builder
    public static class UserData {
        Long id;
        String prenom;
        String nom;
        String profil;
    }
}