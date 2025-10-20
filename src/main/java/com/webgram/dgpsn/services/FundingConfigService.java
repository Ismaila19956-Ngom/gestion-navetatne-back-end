package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.entities.enums.FundingTypeConfig;
import com.webgram.dgpsn.models.FundingConfigDTO;

import java.text.ParseException;

public interface FundingConfigService {
    FundingConfigDTO create(FundingConfigDTO fundingConfigDTO);
    FundingConfigDTO update(FundingConfigDTO fundingConfigDTO);
    FundingConfigDTO read(Long fundingConfigId);
    void delete(Long fundingId);
    Page<FundingConfigDTO> readAll(
            Pageable pageable,
            String libelle,
            String annee,
            FundingTypeConfig fundingTypeConfig,
            String startingDate,
            String endingDate,
            String estimatedAmount,
            String actualAmount,
            Long managementUnitId
    ) throws ParseException;

//    void importMilestone(MultipartFile file, Long projectId);
//    void exportMilsstone(PrintWriter writer);
}
