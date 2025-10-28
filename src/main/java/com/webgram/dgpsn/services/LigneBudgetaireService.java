package com.webgram.dgpsn.services;

import com.webgram.dgpsn.entities.enums.TypeLigneBugetaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.LigneBudgetaireDTO;

import java.util.List;

public interface LigneBudgetaireService {
    LigneBudgetaireDTO create(LigneBudgetaireDTO ligneBudgetaireDTO);
    List<LigneBudgetaireDTO> createMultiple(List<LigneBudgetaireDTO> ligneBudgetaireDTOs);
    LigneBudgetaireDTO update(LigneBudgetaireDTO ligneBudgetaireDTO);
    LigneBudgetaireDTO read(Long ligneBudgetaireId);
    public List<LigneBudgetaireDTO> findByBudgetId(Long budgetId);
    void delete(Long ligneBudgetaireId);
    Page<LigneBudgetaireDTO> readAll(
            Pageable pageable,
            Long rubriqueId,
            Double montant,
            TypeLigneBugetaire typeLigneBugetaire,
            String commentaire,
            Long budgetId
    );
}