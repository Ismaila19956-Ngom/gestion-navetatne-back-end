package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.TypeRequeteDTO;

public interface TypeRequeteService {
    TypeRequeteDTO create(TypeRequeteDTO typeRequeteDTO);
    TypeRequeteDTO update(TypeRequeteDTO typeRequeteDTO);
    TypeRequeteDTO read(Long typeRequeteId);
    void delete(Long typeRequeteId);
    Page<TypeRequeteDTO> readAll(
            Pageable pageable,
             String code,
            String libelle,
            Long categorieRequeteId,
            String sortBy,
            Boolean ascending
    );
}
