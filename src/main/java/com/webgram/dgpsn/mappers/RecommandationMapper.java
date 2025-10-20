package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.RecommandationDTO;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RecommandationMapper extends EntityMapper<RecommandationDTO, RecommendationEntity> {

    @Override
    @Mapping(target = "issueLog", source = "issueLogId", qualifiedByName = "getIssueLog")
    @Mapping(target = "risk", source = "riskId", qualifiedByName = "getRisk")
    @Mapping(target = "assignment", source = "assignmentId", qualifiedByName = "getAssignment")
    @Mapping(target = "status", source = "statusId", qualifiedByName = "getStatus")
    RecommendationEntity asEntity(RecommandationDTO dto);

    @Named("getIssueLog")
    default IssueLogEntity getIssueLog(Long issueLogId) {
        if (Objects.nonNull(issueLogId)){
            return IssueLogEntity.builder().id(issueLogId).build();
        } else {
            return null;
        }
    }

    @Named("getRisk")
    default RiskEntity getRisk(Long riskId) {
        if (Objects.nonNull(riskId)){
            return RiskEntity.builder().id(riskId).build();
        } else {
            return null;
        }
    }

    @Named("getAssignment")
    default AssignmentEntity getAssignment(Long assignmentId) {
        if (Objects.nonNull(assignmentId)){
            return AssignmentEntity.builder().id(assignmentId).build();
        } else {
            return null;
        }
    }

    @Named("getStatus")
    default StatusEntity getStatus(Long statusId) {
        if (Objects.nonNull(statusId)){
            return StatusEntity.builder().id(statusId).build();
        } else {
            return null;
        }
    }
}
