package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.TypeExpenseMapper;
import com.webgram.dgpsn.models.TypeExpenseDTO;
import com.webgram.dgpsn.repositories.TypeExpenseRepository;
import com.webgram.dgpsn.services.TypeExpenseService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TypeExpenseServiceImpl implements TypeExpenseService {
    private final TypeExpenseRepository expenseRepository;
    private final TypeExpenseMapper typeExpenseMapper;

    @Override
    @Journal(actionType= ActionType.ADD_INDICATEURS_TO_AVANCEMENT)
    public TypeExpenseDTO create(TypeExpenseDTO typeExpenseDTO) {
         var savedTypeExpense = expenseRepository.save(typeExpenseMapper.asEntity(typeExpenseDTO));

        log.info("TypeExpense successfully added {}", savedTypeExpense);

        return typeExpenseMapper.asDto(savedTypeExpense);
    }

    @Override
    @Journal(actionType=ActionType.UPDATE_INDICATEURS_TO_AVANCEMENT)
    public TypeExpenseDTO update(TypeExpenseDTO typeExpenseDTO) {
        try{
            if(expenseRepository.existsById(typeExpenseDTO.getId())) {
                var indicator = typeExpenseMapper.asEntity(typeExpenseDTO);

                var updatedExpense = expenseRepository.save(indicator);

                log.info("Expense successfully updated {} ", updatedExpense.getId());

                return typeExpenseMapper.asDto(updatedExpense);
            } else {
                throw new ResourceNotFoundException("Expense", typeExpenseDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Expense", typeExpenseDTO.getId());
        }
    }

    @Override
    @Journal(actionType=ActionType.READ_INDICATEURS_TO_AVANCEMENT)
    public TypeExpenseDTO read(Long expenseId) {
        var Expense = expenseRepository
                .findById(expenseId)
                .orElseThrow(()-> new ResourceNotFoundException("Expense", expenseId));

        log.info("reading Expense id {}", expenseId);

        return typeExpenseMapper.asDto(Expense);
    }

    @Override
    @Journal(actionType=ActionType.DELETE_INDICATEURS_TO_AVANCEMENT)
    public void delete(Long expenseId) {
        try {
            expenseRepository.deleteById(expenseId);
            log.info("The expense id {} is deleted", expenseId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("expenseId", expenseId);
        }
    }

    @Override
    @Journal(actionType=ActionType.READ_INDICATEURS_TO_AVANCEMENT)
    public Page<TypeExpenseDTO> readAll(
            Pageable pageable,
            String code,
            String libelle,
            Long categorieDepenseId,
            String sortBy,
            Boolean ascending
    ) {
        return expenseRepository
                .readAllByFilters(pageable, code, libelle, categorieDepenseId,sortBy,
                         ascending)
                .map(typeExpenseMapper::asDto);
    }
}
