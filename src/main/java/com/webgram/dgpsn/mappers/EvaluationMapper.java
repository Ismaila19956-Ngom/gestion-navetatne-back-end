package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.EvaluationEntity;
import com.webgram.dgpsn.models.EvaluationDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface EvaluationMapper extends EntityMapper<EvaluationDTO, EvaluationEntity> {

    EvaluationEntity asEntity(EvaluationDTO evaluationDTO);

    EvaluationDTO asDto(EvaluationEntity evaluationEntity);
}