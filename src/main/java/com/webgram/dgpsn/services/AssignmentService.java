package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.entities.enums.StructureProjectType;
import com.webgram.dgpsn.models.AssignmentDTO;

import java.io.IOException;
import java.text.ParseException;

public interface AssignmentService {
    AssignmentDTO create(AssignmentDTO assignmentDTO);
    AssignmentDTO update(AssignmentDTO assignmentDTO);
    AssignmentDTO create(MultipartFile file, String assignment) throws IOException;
    AssignmentDTO update(MultipartFile file, AssignmentDTO assignment) throws IOException;
    AssignmentDTO read(Long assignmentId);
//    DownloadFile readFile(Long id);
    void delete(Long assignmentId);
    Page<AssignmentDTO> readAll(
            Pageable pageable,
            String libelle,
            String startDate,
            String endDate,
            StructureProjectType structureProjectType,
            Long assignmentTypeId,
            Long structureId,
            Long projetId
    ) throws ParseException;

//    void importAssignment(MultipartFile file, Long projectId);
//
//    void exportAssignment(PrintWriter writer);
}
