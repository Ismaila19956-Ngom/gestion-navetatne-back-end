package com.webgram.dgpsn.models.responses.ptba;

import lombok.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PtbaResponseDTO {
    private Long projetId;
    private String projetCode;
    private String projetName;
    private Integer annee;

    // Liste des activités groupées
    private List<PtbaActivityDTO> activities;

    // Liste dynamique de tous les bailleurs (structures) utilisés
    private Set<String> bailleurs;

    // Totaux par objectif
    private Map<String, Double> totauxParObjectif;

    // Totaux par action
    private Map<String, Double> totauxParAction;

    // Total général
    private Double totalGeneral;

    // Totaux par source de financement (Map dynamique)
    private Map<String, Double> totauxSourcesFinancement;

    // Métadonnées
    private String structure;
    private String responsable;
}
