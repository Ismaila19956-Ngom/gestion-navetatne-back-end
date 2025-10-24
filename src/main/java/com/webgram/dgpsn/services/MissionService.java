package com.webgram.dgpsn.services;

import com.webgram.dgpsn.entities.enums.Statut;
import com.webgram.dgpsn.entities.enums.StatutType;
import com.webgram.dgpsn.models.MissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public interface MissionService {
    MissionDTO createMission(MissionDTO dto, Map<String, MultipartFile> files);
    MissionDTO updateMission(MissionDTO dto, Map<String, MultipartFile> files);
    void deleteMission(Long id);
    MissionDTO getMissionById(Long id);
    Page<MissionDTO> getAllMissions(Map<String, String> searchParams, Pageable pageable);
    void exportMissions(PrintWriter writer);
    List<MissionDTO> importMissions(List<MissionDTO> dtos);
    MissionDTO updateStatut(Long id, Statut statut);
}