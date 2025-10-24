package com.webgram.dgpsn.models;

import com.webgram.dgpsn.entities.enums.CourrierType;
import com.webgram.dgpsn.entities.enums.NatureCourrier;
import com.webgram.dgpsn.entities.enums.UrgenceCourrier;
import com.webgram.dgpsn.entities.enums.StatutCourrier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourrierDTO {
    private Long id;
    private String reference;
    private String correspondant;
    private String objet;
    private String description;
    private CourrierType type;        // ARRIVER vs DEPART
    private NatureCourrier nature;    // ACADEMIQUE vs ADMINISTRATIF
    private UrgenceCourrier urgence;
    private StatutCourrier statut;
    private LocalDateTime dateCourrier;
    private LocalDateTime dateReception;
    private LocalDateTime dateEnvoi;
    private LocalDateTime dateTraitement;
    private String numeroEnregistrement;
    private String numeroSuivi;
    private String modeEnvoi;
    private String instructions;
    private String notes;
    private String priorite;
}