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
import com.webgram.dgpsn.mappers.ExpenseActivityMapper;
import com.webgram.dgpsn.models.ExpenseActivityDTO;
import com.webgram.dgpsn.repositories.ExpenseActivityRepository;
import com.webgram.dgpsn.repositories.ManagementUnitRepository;
import com.webgram.dgpsn.services.ExpenseActivityService;

import java.text.ParseException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ExpenseActivityServiceImpl implements ExpenseActivityService {
    private final ExpenseActivityRepository expenseActivityRepository;
    private final ExpenseActivityMapper expenseActivityMapper;
    private final ManagementUnitRepository managementUnitRepository;
    private final WorkbookService workbookService;

    @Override
    @Journal(actionType = ActionType.ADD_DATES_IMPORTANTE)
    public ExpenseActivityDTO create(ExpenseActivityDTO expenseActivityDTO) {
         var savedExpense = expenseActivityRepository.save(expenseActivityMapper.asEntity(expenseActivityDTO));

        log.info("ExpenseActivity successfully added {}", savedExpense);

        return expenseActivityMapper.asDto(savedExpense);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_DATES_IMPORTANTE)
    public ExpenseActivityDTO update(ExpenseActivityDTO expenseActivityDTO) {
        try{
            if(expenseActivityRepository.existsById(expenseActivityDTO.getId())) {
                var expense = expenseActivityMapper.asEntity(expenseActivityDTO);

                var updatedExpense= expenseActivityMapper.asDto(expenseActivityRepository.save(expense));

                log.info("ExpenseActivity successfully updated {} ", updatedExpense.getId());

                return updatedExpense;
            } else {
                throw new ResourceNotFoundException("ExpenseActivity", expenseActivityDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("ExpenseActivity", expenseActivityDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public ExpenseActivityDTO read(Long expenseActivityId) {
        var expense = expenseActivityRepository
                .findById(expenseActivityId)
                .orElseThrow(()-> new ResourceNotFoundException("ExpenseActivity", expenseActivityId));

        log.info("reading expense id {}", expense);

        return expenseActivityMapper.asDto(expense);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_DATES_IMPORTANTE)
    public void delete(Long expenseActivityId) {
        try {
            expenseActivityRepository.deleteById(expenseActivityId);
            log.info("The expense id {} is deleted", expenseActivityId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("ExpenseActivity", expenseActivityId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public Page<ExpenseActivityDTO> readAll(
            Pageable pageable,
            String date,
            String unitAmount,
            String quantity,
            Double totalAmount,
            Long categorieDepenseId,
            Long typeDepenseId,
            Long projetId
    ) throws ParseException {
        return expenseActivityRepository
                .readAllByFiltering(pageable,
                        date,
                        unitAmount,
                        quantity,
                        totalAmount,
                        categorieDepenseId,
                        typeDepenseId,
                        projetId)
                .map(expenseActivityMapper::asDto);
    }


}