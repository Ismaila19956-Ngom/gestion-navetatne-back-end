package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.RisqueEntity;
import com.webgram.dgpsn.models.RisqueDto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.RisqueExcelDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class RisqueMapper implements EntityMapper<RisqueDto, RisqueEntity> {

      public abstract RisqueEntity asEntity(RisqueDto risqueEntity);

      public abstract RisqueDto asDto(RisqueEntity risqueEntity);
   public abstract RisqueExcelDTO asExcelDto(RisqueEntity entity);
   }
