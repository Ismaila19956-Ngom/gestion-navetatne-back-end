package com.webgram.dgpsn.services;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.models.EvaluationEnvironnementaleDTO;

import java.io.IOException;

public interface EvaluationEnvironnementaleService {

    EvaluationEnvironnementaleDTO create(EvaluationEnvironnementaleDTO evaluationEnvironnementaleDTO,
                                        MultipartFile carteGeographique,
                                        MultipartFile planMasse,
                                        MultipartFile planSituation,
                                        MultipartFile planInstallations,
                                        MultipartFile planReseaux,
                                        MultipartFile tdrEtude,
                                        MultipartFile attestationDomaine,
                                        MultipartFile bilanEau,
                                        MultipartFile[] autresDocuments) throws IOException;

    EvaluationEnvironnementaleDTO update(EvaluationEnvironnementaleDTO evaluationEnvironnementaleDTO,
                                        MultipartFile carteGeographique,
                                        MultipartFile planMasse,
                                        MultipartFile planSituation,
                                        MultipartFile planInstallations,
                                        MultipartFile planReseaux,
                                        MultipartFile tdrEtude,
                                        MultipartFile attestationDomaine,
                                        MultipartFile bilanEau,
                                        MultipartFile[] autresDocuments) throws IOException;

    EvaluationEnvironnementaleDTO read(Long evaluationEnvironnementaleId);

    void delete(Long evaluationEnvironnementaleId);

    Page<EvaluationEnvironnementaleDTO> readAll(Pageable pageable,
                                                Long programmeId,
                                                Long projetId,
                                                Long activiteId,
                                                Long directionId,
                                                Long promoteurId,
                                                String titreProjet,
                                                String sortBy,
                                                Boolean ascending);

    Resource downloadFile(Long evaluationEnvironnementaleId, String docType) throws IOException;
}