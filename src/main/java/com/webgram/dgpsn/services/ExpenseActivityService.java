package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.ExpenseActivityDTO;

import java.text.ParseException;

public interface ExpenseActivityService {
    ExpenseActivityDTO create(ExpenseActivityDTO expenseActivityDTO);
    ExpenseActivityDTO update(ExpenseActivityDTO expenseActivityDTO);
    ExpenseActivityDTO read(Long expenseActivityId);
    void delete(Long expenseActivityId);
    Page<ExpenseActivityDTO> readAll(
            Pageable pageable,
            String date,
            String unitAmount,
            String quantity,
            Double totalAmount,
            Long categorieDepenseId,
            Long typeDepenseId,
            Long projetId
    ) throws ParseException;
//    void importMilestone(MultipartFile file, Long projectId);
//    void exportMilsstone(PrintWriter writer);
}
