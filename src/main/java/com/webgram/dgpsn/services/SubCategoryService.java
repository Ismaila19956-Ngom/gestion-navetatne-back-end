package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.SubCategoryDTO;

public interface SubCategoryService {
    SubCategoryDTO createSubCategory(SubCategoryDTO subCategoryDTO);
    SubCategoryDTO updateSubCategory(SubCategoryDTO subCategoryDTO);
    SubCategoryDTO readSubCategory(Long id);
    SubCategoryDTO readSubCategoryByCode(String code);
    void deleteSubCategory(Long id);
    Page<SubCategoryDTO> readAllSubCategory(Pageable pageable, String code, String libelle);

}
