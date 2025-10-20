package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.entities.enums.CategoryDocument;
import com.webgram.dgpsn.models.CategorieDocumentDTO;

public interface CategorieDocumentService {
    CategorieDocumentDTO create(CategorieDocumentDTO categorieDocumentDTO);
    CategorieDocumentDTO update(CategorieDocumentDTO categorieDocumentDTO);
    CategorieDocumentDTO read(Long docId);
    void delete(Long docId);
    Page<CategorieDocumentDTO> readAll(Pageable pageable, CategoryDocument categoryDocument);
}
