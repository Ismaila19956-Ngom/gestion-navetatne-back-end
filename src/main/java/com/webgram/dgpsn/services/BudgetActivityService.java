package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.BudgetActivityDTO;

import java.text.ParseException;

public interface BudgetActivityService {
    BudgetActivityDTO create(BudgetActivityDTO budgetActivityDTO);
    BudgetActivityDTO update(BudgetActivityDTO budgetActivityDTO);
    BudgetActivityDTO read(Long budgetActivityId);
    void delete(Long budgetActivityId);
    Page<BudgetActivityDTO> readAll(
            Pageable pageable,
            Long typeBudgetId,
            Integer year,
            Double amount,
            String sortBy,
            Boolean ascending,
            Long projetId
    ) throws ParseException;
//    void importMilestone(MultipartFile file, Long projectId);
//    void exportMilsstone(PrintWriter writer);
}
