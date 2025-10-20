package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.MeetingAgentEntity;
import com.webgram.dgpsn.entities.MeetingEntity;
import com.webgram.dgpsn.models.AgentDTO;
import com.webgram.dgpsn.models.MeetingDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class MeetingMapper implements EntityMapper<MeetingDTO, MeetingEntity> {
    @Autowired
    AgentMapper agentMapper;

    @Override
    @Mapping(target = "agents", source = "meetingAgents", qualifiedByName = "extractAgents")
    public abstract MeetingDTO asDto(MeetingEntity entity);

//    @Named("extractAgents")
//    public List<AgentDTO> extractAgents(List<MeetingAgentEntity> meetingAgents) {
//        return meetingAgents.stream()
//                .map(MeetingAgentEntity::getAgent)
//                .map(agentMapper::asDto)
//                .collect(Collectors.toList());
//    }
            @Named("extractAgents")
            public List<AgentDTO> extractAgents(List<MeetingAgentEntity> meetingAgents) {
                if (meetingAgents == null) {
                    return Collections.emptyList(); // ou toute autre valeur par défaut appropriée
                }
                return meetingAgents.stream()
                        .map(MeetingAgentEntity::getAgent)
                        .map(agentMapper::asDto)
                        .collect(Collectors.toList());
            }






    @Override
    @Mapping(target = "projet.id", source = "projetId")
    @Mapping(target = "meetingType.id", source = "meetingTypeId")
    @Mapping(target = "heureDebutPrevue", source = "heureDebutPrevue", qualifiedByName = "parseTimes")
    @Mapping(target = "heureFinPrevue", source = "heureFinPrevue", qualifiedByName = "parseTimes")
    @Mapping(target = "heureDebutReelle", source = "heureDebutReelle", qualifiedByName = "parseTimes")
    @Mapping(target = "heureFinReelle", source = "heureFinReelle", qualifiedByName = "parseTimes")
    public abstract MeetingEntity asEntity(MeetingDTO dto);

    @Named("parseTimes")
    public Date parseTimes(String time) {
        try {
            return new SimpleDateFormat("HH:MM").parse(time);
        } catch (ParseException ex) {
            System.out.println("Parsing heureDebutPrevue... error");
            return null;
        }
    }
}
