package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.MeetingAgentEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.AgentMapper;
import com.webgram.dgpsn.mappers.MeetingAgentMapper;
import com.webgram.dgpsn.mappers.MeetingMapper;
import com.webgram.dgpsn.models.MeetingAgentDTO;
import com.webgram.dgpsn.models.MeetingDTO;
import com.webgram.dgpsn.repositories.AgentRepository;
import com.webgram.dgpsn.repositories.MeetingAgentRepository;
import com.webgram.dgpsn.repositories.MeetingRepository;
import com.webgram.dgpsn.services.MeetingService;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MeetingServiceImpl implements MeetingService {
    private final MeetingRepository meetingRepository;
    private final MeetingAgentRepository meetingAgentRepository;
    private final AgentRepository agentRepository;

    private final MeetingMapper meetingMapper;
    private final MeetingAgentMapper meetingAgentMapper;
    private final AgentMapper agentMapper;

    @Override
    @Journal(actionType = ActionType.ADD_MEETING)
    public MeetingDTO create(MeetingAgentDTO meetingAgentDTO) {
        var meetingDTO= saveMeeting(meetingAgentDTO);
        System.out.println("Contenu de meetingDTO : " + meetingDTO);
        log.info("meeting {} successfully added", meetingDTO);

        return meetingDTO;
    }

    @Override
    public MeetingDTO updateMeetingAgent(MeetingAgentDTO meetingAgentDTO) {
        if(meetingAgentRepository.existsByMeetingId(meetingAgentDTO.getMeeting().getId())) {
            meetingAgentRepository.deleteByMeetingId(meetingAgentDTO.getMeeting().getId());
        }

        var meetingDTO= saveMeeting(meetingAgentDTO);

        log.info("IssueLog {} successfully updated", meetingDTO);

        log.info("agents {}", meetingAgentDTO.getAgents());

        return meetingDTO;
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_MEETING)
    public MeetingDTO update(MeetingDTO meetingDTO) {
        try{
            if(meetingRepository.existsById(meetingDTO.getId())) {
                var meeting = meetingMapper.asEntity(meetingDTO);

                var updatedMeeting = meetingRepository.save(meeting);

                log.info("meeting successfully updated {} ", meeting.getId());

                return meetingMapper.asDto(updatedMeeting);
            } else {
                throw new ResourceNotFoundException("meeting", meetingDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("meeting", meetingDTO.getId());
        }
    }

    @Override
    public MeetingDTO read(Long meetingId) {
        var meeting = meetingRepository
                .findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting", meetingId));

        log.info("reading meeting id {}", meeting);

        return meetingMapper.asDto(meeting);

    }

    @Override
    @Journal(actionType = ActionType.READ_MEETING)
    public Page<MeetingDTO> readAll(
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
    ) {
        return meetingRepository
                .readAllByFiltering(pageable, libelle, predicatedDate, readDate,comment, heureDebutPrevue, heureFinPrevue,
                        heureDebutReelle, heureFinReelle, meetingTypeId, projetId)
                .map(meetingMapper::asDto);
    }


    @Override
    @Journal(actionType = ActionType.DELETE_MEETING)
    public void delete(Long meetingId) {
        try {
            List<MeetingAgentEntity> meetingAgents = meetingAgentRepository.findByMeetingId(meetingId);
            for (MeetingAgentEntity meetingAgent : meetingAgents) {
                meetingAgent.setMeeting(null);
                meetingAgentRepository.save(meetingAgent);
            }

            meetingRepository.deleteById(meetingId);
            log.info("The meeting id {} is deleted", meetingId);
        } catch (Exception ex) {

            log.error("Error deleting meeting with id {}", meetingId, ex);
            throw ex;
        }
    }


    MeetingDTO saveMeeting(MeetingAgentDTO meetingAgentDTO) {
        var meetingDTO = meetingAgentDTO.getMeeting();
    var meeting = meetingMapper.asEntity(meetingDTO);
    var meetingSaved =  meetingRepository.save(meeting);

    var agentsId = meetingAgentDTO.getAgents();

    if(Objects.nonNull(agentsId)) {
        var meetingAgents = new ArrayList<MeetingAgentEntity>();

        for(Long id : agentsId){
            var agent = agentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("risk not found by id", id));
            var meetingAgent = MeetingAgentEntity
                    .builder()
                    .agent(agent)
                    .meeting(meetingSaved)
                    .build();
            meetingAgents.add(meetingAgent);
        }

        meetingAgentRepository.saveAll(meetingAgents);
    }
        return meetingMapper.asDto(meetingSaved);
    }
}