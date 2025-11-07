package com.webgram.dgpsn.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CongeDashboardDTO {
    private CongeDashboardSummaryDTO summary;
    private List<TypeCongeCountDTO> typeCongeRepartition;
    private List<StatutCountDTO> statutRepartition;
    private List<MoisCountsDTO> evolutionMensuelle;
    private List<DureeRangeDTO> dureeRepartition;
    private List<TopAgentDTO> topAgents;
    private Map<String, Double> tauxApprobationParType;
}