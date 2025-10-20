package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.entities.enums.TypeApplicant;
import com.webgram.dgpsn.models.QueryDTO;

import java.util.Date;

public interface QueryService {
    QueryDTO create(QueryDTO queryDTO);
    QueryDTO update(QueryDTO queryDTO);
//    QueryDTO create(MultipartFile file, String query) throws IOException;
//    QueryDTO update(MultipartFile file, QueryDTO queryDTO) throws IOException;
    QueryDTO read(Long queryId);
    void delete(Long queryId);
//    DownloadFile readFile(Long id);
    Page<QueryDTO> readAll(
            Pageable pageable,
            Date date,
            TypeApplicant typeDemandeur,
            TypeApplicant typeDestinataire,
            Long categorieRequeteId,
            Long typeRequeteId,
            Long demandeurActorId,
            Long demandeurStructureId,
            Long destinataireActorId,
            Long destinataireStructureId,
            Long projetId
    );

//    void importQuery(MultipartFile file, Long projectId);
//
//    void exportQuery(PrintWriter writer);
}
