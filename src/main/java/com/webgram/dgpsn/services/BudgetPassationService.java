package com.webgram.dgpsn.services;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.BudgetPassationDTO;

import java.util.Map;

public interface BudgetPassationService {
    BudgetPassationDTO create(BudgetPassationDTO budgetPassationDTO);

    BudgetPassationDTO update(BudgetPassationDTO budgetPassationDTO);

    BudgetPassationDTO read(Long budgetPassationId);

    void delete(Long budgetPassationId);

    Page<BudgetPassationDTO> readAllBudgetPassations(Map<String, String> searchParams, Pageable pageable);

    ByteArrayResource exportAllBudgetPassationsForYearWithEngagementsAndOrdonnancements(String year, EngagementService engagementService, OrdonnancementService ordonnancementService);


    }