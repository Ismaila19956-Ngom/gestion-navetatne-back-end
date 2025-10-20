package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.ParticipantagEntity;
import com.webgram.dgpsn.models.ParticipantagDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.ParticipantagExcelDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class ParticipantagMapper implements EntityMapper<ParticipantagDto, ParticipantagEntity> {

   @Mapping(target = "assemblegeneral.id", source = "assemblegeneralId")
   public abstract ParticipantagEntity asEntity(ParticipantagDto participantagEntity);

      public abstract ParticipantagDto asDto(ParticipantagEntity participantagEntity);
   public abstract ParticipantagExcelDTO asExcelDto(ParticipantagEntity entity);
   }
