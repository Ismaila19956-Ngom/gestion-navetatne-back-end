package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.webgram.dgpsn.entities.enums.CategorieAlerte;
import com.webgram.dgpsn.entities.enums.Priority;
import com.webgram.dgpsn.entities.enums.TypeAlerte;

import jakarta.validation.constraints.NotNull;
import java.util.Set;

@Data
public class TemplateDto {
    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String libelle;

    private TypeAlerte typeAlerte;
    @NotNull
    private CategorieAlerte categorieAlerte;

    private Priority priority;

    private Long deadlines;

    private boolean sendMail;

    private boolean sendSms;

    private String message;

    private Set<ProfileDTO> profiles;

    private Set<Long> profileIds;
}
