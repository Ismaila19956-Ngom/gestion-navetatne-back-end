package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.EtapeMissionDTO;

import java.text.ParseException;
import java.util.Date;

public interface EtapeMissionService {
     EtapeMissionDTO create( EtapeMissionDTO etapeMissionDTO);
     EtapeMissionDTO update( EtapeMissionDTO etapeMissionDTO);
     EtapeMissionDTO read(Long etapeMissionId);
    void delete(Long etapeMissionId);
    Page<EtapeMissionDTO> readAll(
            Pageable pageable,
            String libelle,
            Date plannedStartDate,
            Date planedEndDate,
            Date actualStartDate,
            Date actualEndDate,
            Long statusId,
            Long responsableId,
            Long assignmentId
    ) throws ParseException;
//    void importMilestone(MultipartFile file, Long projectId);
//    void exportMilsstone(PrintWriter writer);
}
