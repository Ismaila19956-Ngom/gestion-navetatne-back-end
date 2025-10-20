package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.ParticipantMissionDTO;

public interface ParticipantMissionService {
    ParticipantMissionDTO create(ParticipantMissionDTO participantMissionDTO);
    ParticipantMissionDTO update(ParticipantMissionDTO participantMissionDTO);
    ParticipantMissionDTO read(Long participantId);
    void delete(Long participantId);
    Page<ParticipantMissionDTO> readAll(Pageable pageable, Long assignmentId, Long actorId, Long roleId);
//    void importActor(MultipartFile file, Long projectId);
//    void exportActor(PrintWriter writer);
}
