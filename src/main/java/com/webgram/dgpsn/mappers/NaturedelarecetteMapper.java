package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.NaturedelarecetteEntity;
import com.webgram.dgpsn.models.NaturedelarecetteDto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.NaturedelarecetteExcelDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class NaturedelarecetteMapper implements EntityMapper<NaturedelarecetteDto, NaturedelarecetteEntity> {

      public abstract NaturedelarecetteEntity asEntity(NaturedelarecetteDto naturedelarecetteEntity);

      public abstract NaturedelarecetteDto asDto(NaturedelarecetteEntity naturedelarecetteEntity);
   public abstract NaturedelarecetteExcelDTO asExcelDto(NaturedelarecetteEntity entity);
   }
