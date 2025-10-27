package com.webgram.dgpsn.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.webgram.dgpsn.annotations.JournalAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.enums.TypePlanComptable;
import java.io.Serializable;

@Data @Builder @Accessors(chain = true) @NoArgsConstructor @AllArgsConstructor @ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanComptableElementDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String code;
    private String libelle;
    private String commentaire;
    private TypePlanComptable type;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private PlanComptableElementDTO parent;
    @JournalAttribute
    @NotNull
    private Long parentId;
}