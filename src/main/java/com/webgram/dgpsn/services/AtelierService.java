package com.webgram.dgpsn.services;
import com.webgram.dgpsn.entities.enums.Statut;
import com.webgram.dgpsn.models.AtelierDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public interface AtelierService {
    AtelierDTO createAtelier(AtelierDTO dto, Map<String, MultipartFile> files);
    AtelierDTO updateAtelier(AtelierDTO dto, Map<String, MultipartFile> files);
    void deleteAtelier(Long id);
    AtelierDTO getAtelierById(Long id);
    Page<AtelierDTO> getAllAteliers(Map<String, String> searchParams, Pageable pageable);
    void exportAteliers(PrintWriter writer);
    List<AtelierDTO> importAteliers(List<AtelierDTO> dtos);
}