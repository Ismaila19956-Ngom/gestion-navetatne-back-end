package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.TypeExpenseDTO;

public interface TypeExpenseService {
    TypeExpenseDTO create(TypeExpenseDTO typeExpenseDTO);
    TypeExpenseDTO update(TypeExpenseDTO typeExpenseDTO);
    TypeExpenseDTO read(Long typeExpenseId);

    void delete(Long typeExpenseId);
    Page<TypeExpenseDTO> readAll(
            Pageable pageable,
             String code,
            String libelle,
            Long categorieDepenseId,
            String sortBy,
            Boolean ascending
    );
}
