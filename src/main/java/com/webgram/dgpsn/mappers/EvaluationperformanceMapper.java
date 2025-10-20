package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.EvaluationperformanceEntity;
import com.webgram.dgpsn.models.EvaluationperformanceDto;
import com.webgram.dgpsn.entities.TypedvaluationEntity;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.EvaluationperformanceExcelDTO;
import com.webgram.dgpsn.entities.RisqueEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

   @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class EvaluationperformanceMapper implements EntityMapper<EvaluationperformanceDto, EvaluationperformanceEntity> {

   @Mapping(target = "entreprise.id", source = "entrepriseId")
@Mapping(target = "typedvaluation", source = "typedvaluationId", qualifiedByName = "mapTypedvaluation")
@Mapping(target = "risque", source = "risqueId", qualifiedByName = "mapRisque")
   public abstract EvaluationperformanceEntity asEntity(EvaluationperformanceDto evaluationperformanceEntity);

   @Named("mapTypedvaluation")
   public TypedvaluationEntity mapTypedvaluation(Long typedvaluationId) {
       if (Objects.nonNull(typedvaluationId)) {
           return TypedvaluationEntity.builder().id(typedvaluationId).build();
       }
       return null;
    }
@Named("mapRisque")
   public RisqueEntity mapRisque(Long risqueId) {
       if (Objects.nonNull(risqueId)) {
           return RisqueEntity.builder().id(risqueId).build();
       }
       return null;
    }
   public abstract EvaluationperformanceDto asDto(EvaluationperformanceEntity evaluationperformanceEntity);
   public abstract EvaluationperformanceExcelDTO asExcelDto(EvaluationperformanceEntity entity);
   }
