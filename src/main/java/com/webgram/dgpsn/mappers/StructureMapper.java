package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.enums.TypeStructure;
import com.webgram.dgpsn.models.StructureDTO;
import com.webgram.dgpsn.entities.StructureEntity;
import com.webgram.dgpsn.services.modelExcel.StructureExcelDTO;

import java.util.List;
import java.util.Objects;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface StructureMapper extends EntityMapper<StructureDTO, StructureEntity> {

    @Mapping(target = "etat", source = "etat", qualifiedByName = "mapEtat")
    @Mapping(target = "typeStructure", source = "typeStructure", qualifiedByName = "mapByTypeStructure")
    StructureEntity asEntity(StructureExcelDTO dto);



    @Mapping(target = "etat", source = "etat", qualifiedByName = "mapEtat")
    @Mapping(target = "typeStructure", source = "typeStructure", qualifiedByName = "mapByTypeStructure")
    @Mapping(target = "country", source = "countryId", qualifiedByName = "getCountry")
    @Mapping(target = "partnerGroup", source = "partnerGroupId", qualifiedByName = "getPartnerGroup")
    @Mapping(target = "tutelle", source = "tutelleId",qualifiedByName ="tutelleConstruct")
    StructureEntity asEntity(StructureDTO dto);

//    @Mapping(target = "tutelleId",source ="tutelle.id")
    @Mapping(target = "partnerGroupId",source ="partnerGroup.id")
    @Mapping(target = "countryId",source ="country.id")
    @Mapping(source = "tutelle.id", target = "tutelleId")
    StructureDTO asDto( StructureEntity structureEntity);

    @Named("mapEtat")
    default boolean mapEtat(String etat) {
        if(etat.equalsIgnoreCase("VRAI")) {
            return true;
        } else if(etat.equalsIgnoreCase("FAUX")) {
            return false;
        }
        return false;
    }
    @Named("getStructure")
    default StructureEntity getStructure(Long tutelleId) {
        if(Objects.nonNull(tutelleId)){
            return StructureEntity.builder().id(tutelleId).build();
        }
        return null;
    }
    @Named("tutelleConstruct")
    default StructureEntity builtTutele(Long tutelleId) {
        if(Objects.nonNull(tutelleId)){
            return StructureEntity.builder().id(tutelleId).build();
        }
        return null;
    }

    @Named("mapByTypeStructure")
    default TypeStructure mapByTypeStructure(String typeStructure) {
       return TypeStructure.valueOf(typeStructure);
    }

    @Named("getPartnerGroup")
    default LabelEntity getPartnerGroup(Long partnerGroupId) {
        if(Objects.nonNull(partnerGroupId)){
            return LabelEntity.builder().id(partnerGroupId).build();
        }
        return null;
    }

    @Named("getCountry")
    default LabelEntity getCountry(Long countryId) {
        if(Objects.nonNull(countryId)){
            return LabelEntity.builder().id(countryId).build();
        }
        return null;
    }

    @Mapping(target = "etat", source = "etat", qualifiedByName = "mapEtatToString")
    @Mapping(target = "typeStructure", source = "typeStructure", qualifiedByName = "mapTypeStructureToString")
    StructureExcelDTO asExcelDto(StructureEntity entity);

    @Named("mapEtatToString")
    default String mapEtatToString(boolean etat) {
        if(etat) {
            return "VRAI";
        } else if (!etat) {
            return "FAUX";
        }
        return "FAUX";
    }

    @Named("mapTypeStructureToString")
    default String mapByTypeStructure(TypeStructure typeStructure) {
        return typeStructure.name();

    }

}
