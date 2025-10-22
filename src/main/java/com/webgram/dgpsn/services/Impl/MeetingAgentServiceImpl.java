package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.entities.MeetingAgentEntity;
import com.webgram.dgpsn.entities.MeetingEntity;
import com.webgram.dgpsn.mappers.AgentMapper;
import com.webgram.dgpsn.models.AgentDTO;
import com.webgram.dgpsn.repositories.MeetingAgentRepository;
import com.webgram.dgpsn.services.MeetingAgentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MeetingAgentServiceImpl implements MeetingAgentService {
    private final MeetingAgentRepository meetingAgentRepository;

    private final AgentMapper agentMapper;

    @Override
    public List<AgentDTO> readAgentsByMeeting(Long meetingId) {
        var meeting = MeetingEntity.builder().id(meetingId).build();

        var agentEntity = meetingAgentRepository
                .findAllByMeeting(meeting).get()
                .stream().map(MeetingAgentEntity::getAgent).collect(Collectors.toList());

        return agentMapper.parse(agentEntity);
    }
}
