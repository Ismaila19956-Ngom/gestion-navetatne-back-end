package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.HistoryFlagDTO;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface HistoryFlagMapper extends EntityMapper<HistoryFlagDTO, HistoryFlagEntity> {

    @Override
    @Mapping(target = "flag", source = "flagId", qualifiedByName = "getFlag")
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
    HistoryFlagEntity asEntity(HistoryFlagDTO dto);

    @Named("getFlag")
    default LabelEntity getFlag(Long flagId) {
        return LabelEntity.builder().id(flagId).build();
    }

    @Named("getProjet")
    default ManagementUnitEntity getProjet(Long projetId) {
        return ManagementUnitEntity.builder().id(projetId).build();
    }
}
