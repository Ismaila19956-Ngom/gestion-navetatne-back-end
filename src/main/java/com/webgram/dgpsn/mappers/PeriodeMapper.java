package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.PeriodeEntity;
import com.webgram.dgpsn.models.PeriodeDto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.PeriodeExcelDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class PeriodeMapper implements EntityMapper<PeriodeDto, PeriodeEntity> {

      public abstract PeriodeEntity asEntity(PeriodeDto periodeEntity);

      public abstract PeriodeDto asDto(PeriodeEntity periodeEntity);
   public abstract PeriodeExcelDTO asExcelDto(PeriodeEntity entity);
   }
