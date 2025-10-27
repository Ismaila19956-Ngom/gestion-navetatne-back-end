package com.webgram.dgpsn.services;
import com.webgram.dgpsn.entities.enums.Statut;
import com.webgram.dgpsn.entities.enums.StatutType;
import com.webgram.dgpsn.models.FormationExterieurDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public interface FormationExterieurService {
    FormationExterieurDTO create(FormationExterieurDTO dto);
    FormationExterieurDTO update(FormationExterieurDTO dto);
    FormationExterieurDTO read(Long id);
    void delete(Long id);
    Page<FormationExterieurDTO> readAll(Map<String, String> searchParams, Pageable pageable);
}