package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.BudgetDgpsnDTO;

import java.util.Map;

public interface BudgetDgpsnService {
    BudgetDgpsnDTO create(BudgetDgpsnDTO budgetDgpsnDTO);
    BudgetDgpsnDTO update(BudgetDgpsnDTO budgetDgpsnDTO);
    BudgetDgpsnDTO read(Long budgetDgpsnId);
    void delete(Long budgetDgpsnId);
    Page<BudgetDgpsnDTO> readAll(
            Pageable pageable,
            String code,
            String libelle,
            Double montant,
            Integer annee
    );
    Map<String, Object> getSyntheseBudgetaire(Long budgetId, String periode, Integer trimestre, Integer mois);
}