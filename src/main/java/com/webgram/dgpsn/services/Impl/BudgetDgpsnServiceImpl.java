package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.BudgetDgpsnMapper;
import com.webgram.dgpsn.models.BudgetDgpsnDTO;
import com.webgram.dgpsn.repositories.BudgetDgpsnRepository;
import com.webgram.dgpsn.services.BudgetDgpsnService;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BudgetDgpsnServiceImpl implements BudgetDgpsnService {
    private final BudgetDgpsnRepository budgetDgpsnRepository;
    private final BudgetDgpsnMapper budgetDgpsnMapper;

    @Override
    @Journal(actionType = ActionType.ADD_DATES_IMPORTANTE)
    public BudgetDgpsnDTO create(BudgetDgpsnDTO budgetDgpsnDTO) {
        var savedBudget = budgetDgpsnRepository.save(budgetDgpsnMapper.asEntity(budgetDgpsnDTO));
        log.info("BudgetDgpsn successfully added {}", savedBudget);
        return budgetDgpsnMapper.asDto(savedBudget);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_DATES_IMPORTANTE)
    public BudgetDgpsnDTO update(BudgetDgpsnDTO budgetDgpsnDTO) {
        try {
            if (budgetDgpsnRepository.existsById(budgetDgpsnDTO.getId())) {
                var budgetEntity = budgetDgpsnMapper.asEntity(budgetDgpsnDTO);
                var updatedBudget = budgetDgpsnMapper.asDto(budgetDgpsnRepository.save(budgetEntity));
                log.info("BudgetDgpsn successfully updated {}", updatedBudget.getId());
                return updatedBudget;
            } else {
                throw new ResourceNotFoundException("BudgetDgpsn", budgetDgpsnDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("BudgetDgpsn", budgetDgpsnDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public BudgetDgpsnDTO read(Long budgetDgpsnId) {
        var budget = budgetDgpsnRepository
                .findById(budgetDgpsnId)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetDgpsn", budgetDgpsnId));
        log.info("Reading BudgetDgpsn id {}", budget);
        return budgetDgpsnMapper.asDto(budget);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_DATES_IMPORTANTE)
    public void delete(Long budgetDgpsnId) {
        try {
            budgetDgpsnRepository.deleteById(budgetDgpsnId);
            log.info("The BudgetDgpsn id {} is deleted", budgetDgpsnId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("BudgetDgpsn", budgetDgpsnId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public Page<BudgetDgpsnDTO> readAll(
            Pageable pageable,
            String code,
            String libelle,
            Double montant,
            Integer annee
    ) {
        return budgetDgpsnRepository
                .readAllByFiltering(pageable, code, libelle, montant, annee)
                .map(budgetDgpsnMapper::asDto);
    }
}