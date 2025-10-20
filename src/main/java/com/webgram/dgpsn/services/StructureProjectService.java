package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.entities.enums.StructureProjectType;
import com.webgram.dgpsn.entities.enums.TypeStructure;
import com.webgram.dgpsn.models.StructureProjectDTO;
import com.webgram.dgpsn.models.responses.ReportingByStructureDTO;

import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;


public interface StructureProjectService {
    StructureProjectDTO create(StructureProjectDTO partnerProjetDTO);
    StructureProjectDTO update(StructureProjectDTO partnerProjetDTO);
    StructureProjectDTO read(Long partnerProjectId);
    void delete(Long partnerProjectId);
    Page<StructureProjectDTO> readAll(
            Pageable pageable,
            Double montant,
            Long projetId,
            Long structureId,
            TypeStructure typeStructure,
            StructureProjectType structureProjectType,
            List<StructureProjectType> structureProjectTypeList,
            String sortBy,
            Boolean ascending,
             String startDate,
            String endDate
    )throws ParseException;
    void importPartener(MultipartFile file, Long projectId);
    void exportPartener(PrintWriter writer);

    void linkManyStructure(List<StructureProjectDTO> partnerProjet);
    List<ReportingByStructureDTO> getReportingByStructrucure(Long structureId);
}
