package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.BudgetEntrepriseDto;

import java.util.List;
import java.util.Map;

public interface BudgetEntrepriseService {

    BudgetEntrepriseDto create(BudgetEntrepriseDto budget);

    BudgetEntrepriseDto update(BudgetEntrepriseDto budget);

    BudgetEntrepriseDto read(Long id);

    void delete(Long id);

    Page<BudgetEntrepriseDto> readAll(Map<String, String> searchParams, Pageable pageable);

    
    List<BudgetEntrepriseDto> readByEntrepriseId(Long entrepriseId);

}
