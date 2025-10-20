package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.EntrepriseDto;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.EntrepriseExcelDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

   @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class EntrepriseMapper implements EntityMapper<EntrepriseDto, EntrepriseEntity> {

   @Mapping(target = "secteuractivite", source = "secteuractiviteId", qualifiedByName = "mapSecteuractivite")
@Mapping(target = "formejuridique", source = "formejuridiqueId", qualifiedByName = "mapFormejuridique")
@Mapping(target = "region", source = "regionId", qualifiedByName = "mapRegion")
@Mapping(target = "departement", source = "departementId", qualifiedByName = "mapDepartement")
   public abstract EntrepriseEntity asEntity(EntrepriseDto entrepriseEntity);

   @Named("mapSecteuractivite")
   public LabelEntity mapSecteuractivite(Long secteurActiviteId) {
       if (Objects.nonNull(secteurActiviteId)) {
           return LabelEntity.builder().id(secteurActiviteId).build();
       }
       return null;
    }
@Named("mapFormejuridique")
   public LabelEntity mapFormejuridique(Long formeJuridiqueId) {
       if (Objects.nonNull(formeJuridiqueId)) {
           return LabelEntity.builder().id(formeJuridiqueId).build();
       }
       return null;
    }
@Named("mapRegion")
   public CadreLogiqueEntity mapRegion(Long regionId) {
       if (Objects.nonNull(regionId)) {
           return CadreLogiqueEntity.builder().id(regionId).build();
       }
       return null;
    }
@Named("mapDepartement")
   public CadreLogiqueEntity mapDepartement(Long departementId) {
       if (Objects.nonNull(departementId)) {
           return CadreLogiqueEntity.builder().id(departementId).build();
       }
       return null;
    }
   public abstract EntrepriseDto asDto(EntrepriseEntity entrepriseEntity);
   public abstract EntrepriseExcelDTO asExcelDto(EntrepriseEntity entity);
   }
