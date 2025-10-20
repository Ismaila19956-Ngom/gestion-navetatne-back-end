package com.webgram.dgpsn.models.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EvaluationKpiResponse {
    private long dossiersEnCours;
    private long agrementsActifs;
    private double delaiMoyenTraitement; // en jours
}