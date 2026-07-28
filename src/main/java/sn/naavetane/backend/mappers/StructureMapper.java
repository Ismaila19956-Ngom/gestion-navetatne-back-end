package sn.naavetane.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import sn.naavetane.backend.entities.LabelEntity;
import sn.naavetane.backend.entities.enums.TypeStructure;
import sn.naavetane.backend.models.StructureDTO;
import sn.naavetane.backend.entities.StructureEntity;

import java.util.List;
import java.util.Objects;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface StructureMapper extends EntityMapper<StructureDTO, StructureEntity> {

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
}
