package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.WorkflowStepEntity;
import com.webgram.dgpsn.models.WorkflowStepDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class WorkflowStepMapper implements EntityMapper<WorkflowStepDTO, WorkflowStepEntity> {
    @Override
    @Mapping(target = "workflow.id", source = "workflowId")
    public abstract WorkflowStepEntity asEntity(WorkflowStepDTO dto);
}
