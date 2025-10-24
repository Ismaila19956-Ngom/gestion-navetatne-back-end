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
    FormationExterieurDTO createFormationExterieur(FormationExterieurDTO dto, Map<String, MultipartFile> files);
    FormationExterieurDTO updateFormationExterieur(FormationExterieurDTO dto, Map<String, MultipartFile> files);
    void deleteFormationExterieur(Long id);
    FormationExterieurDTO getFormationExterieurById(Long id);
    Page<FormationExterieurDTO> getAllFormationExterieurs(Map<String, String> searchParams, Pageable pageable);
    void exportFormationExterieurs(PrintWriter writer);
    List<FormationExterieurDTO> importFormationExterieurs(List<FormationExterieurDTO> dtos);
    FormationExterieurDTO updateStatut(Long id, Statut statut);
}
