package com.webgram.dgpsn.services;

import com.webgram.dgpsn.models.OrdreMissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

public interface OrdreMissionService {
    OrdreMissionDTO create(OrdreMissionDTO ordreMissionDTO) throws IOException;

    OrdreMissionDTO createDocumentOrdreMission(Long ordreMissionId,MultipartFile file, String document) throws IOException;

    OrdreMissionDTO update(OrdreMissionDTO ordreMissionDTO) throws IOException;

    OrdreMissionDTO read(Long ordreMissionId);

    OrdreMissionDTO updateStatusOrdreMission(Long ordreMissionId,String statut);


    void delete(Long ordreMissionId);

    void deleteDocumentOrdreMission(Long documentId);
    Page<OrdreMissionDTO> readAll(
            Pageable pageable,
            String groupe,
            String indice,
            String objectMission,
            String priseEnCharge,
            String frais,
            Long ordreMissionId,
            Long agentId,
            String sortBy,
            Boolean ascending,
            List<Long> agentIds
    );

}
