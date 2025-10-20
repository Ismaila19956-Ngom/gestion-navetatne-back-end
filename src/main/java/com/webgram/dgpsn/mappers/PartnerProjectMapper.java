package com.webgram.dgpsn.mappers;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.entities.StructureProjectEntity;
import com.webgram.dgpsn.entities.StructureEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.models.StructureProjectDTO;
import com.webgram.dgpsn.repositories.StructureRepository;
import com.webgram.dgpsn.services.modelExcel.PartnerProjectExcelDTO;

import java.text.MessageFormat;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class PartnerProjectMapper implements EntityMapper<StructureProjectDTO, StructureProjectEntity> {

    @Autowired
    private StructureRepository structureRepository;

    @Override
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
    @Mapping(target = "structure", source = "structureId", qualifiedByName = "getStructure")
    public abstract StructureProjectEntity asEntity(StructureProjectDTO dto);

    @Named("getProjet")
    public ManagementUnitEntity getProjet(Long projetId) {
        return ManagementUnitEntity.builder().id(projetId).build();
    }

    @Named("getStructure")
    public StructureEntity getStructure(Long structureId) {
        return StructureEntity.builder().id(structureId).build();
    }

    @Mapping(target = "structure", source = "codeStructure", qualifiedByName = "getStructureByCode")
    public abstract StructureProjectEntity asEntity(PartnerProjectExcelDTO dto);

    @Named("getStructureByCode")
    public StructureEntity getStructureByCode(String code) {
        if(StringUtils.isNotEmpty(code)){
            var structureExecution = structureRepository.findByCode(code)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code structureExecution n'existe pas", code)));
            return structureExecution;
        }
        return null;
    }

    @Mapping(target = "codeStructure", source = "structure.code")
    public abstract PartnerProjectExcelDTO asExcelDto(StructureProjectEntity entity);
}
