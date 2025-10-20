package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.SecteuractiviteEntity;
import com.webgram.dgpsn.models.SecteuractiviteDto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.SecteuractiviteExcelDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class SecteuractiviteMapper implements EntityMapper<SecteuractiviteDto, SecteuractiviteEntity> {

      public abstract SecteuractiviteEntity asEntity(SecteuractiviteDto secteuractiviteEntity);

      public abstract SecteuractiviteDto asDto(SecteuractiviteEntity secteuractiviteEntity);
   public abstract SecteuractiviteExcelDTO asExcelDto(SecteuractiviteEntity entity);
   }
