package com.webgram.dgpsn.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceExterieurKpiResponse {
    private Long totalFormations;
    private Long totalMissions;
    private Long totalAteliers;
    private Double totalBudget;
    private Long totalParticipants;
}
