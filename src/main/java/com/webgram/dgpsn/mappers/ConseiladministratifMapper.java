package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.ConseiladministratifEntity;
import com.webgram.dgpsn.models.ConseiladministratifDto;
import com.webgram.dgpsn.entities.ProcedurenominationEntity;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.ConseiladministratifExcelDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

   @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class ConseiladministratifMapper implements EntityMapper<ConseiladministratifDto, ConseiladministratifEntity> {

   @Mapping(target = "entreprise.id", source = "entrepriseId")
@Mapping(target = "procedurenomination", source = "procedurenominationId", qualifiedByName = "mapProcedurenomination")
   public abstract ConseiladministratifEntity asEntity(ConseiladministratifDto conseiladministratifEntity);

   @Named("mapProcedurenomination")
   public ProcedurenominationEntity mapProcedurenomination(Long procedurenominationId) {
       if (Objects.nonNull(procedurenominationId)) {
           return ProcedurenominationEntity.builder().id(procedurenominationId).build();
       }
       return null;
    }
   public abstract ConseiladministratifDto asDto(ConseiladministratifEntity conseiladministratifEntity);
   public abstract ConseiladministratifExcelDTO asExcelDto(ConseiladministratifEntity entity);
   }
