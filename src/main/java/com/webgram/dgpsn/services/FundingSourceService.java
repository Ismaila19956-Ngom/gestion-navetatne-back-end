package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.FundingSourceDTO;

import java.text.ParseException;

public interface FundingSourceService {
    FundingSourceDTO create(FundingSourceDTO fundingSourceDTO);
    FundingSourceDTO update(FundingSourceDTO fundingSourceDTO);
    FundingSourceDTO read(Long fundingSourceId);
    void delete(Long fundingSourceId);
    Page<FundingSourceDTO> readAll(
            Pageable pageable,
            String montant,
            Long managementUnitId,
            Long structureId,
            Long budgetId,
            Long tacheId,
            Long valueIndicatorId,
            Long budgetGlobalId
    ) throws ParseException;

//    void importMilestone(MultipartFile file, Long projectId);
//    void exportMilsstone(PrintWriter writer);
}
