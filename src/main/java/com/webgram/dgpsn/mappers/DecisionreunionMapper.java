package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.DecisionreunionEntity;
import com.webgram.dgpsn.models.DecisionreunionDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.DecisionreunionExcelDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class DecisionreunionMapper implements EntityMapper<DecisionreunionDto, DecisionreunionEntity> {

   @Mapping(target = "reunion.id", source = "reunionId")
   public abstract DecisionreunionEntity asEntity(DecisionreunionDto decisionreunionEntity);

      public abstract DecisionreunionDto asDto(DecisionreunionEntity decisionreunionEntity);
   public abstract DecisionreunionExcelDTO asExcelDto(DecisionreunionEntity entity);
   }
