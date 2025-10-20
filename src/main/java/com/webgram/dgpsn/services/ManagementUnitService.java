package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.entities.enums.TypeProjet;
import com.webgram.dgpsn.models.DownloadFile;
import com.webgram.dgpsn.models.ManagementUnitDTO;
import com.webgram.dgpsn.models.TreeNodeDTO;
import com.webgram.dgpsn.models.responses.StatisticProjectDTO;

import java.io.PrintWriter;
import java.util.List;


public interface ManagementUnitService {
    ManagementUnitDTO create(ManagementUnitDTO managementUnitDTO);
    ManagementUnitDTO update(ManagementUnitDTO managementUnitDTO);
    ManagementUnitDTO read(Long projetId);
    void delete(Long projetId);
    Page<ManagementUnitDTO> readAll(
            Pageable pageable,
            String code,
            String name,
            TypeProjet type,
            String dateDebut,
            String dateFin,
            Double budget,
            Long poids,
            Long responsibleId,
            String tag,
            Long parentId,
            Long axePSEId,
            Boolean publish
    );
    void importProject(MultipartFile file);
    void export(PrintWriter writer);
    void exportProjets(PrintWriter writer);
    DownloadFile generateFilePdf();
    DownloadFile redFile(Long id);
    void uploadImage(Long id, MultipartFile file);
    void publishOrUnpublish(Long documentId);
    List<ManagementUnitDTO> readPublishedProjects();

    StatisticProjectDTO readStatisticProject();
    TreeNodeDTO readTreeManagmentUnit(Long projectId);
    TreeNodeDTO addNodeToTreeManagmentUnit(Long parentId, TreeNodeDTO nodeDTO);
    List<TreeNodeDTO> readAllProjectsWithTree();
}

