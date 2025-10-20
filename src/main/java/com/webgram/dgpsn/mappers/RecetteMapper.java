package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.RecetteEntity;
import com.webgram.dgpsn.models.RecetteDto;
import com.webgram.dgpsn.entities.NaturedelarecetteEntity;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.RecetteExcelDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

   @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class RecetteMapper implements EntityMapper<RecetteDto, RecetteEntity> {

   @Mapping(target = "entreprise.id", source = "entrepriseId")
@Mapping(target = "naturedelarecette", source = "naturedelarecetteId", qualifiedByName = "mapNaturedelarecette")
   public abstract RecetteEntity asEntity(RecetteDto recetteEntity);

   @Named("mapNaturedelarecette")
   public NaturedelarecetteEntity mapNaturedelarecette(Long naturedelaRecetteId) {
       if (Objects.nonNull(naturedelaRecetteId)) {
           return NaturedelarecetteEntity.builder().id(naturedelaRecetteId).build();
       }
       return null;
    }
   public abstract RecetteDto asDto(RecetteEntity recetteEntity);
   public abstract RecetteExcelDTO asExcelDto(RecetteEntity entity);
   }
