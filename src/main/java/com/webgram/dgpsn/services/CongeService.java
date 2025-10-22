package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.entities.enums.StatutType;
import com.webgram.dgpsn.models.CongeDTO;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface CongeService {
    CongeDTO create(CongeDTO congeDTO);

    CongeDTO createDocumentConge(Long congeId, MultipartFile file, String document) throws IOException;

    CongeDTO update(Long congeId, CongeDTO congeDTO);

    CongeDTO read(Long congeId);

    List<CongeDTO> readAll();

    Page<CongeDTO> readPage(Map<String, String> searchParams, int page, int size, List<Long> agentIds) throws ParseException;

    void delete(Long congeId);

    void deleteDocumentConge(Long documentId);

    void validConge(Long CongeId, StatutType statut);

    CongeDTO updateNumeroDecision(Long congeId, String numeroDecision);
}
