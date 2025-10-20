package com.webgram.dgpsn.services.Impl;

import com.khoutech.openexcel.services.WorkbookService;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.BudgetActivityMapper;
import com.webgram.dgpsn.models.BudgetActivityDTO;
import com.webgram.dgpsn.repositories.BudgetActivityRepository;
import com.webgram.dgpsn.repositories.ManagementUnitRepository;
import com.webgram.dgpsn.services.BudgetActivityService;

import java.text.ParseException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BudgetActivityServiceImpl implements BudgetActivityService {
    private final BudgetActivityRepository budgetActivityRepository;
    private final BudgetActivityMapper budgetActivityMapper;
    private final ManagementUnitRepository managementUnitRepository;
    private final WorkbookService workbookService;

    @Override
    @Journal(actionType = ActionType.ADD_DATES_IMPORTANTE)
    public BudgetActivityDTO create(BudgetActivityDTO budgetActivityDTO) {
         var savedBudget = budgetActivityRepository.save(budgetActivityMapper.asEntity(budgetActivityDTO));

        log.info("BudgetActivity successfully added {}", savedBudget);

        return budgetActivityMapper.asDto(savedBudget);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_DATES_IMPORTANTE)
    public BudgetActivityDTO update(BudgetActivityDTO budgetActivityDTO) {
        try{
            if(budgetActivityRepository.existsById(budgetActivityDTO.getId())) {
                var budget = budgetActivityMapper.asEntity(budgetActivityDTO);

                var updatedBudget= budgetActivityMapper.asDto(budgetActivityRepository.save(budget));

                log.info("BudgetActivity successfully updated {} ", updatedBudget.getId());

                return updatedBudget;
            } else {
                throw new ResourceNotFoundException("BudgetActivity", budgetActivityDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("BudgetActivity", budgetActivityDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public BudgetActivityDTO read(Long budgetActivityId) {
        var budget = budgetActivityRepository
                .findById(budgetActivityId)
                .orElseThrow(()-> new ResourceNotFoundException("BudgetActivity", budgetActivityId));

        log.info("reading budget id {}", budget);

        return budgetActivityMapper.asDto(budget);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_DATES_IMPORTANTE)
    public void delete(Long budgetActivityId) {
        try {
            budgetActivityRepository.deleteById(budgetActivityId);
            log.info("The budget id {} is deleted", budgetActivityId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Milestone", budgetActivityId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public Page<BudgetActivityDTO> readAll(
            Pageable pageable,
            Long typeBudgetId,
            Integer year,
            Double amount,
            String sortBy,
            Boolean ascending,
            Long projetId
    ) throws ParseException {
        return budgetActivityRepository
                .readAllByFiltering(pageable, typeBudgetId, year, amount,sortBy,ascending, projetId)
                .map(budgetActivityMapper::asDto);
    }


}