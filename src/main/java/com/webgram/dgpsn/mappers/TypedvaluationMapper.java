package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.TypedvaluationEntity;
import com.webgram.dgpsn.models.TypedvaluationDto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.TypedvaluationExcelDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class TypedvaluationMapper implements EntityMapper<TypedvaluationDto, TypedvaluationEntity> {

      public abstract TypedvaluationEntity asEntity(TypedvaluationDto typedvaluationEntity);

      public abstract TypedvaluationDto asDto(TypedvaluationEntity typedvaluationEntity);
   public abstract TypedvaluationExcelDTO asExcelDto(TypedvaluationEntity entity);
   }
