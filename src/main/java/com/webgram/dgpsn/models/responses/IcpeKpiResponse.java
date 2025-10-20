package com.webgram.dgpsn.models.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IcpeKpiResponse {
    private long totalIcpe;
    private long inspectionsAnneeEnCours;
    private double tauxConformiteGlobal; // en pourcentage
    private long risquesEleves;
}