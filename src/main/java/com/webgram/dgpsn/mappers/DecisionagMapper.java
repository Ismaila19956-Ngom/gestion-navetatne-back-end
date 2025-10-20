package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.DecisionagEntity;
import com.webgram.dgpsn.models.DecisionagDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.DecisionagExcelDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class DecisionagMapper implements EntityMapper<DecisionagDto, DecisionagEntity> {

   @Mapping(target = "assemblegeneral.id", source = "assemblegeneralId")
   public abstract DecisionagEntity asEntity(DecisionagDto decisionagEntity);

      public abstract DecisionagDto asDto(DecisionagEntity decisionagEntity);
   public abstract DecisionagExcelDTO asExcelDto(DecisionagEntity entity);
   }
