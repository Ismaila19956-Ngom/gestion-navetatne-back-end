package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.EvaluationfinanciereEntity;
import com.webgram.dgpsn.models.EvaluationfinanciereDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.EvaluationfinanciereExcelDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class EvaluationfinanciereMapper implements EntityMapper<EvaluationfinanciereDto, EvaluationfinanciereEntity> {

   @Mapping(target = "entreprise.id", source = "entrepriseId")
   public abstract EvaluationfinanciereEntity asEntity(EvaluationfinanciereDto evaluationfinanciereEntity);

      public abstract EvaluationfinanciereDto asDto(EvaluationfinanciereEntity evaluationfinanciereEntity);
   public abstract EvaluationfinanciereExcelDTO asExcelDto(EvaluationfinanciereEntity entity);
   }
