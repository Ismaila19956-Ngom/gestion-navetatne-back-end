package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.PeriodiciteEntity;
import com.webgram.dgpsn.models.PeriodiciteDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.PeriodiciteExcelDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class PeriodiciteMapper implements EntityMapper<PeriodiciteDto, PeriodiciteEntity> {

   @Mapping(target = "periode.id", source = "periodeId")
   public abstract PeriodiciteEntity asEntity(PeriodiciteDto periodiciteEntity);

      public abstract PeriodiciteDto asDto(PeriodiciteEntity periodiciteEntity);
   public abstract PeriodiciteExcelDTO asExcelDto(PeriodiciteEntity entity);
   }
