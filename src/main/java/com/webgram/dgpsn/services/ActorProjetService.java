package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.models.ActorProjetDTO;

import java.io.PrintWriter;

public interface ActorProjetService {
    ActorProjetDTO create(ActorProjetDTO actorsProject);
    ActorProjetDTO update(ActorProjetDTO actorProject);
    ActorProjetDTO read(Long actorProjectId);
    void delete(Long actorProjectId);
    Page<ActorProjetDTO> readAll(Pageable pageable, Long projectId, Long agentId, Long roleId);
    void importActor(MultipartFile file, Long projectId);
    void exportActor(PrintWriter writer);
}
