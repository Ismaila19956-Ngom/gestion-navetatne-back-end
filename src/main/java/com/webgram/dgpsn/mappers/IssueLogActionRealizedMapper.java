package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.IssueLogActionRealizedEntity;
import com.webgram.dgpsn.entities.IssueLogEntity;
import com.webgram.dgpsn.models.IssueLogActionRealizedDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface IssueLogActionRealizedMapper extends EntityMapper<IssueLogActionRealizedDTO, IssueLogActionRealizedEntity> {

    @Override
    @Mapping(target = "issueLog", source = "issueLogId", qualifiedByName = "getIssueLog")
    IssueLogActionRealizedEntity asEntity(IssueLogActionRealizedDTO dto);

    @Named("getIssueLog")
    default IssueLogEntity getIssueLog(Long issueLogId) {
        return IssueLogEntity.builder().id(issueLogId).build();
    }
}
