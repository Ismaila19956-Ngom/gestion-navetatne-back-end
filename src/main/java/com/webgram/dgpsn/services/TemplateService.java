package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.entities.enums.CategorieAlerte;
import com.webgram.dgpsn.entities.enums.Priority;
import com.webgram.dgpsn.entities.enums.TypeAlerte;
import com.webgram.dgpsn.models.TemplateDto;

import java.util.Set;


public interface TemplateService {
    TemplateDto create(TemplateDto templateDto);
    TemplateDto update(TemplateDto templateDto);
    TemplateDto read(Long id);
    void delete(Long id);
    Page<TemplateDto> readAll(Pageable pageable, String libelle,CategorieAlerte categorieAlerte, TypeAlerte typeAlerte , Priority priority);

    Set<TypeAlerte> readAlertTypes(CategorieAlerte categorieAlerte);

    Set<CategorieAlerte> readAllCategorieAlerte();

}
