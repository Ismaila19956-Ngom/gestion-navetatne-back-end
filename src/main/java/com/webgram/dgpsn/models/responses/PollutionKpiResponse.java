package com.webgram.dgpsn.models.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PollutionKpiResponse {
    private double quantiteTotaleDechetsAnnee;
    private long alertesPollutionEau30j;
    private long nonConformitesRejets30j;
}