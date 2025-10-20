package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.enums.Statut;
import com.webgram.dgpsn.entities.enums.TypeDemande;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private TypeDemande requestType;
    private String lastName;
    private String firstName;
    private String email;
    private String phone;
    private String organization;
    private String subject;
    private String message;
    private Statut statut;

//    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private DirectionEntity service;

    private Date dateCreation;

    private String serviceLibelle;

    private String filePath;
    private boolean consent;

    private Long serviceId;
}