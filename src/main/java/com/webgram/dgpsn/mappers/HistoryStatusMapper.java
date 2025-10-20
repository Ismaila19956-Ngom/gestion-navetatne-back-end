package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.HistoryStatusDTO;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface HistoryStatusMapper extends EntityMapper<HistoryStatusDTO, HistoryStatusEntity> {

    @Override
    @Mapping(target = "status", source = "statusId", qualifiedByName = "getStatus")
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
    HistoryStatusEntity asEntity(HistoryStatusDTO dto);

    @Named("getStatus")
    default StatusEntity getStatus(Long statusId) {
        return StatusEntity.builder().id(statusId).build();
    }

    @Named("getProjet")
    default ManagementUnitEntity getProjet(Long projetId) {
        return ManagementUnitEntity.builder().id(projetId).build();
    }
}
