package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.annotations.JournalAttribute;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JournalAttribute
    private Long id;

    @NotNull
    @JournalAttribute
    @Schema(description = "Code unique de la station")
    private String code;

    @NotNull
    @JournalAttribute
    @Schema(description = "Nom de la station")
    private String name;

    @Schema(description = "Type de la station (ex. urbaine, industrielle)", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LabelDTO type;

    @NotNull
    @JournalAttribute
    @Schema(description = "ID du type de station")
    private Long typeId;

    @Schema(description = "Région géographique", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CadreLogiqueDTO region;

    @NotNull
    @JournalAttribute
    @Schema(description = "ID de la région")
    private Long regionId;

    @Schema(description = "Département géographique", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CadreLogiqueDTO departement;

    @NotNull
    @JournalAttribute
    @Schema(description = "ID du département")
    private Long departementId;

    @JournalAttribute
    @Schema(description = "Latitude de la station")
    private String latitude;

    @JournalAttribute
    @Schema(description = "Longitude de la station")
    private String longitude;
}