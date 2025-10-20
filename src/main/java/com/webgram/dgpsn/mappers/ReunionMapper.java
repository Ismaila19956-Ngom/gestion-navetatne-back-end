package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.ReunionEntity;
import com.webgram.dgpsn.models.ReunionDto;
import com.webgram.dgpsn.entities.TypereunionEntity;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.ReunionExcelDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

   @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class ReunionMapper implements EntityMapper<ReunionDto, ReunionEntity> {

   @Mapping(target = "conseiladministratif.id", source = "conseiladministratifId")
@Mapping(target = "typereunion", source = "typereunionId", qualifiedByName = "mapTypereunion")
   public abstract ReunionEntity asEntity(ReunionDto reunionEntity);

   @Named("mapTypereunion")
   public TypereunionEntity mapTypereunion(Long typereunionId) {
       if (Objects.nonNull(typereunionId)) {
           return TypereunionEntity.builder().id(typereunionId).build();
       }
       return null;
    }
   public abstract ReunionDto asDto(ReunionEntity reunionEntity);
   public abstract ReunionExcelDTO asExcelDto(ReunionEntity entity);
   }
