package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.MilestoneEntity;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.models.MilestoneDTO;
import com.webgram.dgpsn.repositories.MilestoneRepository;
import com.webgram.dgpsn.services.modelExcel.MilestoneExcelDTO;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class MilestoneMapper implements EntityMapper<MilestoneDTO, MilestoneEntity> {

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Override
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
    public abstract MilestoneEntity asEntity(MilestoneDTO dto);

    @Named("getProjet")
    public ManagementUnitEntity getProjet(Long projetId) {
        return ManagementUnitEntity.builder().id(projetId).build();
    }

    public abstract MilestoneEntity asEntity(MilestoneExcelDTO dto);

    public abstract MilestoneExcelDTO asExcelDto(MilestoneEntity entity);
}
