package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.entities.PassationPlanEntity;
import com.webgram.dgpsn.entities.StructureEntity;
import com.webgram.dgpsn.models.PassationPlanDTO;

import java.util.Objects;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PassationPlanMapper extends EntityMapper<PassationPlanDTO, PassationPlanEntity> {




    @Mapping(target = "managementUnit", source = "managementUnitId", qualifiedByName = "getProjet")
    @Mapping(target = "structure", source = "structureId",qualifiedByName ="getStructure")
    PassationPlanEntity asEntity(PassationPlanDTO dto);



    @Mapping(source = "managementUnit.id", target = "managementUnitId")
    @Mapping(source = "structure.id", target = "structureId")
    PassationPlanDTO asDto( PassationPlanEntity passationPlanEntity);


    @Named("getStructure")
    default StructureEntity builtTutele(Long structureId) {
        if(Objects.nonNull(structureId)){
            return StructureEntity.builder().id(structureId).build();
        }
        return null;
    }


    @Named("getProjet")
    default ManagementUnitEntity getPartnerGroup(Long managementUnitId) {
        if(Objects.nonNull(managementUnitId)){
            return ManagementUnitEntity.builder().id(managementUnitId).build();
        }
        return null;
    }




}
