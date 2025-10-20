package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.CompanyEvaluationEntity;
import com.webgram.dgpsn.models.CompanyEvaluationDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CompanyEvaluationMapper extends EntityMapper<CompanyEvaluationDTO, CompanyEvaluationEntity> {

    @Mapping(source = "startup.id", target = "startupId")
    @Mapping(source = "evaluation.id", target = "evaluationId")
    @Mapping(source = "category.id", target = "categoryId")
    CompanyEvaluationDTO asDto(CompanyEvaluationEntity entity);

    @Mapping(source = "startupId", target = "startup.id")
    @Mapping(source = "evaluationId", target = "evaluation.id")
    @Mapping(source = "categoryId", target = "category.id")
    CompanyEvaluationEntity asEntity(CompanyEvaluationDTO dto);


}