package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.CompletedActivityDTO;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CompletedActivityMapper extends EntityMapper<CompletedActivityDTO, CompletedActivityEntity> {

    @Mapping(target = "issueLog", source = "issueLogId", qualifiedByName = "getIssueLog")
    @Override
    CompletedActivityEntity asEntity(CompletedActivityDTO completedActivityDTO);

    @Named("getIssueLog")
    default IssueLogEntity getIssueLog(Long issueLogId) {

        if(Objects.nonNull(issueLogId)) {
            return IssueLogEntity.builder().id(issueLogId).build();
        }
        return null;
    }
}
