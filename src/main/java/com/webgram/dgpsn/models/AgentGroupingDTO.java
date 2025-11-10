package com.webgram.dgpsn.models;

import lombok.*;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentGroupingDTO {
    // nombre d’agents par tranche d’âge et par sexe
    private Map<String, Map<String, Long>> ageGroups;

    // total global par sexe
    private Long totalHommes;
    private Long totalFemmes;
}
