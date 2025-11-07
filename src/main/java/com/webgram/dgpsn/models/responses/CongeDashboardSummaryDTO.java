package com.webgram.dgpsn.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CongeDashboardSummaryDTO {
    private Long totalDemandes;
    private Long congesApprouves;
    private Long congesEnCours;
    private Long congesRefuses;
}