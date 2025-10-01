package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.WorkflowEntity;
import com.webgram.dgpsn.models.WorkflowDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class WorkflowMapper implements EntityMapper<WorkflowDTO, WorkflowEntity> {
}
