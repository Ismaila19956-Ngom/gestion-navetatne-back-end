package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.MeetingDTO;
import com.webgram.dgpsn.models.MeetingAgentDTO;


import java.sql.Time;
import java.util.Date;

public interface MeetingService {
//    MeetingDTO create(MeetingAgentDTO meetingAgentDTO);
MeetingDTO create(MeetingAgentDTO meetingAgentDTO);

 MeetingDTO updateMeetingAgent(MeetingAgentDTO meetingAgentDTO);
//  void deleteMeetingAgent(Long meetingAgentId);
    MeetingDTO update(MeetingDTO meetingDTO);
    MeetingDTO read(Long meetinId);
    void delete(Long meetingId);
    Page<MeetingDTO> readAll(
            Pageable pageable,
            String libelle,
            Date predicatedDate,
            Date readDate,
            String comment,
            Time heureDebutPrevue,
            Time heureFinPrevue,
            Time heureDebutReelle,
            Time heureFinReelle,
            Long meetingTypeId,
            Long projetId
    );
}
