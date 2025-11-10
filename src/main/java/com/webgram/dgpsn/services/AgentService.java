package com.webgram.dgpsn.services;

import com.webgram.dgpsn.models.AgentCountByDirectionDTO;
import com.webgram.dgpsn.models.AgentDashboardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.models.AgentDTO;
import com.webgram.dgpsn.entities.enums.TypeStructure;
import com.webgram.dgpsn.models.DownloadFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

public interface AgentService {
    AgentDTO create(MultipartFile file, String agent) throws IOException;
    AgentDTO update(MultipartFile file, AgentDTO agentDTO) throws IOException;
    AgentDTO read(Long agentId);
    void delete(Long agentId);
    DownloadFile readFile(Long id);
    Page<AgentDTO> readAll(
            Pageable pageable,
            List<Long> idsToIgnore,
            TypeStructure typeStructure,
            String nom,
            String prenom,
            String adresse,
            String email,
            String telephone,
            Date dateCreation,
            Long structureId,
            Long fonctionId,
            Long directionId,
            String sortBy,
            Boolean ascending
    );
    void importAgent(MultipartFile file);
    void exportAgent(PrintWriter writer);
    List<AgentDTO> getAgentNotInUsers();
//    AgentDashboardDTO getAgentDashboard();
//    List<AgentCountByDirectionDTO> AgentCountByDirection();
}
