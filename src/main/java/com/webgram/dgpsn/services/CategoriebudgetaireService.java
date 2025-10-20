package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


import com.webgram.dgpsn.models.CategoriebudgetaireDto;

public interface CategoriebudgetaireService {

    CategoriebudgetaireDto create(CategoriebudgetaireDto categoriebudgetaire);

    CategoriebudgetaireDto update(CategoriebudgetaireDto categoriebudgetaire);

    CategoriebudgetaireDto read(Long id);

    void delete(Long id);

    Page<CategoriebudgetaireDto> readAll(Map<String, String> searchParams, Pageable pageable);

    }