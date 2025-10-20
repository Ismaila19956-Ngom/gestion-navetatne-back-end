package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.ProcedurenominationEntity;
import com.webgram.dgpsn.models.ProcedurenominationDto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.ProcedurenominationExcelDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class ProcedurenominationMapper implements EntityMapper<ProcedurenominationDto, ProcedurenominationEntity> {

      public abstract ProcedurenominationEntity asEntity(ProcedurenominationDto procedurenominationEntity);

      public abstract ProcedurenominationDto asDto(ProcedurenominationEntity procedurenominationEntity);
   public abstract ProcedurenominationExcelDTO asExcelDto(ProcedurenominationEntity entity);
   }
