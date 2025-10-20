package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.entities.enums.SouceBudget;
import com.webgram.dgpsn.models.BudgetDTO;

import java.text.ParseException;

public interface BudgetService {
    BudgetDTO create(BudgetDTO budgetDTO);
    BudgetDTO update(BudgetDTO budgetDTO);
    BudgetDTO read(Long fundingActivityId);
    void delete(Long budgetId);
    Page<BudgetDTO> readAll(
            Pageable pageable,
            String libelle,
            SouceBudget souceBudget,
            String estimatedAmount,
            String actualAmount,
            Long managementUnitId
    ) throws ParseException;

//    void importMilestone(MultipartFile file, Long projectId);
//    void exportMilsstone(PrintWriter writer);

    Long totalFunding();
}
