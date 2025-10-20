package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.MeetingAgentEntity;
import com.webgram.dgpsn.models.MeetingAgentDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MeetingAgentMapper extends EntityMapper<MeetingAgentDTO, MeetingAgentEntity> {

    @Override
    MeetingAgentDTO asDto(MeetingAgentEntity entity);
}
