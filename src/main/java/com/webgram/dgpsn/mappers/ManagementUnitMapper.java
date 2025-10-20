package com.webgram.dgpsn.mappers;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.entities.enums.StructureProjectType;
import com.webgram.dgpsn.entities.enums.TypeProjet;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.models.ManagementUnitDTO;
import com.webgram.dgpsn.models.responses.ManagementUnitTreeDTO;
import com.webgram.dgpsn.models.responses.MissingElementDTO;
import com.webgram.dgpsn.repositories.*;
import com.webgram.dgpsn.services.modelExcel.ManagementUnitExcelDTO;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class ManagementUnitMapper implements EntityMapper<ManagementUnitDTO, ManagementUnitEntity> {

    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private StructureRepository structureRepository;

    @Autowired
    private SubSectorRepository subSectorRepository;

    @Autowired
    private LabelRepository flagRepository;

    @Autowired
    private StructureProjectRepository structureProjectRepository;

    @Autowired
    private MilestoneRepository jallonRepository;

    @Autowired
    private UgpProjetRepository ugpProjetRepository;

    @Autowired
    private GeographicalLocationRepository zoneInterventionRepository;

    @Autowired
    private IndicatorProjetRepository indicatorRepository;

    @Autowired
    private ActorProjetRepository acteurRepository;

    @Autowired
    private BudgetRepository budgetRepository; // Ajout de l'injection
    @Override

    @Mapping(target = "axe", source = "axeId", qualifiedByName = "getAxe")
    @Mapping(target = "responsible", source = "responsibleId", qualifiedByName = "getResponsible")
    @Mapping(target = "parent", source = "parentId", qualifiedByName = "getParent")
    @Mapping(target = "subSectors", source = "subSectorIds", qualifiedByName = "mapSubSector")
    @Mapping(target = "executionZones", source = "executionZoneIds", qualifiedByName = "mapExecutionZone")
    @Mapping(target = "beneficiaries", source = "beneficiarieIds", qualifiedByName = "mapBeneficiary")
    public abstract ManagementUnitEntity asEntity(ManagementUnitDTO dto);

//    @Mapping(target = "minister", source = "codeMinister", qualifiedByName = "getStructureByCode")
//    @Mapping(target = "status", source = "codeStatus", qualifiedByName = "getStatusByCode")
//    @Mapping(target = "structure", source = "codeStructure", qualifiedByName = "getStructureByCode")
//    @Mapping(target = "subSector", source = "codeSubSector", qualifiedByName = "getSubSectorByCode")
//    @Mapping(target = "axePSE", source = "codeAxePSE", qualifiedByName = "getAxePSEByCode")
//    @Mapping(target = "flag", source = "codeFlag", qualifiedByName = "getFlagByCode")
//    @Mapping(target = "type", source = "stringType", qualifiedByName = "getTypeProject")
//    @Mapping(target = "periodicity", source = "codePeriodicity", qualifiedByName = "getPeriodicityByCode")
//    @Mapping(target = "meetingFrequency", source = "codeMeetingFrequency", qualifiedByName = "getPeriodicityByCode")
    public abstract ManagementUnitEntity asEntity(ManagementUnitExcelDTO dto);

    @Mapping(source = "entity", target = "missingElements", qualifiedByName = "mapMissingElements")
    @Mapping(source = "entity", target = "statut", qualifiedByName = "mapStatut")
    @Mapping(target = "budgetInsvest", source = "id", qualifiedByName = "mapBudgetFromActualAmount") // Ajout du mappage personnalisé
    public abstract ManagementUnitDTO asDto(ManagementUnitEntity entity);

//    @Mapping(source = "minister.code", target = "codeMinister")
//    @Mapping(source = "status.code", target = "codeStatus")
//    @Mapping(source = "structure.code", target = "codeStructure")
//    @Mapping(source = "subSector.code", target = "codeSubSector")
//    @Mapping(source = "axePSE.code", target = "codeAxePSE")
//    @Mapping(source = "flag.code", target = "codeFlag")
//    @Mapping(source = "type", target = "stringType")
//    @Mapping(source = "equivalence", target = "equivalence", qualifiedByName = "toMilliard")
//    @Mapping(source = "periodicity.code", target = "codePeriodicity")
//    @Mapping(source = "meetingFrequency.code", target = "codeMeetingFrequency")
//    @Mapping(source = "statut", target = "statut", qualifiedByName = "getStatusManagement")
    public abstract ManagementUnitExcelDTO asExcelDto(ManagementUnitEntity entity);

//    @Named("getStatusManagement")
//    public StatusEntity getStatusManagement(ManagementUnitEntity entity) {
//        if(!Objects.isNull(entity.getId())){
//            var status = statusRepository.findBy(entity.getId())
//                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code statut {} n'existe pas", codeStatus)));
//            return status;
//
//        }
//        return null;
//    }

    @Named("toMilliard")
    public Double toMilliard(Double equivalence) {
        if(Objects.nonNull(equivalence)) {
            return equivalence/1000000000;
        }
        return Double.valueOf(0);
    }

    @Named("mapBudgetFromActualAmount")
    protected Double mapBudgetFromActualAmount(Long managementUnitId) {
        return budgetRepository.sumActualAmountByManagementUnitId(managementUnitId);
    }

    @Named("getStatusByCode")
    public StatusEntity getStatusByCode(String codeStatus) {
        if(StringUtils.isNotEmpty(codeStatus)){
            var status = statusRepository.findByCode(codeStatus)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code statut {} n'existe pas", codeStatus)));
            return status;

        }
        return null;
    }

    @Named("getStructureByCode")
    public StructureEntity getStructureByCode(String codeStructure) {
        if(StringUtils.isNotEmpty(codeStructure)){
            var structure = structureRepository.findByCode(codeStructure)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code structure {} n'existe pas", codeStructure)));
            return structure;
        }
        return null;
    }

    @Named("getSubSectorByCode")
    public SubSectorEntity getSubSectorByCode(String codeSubSector) {
        if(StringUtils.isNotEmpty(codeSubSector)){
            var subSector = subSectorRepository.findByCode(codeSubSector)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code sous secteur {} n'existe pas", codeSubSector)));
            return subSector;
        }
        return null;
    }

//    @Named("getAxePSEByCode")
//    public AxePSEEntity getAxePSEByCode(String codeAxePSE) {
//        if(StringUtils.isNotEmpty(codeAxePSE)){
//            var axePSE = axePSERepository.findByCode(codeAxePSE)
//                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code axePSE {} n'existe pas", codeAxePSE)));
//            return axePSE;
//        }
//        return null;
//    }

//    @Named("getFlagByCode")
//    public FlagEntity getFlagByCode(String codeFlag) {
//        if(StringUtils.isNotEmpty(codeFlag)){
//            var flag = flagRepository.findByCode(codeFlag)
//                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code flag n'existe pas", codeFlag)));
//            return flag;
//        }
//        return null;
//    }

    @Named("getTypeProject")
    public TypeProjet getTypeProject(String stringType) {
        if(StringUtils.isNotEmpty(stringType)){
            return TypeProjet.valueOf(stringType);
        }
        return null;
    }


//    @Named("getPeriodicityByCode")
//    public PeriodicityEntity getPeriodicityByCode(String codePeriodicity) {
//        if(StringUtils.isNotEmpty(codePeriodicity)){
//            var periodicity = periodicityRepository.findByCode(codePeriodicity)
//                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code periodicite {} n'existe pas", codePeriodicity)));
//            return periodicity;
//        }
//        return null;
//    }

    @Named("getStatus")
    public StatusEntity getStatus(Long statusId) {
        if(Objects.nonNull(statusId)){
            return StatusEntity.builder().id(statusId).build();
        }
        return null;
    }

    @Named("getStructure")
    public StructureEntity getStructure(Long structureId) {
        if(Objects.nonNull(structureId)){
            return StructureEntity.builder().id(structureId).build();
        }
        return null;
    }
    @Named("getSubSector")
    public SubSectorEntity getSubSector(Long subSectorId) {
        if(Objects.nonNull(subSectorId)){
            return SubSectorEntity.builder().id(subSectorId).build();
        }
        return null;
    }

    @Named("getAxe")
    public LabelEntity getAxePSE(Long axeId) {
        if(Objects.nonNull(axeId)){
            return LabelEntity.builder().id(axeId).build();
        }
        return null;
    }
    @Named("getParent")
    public ManagementUnitEntity getParent(Long parentId) {
        if(Objects.nonNull(parentId)){
            return ManagementUnitEntity.builder().id(parentId).build();
        }
        return null;
    }

    @Named("getResponsible")
    public AgentEntity getResponsible(Long responsibleId) {
        if(Objects.nonNull(responsibleId)) {
            return AgentEntity.builder().id(responsibleId).build();
        }
        return null;
    }

    @Named("mapSubSector")
    public List<SubSectorEntity> mapSubsector(List<Long> subSectorIds ) {
        List<SubSectorEntity> subSectors = new ArrayList<>();
        if(Objects.nonNull(subSectorIds)) {
            for(Long subsectorId: subSectorIds) {
                subSectors.add(SubSectorEntity.builder().id(subsectorId).build());
            }
            return subSectors;
        }
        return null;
    }
    @Named("mapExecutionZone")
    public List<LabelEntity> mapExecutionZone(List<Long> executionZoneIds ) {
        List<LabelEntity> executionZones = new ArrayList<>();
        if(Objects.nonNull(executionZoneIds)) {
            for(Long labelId: executionZoneIds) {
                executionZones.add(LabelEntity.builder().id(labelId).build());
            }
            return executionZones;
        }
        return null;
    }
    @Named("mapBeneficiary")
    public List<LabelEntity> mapBeneficiary(List<Long> beneficiarieIds ) {
        List<LabelEntity> beneficiaries = new ArrayList<>();
        if(Objects.nonNull(beneficiarieIds)) {
            for(Long labelId: beneficiarieIds) {
                beneficiaries.add(LabelEntity.builder().id(labelId).build());
            }
            return beneficiaries;
        }
        return null;
    }

    // Custom methods with @Named for retrieving missing elements
    @Named("mapMissingElements")
    public List<MissingElementDTO> mapMissingElements(ManagementUnitEntity entity) {
        List<MissingElementDTO> missingElements = new ArrayList<>();

        // Check each element and add to the missing list if absent
        List<MilestoneEntity> jalons = jallonRepository.findByProjetId(entity.getId());
        if (jalons.isEmpty()) {
            missingElements.add(new MissingElementDTO("Jalons", 0));
        } else {
            missingElements.add(new MissingElementDTO("Jalons", jalons.size()));
        }

        List<ActorProjetEntity> acteurs = acteurRepository.findByProjetId(entity.getId());
        if (acteurs.isEmpty()) {
            missingElements.add(new MissingElementDTO("Acteurs", 0));
        } else {
            missingElements.add(new MissingElementDTO("Acteurs", acteurs.size()));
        }

        List<GeographicalLocationEntity> zonesIntervention = zoneInterventionRepository.findByProjetId(entity.getId());
        if (zonesIntervention.isEmpty()) {
            missingElements.add(new MissingElementDTO("Zone d'intervention", 0));
        } else {
            missingElements.add(new MissingElementDTO("Zone d'intervention", zonesIntervention.size()));
        }

        List<UgpProjetEntity> ugps = ugpProjetRepository.findByProjetId(entity.getId());
        if (ugps.isEmpty()) {
            missingElements.add(new MissingElementDTO("UGP", 0));
        } else {
            missingElements.add(new MissingElementDTO("UGP", ugps.size()));
        }

        List<IndicatorProjetEntity> indicators = indicatorRepository.findByProjetId(entity.getId());
        if (indicators.isEmpty()) {
            missingElements.add(new MissingElementDTO("Indicateurs", 0));
        } else {
            missingElements.add(new MissingElementDTO("Indicateurs", indicators.size()));
        }

        List<StructureProjectEntity> executionStructures = structureProjectRepository.findByProjetIdAndStructureProjectType(entity.getId(), StructureProjectType.EXECUTION);
        if (executionStructures.isEmpty()) {
            missingElements.add(new MissingElementDTO("Structures d'exécution", 0));
        } else {
            missingElements.add(new MissingElementDTO("Structures d'exécution", executionStructures.size()));
        }

        List<StructureProjectEntity> tutelleStructures = structureProjectRepository.findByProjetIdAndStructureProjectType(entity.getId(), StructureProjectType.GUARDIANSHIP);
        if (tutelleStructures.isEmpty()) {
            missingElements.add(new MissingElementDTO("Structures de tutelle", 0));
        } else {
            missingElements.add(new MissingElementDTO("Structures de tutelle", tutelleStructures.size()));
        }

        List<StructureProjectEntity> partnerStructures = structureProjectRepository.findByProjetIdAndStructureProjectType(entity.getId(), StructureProjectType.PARTNER);
        if (partnerStructures.isEmpty()) {
            missingElements.add(new MissingElementDTO("Partenaire", 0));
        } else {
            missingElements.add(new MissingElementDTO("Partenaire", partnerStructures.size()));
        }

        return missingElements;
    }

    @Named("mapStatut")
    public String mapStatut(ManagementUnitEntity entity) {
        List<MissingElementDTO> missingElements = mapMissingElements(entity);

        for (MissingElementDTO element : missingElements) {
            if (element.getNombre() == 0) {
                return "red";
            }
        }

        return "green";
    }

    @Mapping(target = "children", ignore = true)
    public  abstract ManagementUnitTreeDTO asTreeDto(ManagementUnitEntity entity);

}
