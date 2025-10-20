package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.ReviewIssueLogEntity;
import com.webgram.dgpsn.models.requests.ReviewIssueLogDTO;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ReviewIssueLogMapper extends EntityMapper<ReviewIssueLogDTO, ReviewIssueLogEntity> {
}
