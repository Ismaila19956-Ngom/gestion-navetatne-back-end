package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.webgram.dgpsn.entities.enums.CategorieAlerte;
import com.webgram.dgpsn.entities.enums.Priority;
import com.webgram.dgpsn.entities.enums.TypeAlerte;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class TemplateDto {
    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String libelle;

    @NotNull
    private TypeAlerte typeAlerte;

    @NotNull
    private CategorieAlerte categorieAlerte;

    @NotNull
    private Priority priority;

    private Long deadlines;

    private boolean sendMail;

    private boolean sendSms;

    @NotBlank
    private String message;

    private Set<ProfileDTO> profiles;

    @NotNull
    @NotEmpty
    private Set<Long> profileIds;
}
