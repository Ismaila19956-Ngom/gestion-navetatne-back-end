package com.webgram.dgpsn.mappers;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.models.FundingDTO;
import com.webgram.dgpsn.repositories.StructureProjectRepository;
import com.webgram.dgpsn.repositories.StructureRepository;
import com.webgram.dgpsn.services.modelExcel.FundingExcelDTO;

import java.text.MessageFormat;
import java.util.Objects;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class FundingMapper implements EntityMapper<FundingDTO, FundingEntity> {

//    @Autowired
//    private FundingTypeRepository fundingTypeRepository;
    @Autowired
    private StructureProjectRepository partnerProjetRepository;

    @Autowired
    private StructureRepository structureRepository;

    @Override
    @Mapping(target ="fundingType.id", source = "fundingTypeId" )
    @Mapping(target = "projet.id", source = "projetId" )
    @Mapping(target = "partnerProjet.id", source = "partnerProjetId" )
    @Mapping(target = "cashEntity", source = "cashId", qualifiedByName = "getCash")
    public abstract   FundingEntity asEntity(FundingDTO dto);


//    @Mapping(target = "fundingType", source = "codeFundingType", qualifiedByName = "getFundingTypeByCode")
//    @Mapping(target = "projet", source = "projectId", qualifiedByName = "getProjet")
//    @Mapping(target = "partnerProjet", source = "dto", qualifiedByName = "getPartnerProjetByCode")
//    @Mapping(target = "structure", source = "codeStructure", qualifiedByName = "getStructureByCode")
    public abstract  FundingEntity asEntity(FundingExcelDTO dto);

//    @Mapping(source ="fundingType.code", target = "codeFundingType" )
//    @Mapping(source = "projet.id", target = "projectId" )
//    @Mapping(source = "partnerProjet.structure.code", target = "partenerCode" )
//    @Mapping(source = "projet.libelle", target = "nomProjet")
//    @Mapping(source = "projet.sigle", target = "sigleProjet")
    public abstract FundingExcelDTO asExcelDto(FundingEntity entity);


//    @Named("getFundingTypeByCode")
//    public FundingTypeEntity getFunding(String fundingTypeCode) {
//        if(StringUtils.isNotEmpty(fundingTypeCode)){
//            var fundingType = fundingTypeRepository.findByCode(fundingTypeCode)
//                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format( "Ce code periode n'existe pas {}", fundingTypeCode)));
//            return fundingType;
//        }
//        return null;
//    }

    @Named("getPartnerProjetByCode")
    public StructureProjectEntity getPartnerProjet (FundingExcelDTO dto) {
        if(StringUtils.isNotEmpty(dto.getCodeStructure())){
            var PartnerProjet = partnerProjetRepository.findByProjetAndStructureCode(ManagementUnitEntity.builder().id(dto.getProjectId()).build(), dto.getCodeStructure())
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code partenaire n'existe pas pour ce projet", dto.getCodeStructure())));
            return PartnerProjet;

        }
        return null;
    }

    @Named("getStructureByCode")
    public StructureEntity getSructure(String structureCode) {
        if(StringUtils.isNotEmpty(structureCode)){
        var structure = structureRepository.findByCode(structureCode)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format( "Ce code periode n'existe pas {}", structureCode)));
        return structure;
        }
        return  null;
    }

    @Named("getProjet")
    public ManagementUnitEntity getProjet(Long projectId) {

        return ManagementUnitEntity.builder().id(projectId).build();
    }


    @Named("getCash")
    public LabelEntity getCashEntity(Long cashId) {
        if(Objects.nonNull(cashId)) {
            return LabelEntity.builder().id(cashId).build();
        }
        return null;
    }
}
