package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.ImpactsAndObjectiveEntity;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.models.ImpactsAndObjectiveDTO;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ImpactsAndObjectiveMapper extends EntityMapper<ImpactsAndObjectiveDTO, ImpactsAndObjectiveEntity> {

    @Override
    @Mapping(target = "managementUnit", source = "managementUnitId", qualifiedByName = "getManagementUnit")
    ImpactsAndObjectiveEntity asEntity(ImpactsAndObjectiveDTO dto);

    @Named("getManagementUnit")
    default ManagementUnitEntity getManagementUnit(Long managementUnitId) {
        return ManagementUnitEntity.builder().id(managementUnitId).build();
    }

}
