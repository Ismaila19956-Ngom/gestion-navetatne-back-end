package com.webgram.dgpsn.models.responses;

import lombok.Builder;
import lombok.Data;
import com.webgram.dgpsn.models.CompletionRateDTO;
import com.webgram.dgpsn.models.ManagementUnitDTO;

import java.util.List;

@Data
@Builder
public class CompletionRateResponse {
    private ManagementUnitDTO managementUnit;
    private List<CompletionRateDTO> completionRates;
    private List<CompletionRateResponse> children;
}
