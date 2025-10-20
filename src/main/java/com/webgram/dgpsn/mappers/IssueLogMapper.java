package com.webgram.dgpsn.mappers;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.models.IssueLogDTO;
import com.webgram.dgpsn.repositories.*;
import com.webgram.dgpsn.services.modelExcel.IssueLogExcelDTO;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class IssueLogMapper implements EntityMapper<IssueLogDTO, IssueLogEntity> {

    @Autowired
    private LabelRepository labelRepository;
//    @Autowired
//    private DelayImpactRepository delayImpactRepository;
//    @Autowired
//    private FinancialImpactRepository financialImpactRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private SpecificNatureRepository specificNatureRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private StructureRepository structureRepository;
    @Autowired
    private RiskRepository riskRepository;
    @Autowired
    private ManagementUnitRepository managementUnitRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;

    @Override
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
//    @Mapping(target = "criticity", source = "criticityCode", qualifiedByName = "getCriticityByCode")
//    @Mapping(target = "delayImpact", source = "delayImpactId", qualifiedByName = "getDelayImpact")
//    @Mapping(target = "financialImpact", source = "financialImpactId", qualifiedByName = "getFinancialImpact")
    @Mapping(target = "assignment", source = "assignmentId", qualifiedByName = "getAssignment")
    @Mapping(target = "status", source = "statusId", qualifiedByName = "getStatus")
    @Mapping(target = "specificNature", source = "specificNatureId", qualifiedByName = "getSpecificNature")
    @Mapping(target = "subCategory", source = "subCategoryId", qualifiedByName = "getSubCategory")
    @Mapping(target = "supervisor", source = "supervisorId", qualifiedByName = "getSupervisor")
    @Mapping(target = "dseppSupervisor", source = "dseppSupervisorId", qualifiedByName = "getDseppSupervisor")
    @Mapping(target = "source", source = "sourceId", qualifiedByName = "getSource")
    @Mapping(target = "minister", source = "ministerId", qualifiedByName = "getMinister")
    @Mapping(target = "resolveChannels", source = "resolveChannelsIds", qualifiedByName = "getResolveChannels")
    @Mapping(target = "risks", source = "risksIds", qualifiedByName = "getRisks")
    @Mapping(target = "structures", source = "structuresIds", qualifiedByName = "getStructures")
    public abstract IssueLogEntity asEntity(IssueLogDTO dto);

    @Named("getDelayImpact")
    public LabelEntity getDelayImpact(Long delayImpactId) {
        if(Objects.nonNull(delayImpactId)) {
            return LabelEntity.builder().id(delayImpactId).build();
        }
        return null;
    }
    @Named("getFinancialImpact")
    public LabelEntity getFinancialImpact(Long financialImpactId) {
        if(Objects.nonNull(financialImpactId)) {
            return LabelEntity.builder().id(financialImpactId).build();
        }
        return null;
    }
    @Named("getStatus")
    public StatusEntity getStatus(Long statusId) {
        if(Objects.nonNull(statusId)) {
            return StatusEntity.builder().id(statusId).build();
        }
        return null;
    }
    @Named("getSpecificNature")
    public SpecificNatureEntity getSpecificNature(Long specificNatureId) {
        if(Objects.nonNull(specificNatureId)) {
            return SpecificNatureEntity.builder().id(specificNatureId).build();
        }
        return null;
    }
    @Named("getSubCategory")
    public SubCategoryEntity getSubCategory(Long subCategoryId) {
        if(Objects.nonNull(subCategoryId)) {
            return SubCategoryEntity.builder().id(subCategoryId).build();
        }
        return null;
    }
    @Named("getSupervisor")
    public StructureEntity getSupervisor(Long supervisorId) {
        if(Objects.nonNull(supervisorId)) {
            return StructureEntity.builder().id(supervisorId).build();
        }
        return null;
    }
    @Named("getDseppSupervisor")
    public AgentEntity getDseppSupervisor(Long dseppSupervisorId) {
        if(Objects.nonNull(dseppSupervisorId)) {
            return AgentEntity.builder().id(dseppSupervisorId).build();
        }
        return null;
    }
    @Named("getSource")
    public LabelEntity getSource(Long sourceId) {
        if(Objects.nonNull(sourceId)) {
            return LabelEntity.builder().id(sourceId).build();
        }
        return null;
    }
    @Named("getMinister")
    public StructureEntity getMinister(Long ministerId) {
        if(Objects.nonNull(ministerId)) {
            return StructureEntity.builder().id(ministerId).build();
        }
        return null;
    }

    @Named("getCriticityByCode")
    public LabelEntity getCriticityByCode(String code) {
        if(Objects.nonNull(code)) {
            return labelRepository.findByCode(code).orElseThrow(() -> new ResourceNotFoundException("Code crticity "+code+"introuvable"));
        } else {
            return null;
        }
    }

    @Named("getResolveChannels")
    public Set<LabelEntity> getResolveChannels(List<Long> resolveChannelIds) {
        return labelRepository.findAllByIds(resolveChannelIds);
    }

    @Named("getRisks")
    public Set<RiskEntity> getRisks(List<Long> risksIds) {
        return riskRepository.findAllByIds(risksIds);
    }

    @Named("getStructures")
    public Set<StructureEntity> getStructures(List<Long> structuresIds) {
        return structureRepository.findAllByIds(structuresIds);
    }

    @Named("getProjet")
    public ManagementUnitEntity getProjet(Long projectId) {
        if(Objects.nonNull(projectId)) {
            return managementUnitRepository.findById(projectId).orElseThrow();
        }else {
            return null;
        }
    }
    @Named("getAssignment")
    public AssignmentEntity getAssignment(Long assignmentId) {
        if(Objects.nonNull(assignmentId)) {
            return assignmentRepository.findById(assignmentId).orElseThrow();
        }else {
            return null;
        }
    }

    @Mapping(target = "projet", source = "projectId", qualifiedByName = "getProject")
//    @Mapping(target = "criticity", source = "criticityCode", qualifiedByName = "getCriticity")
//    @Mapping(target = "delayImpact", source = "delayImpactCode", qualifiedByName = "getDelayImpact")
//    @Mapping(target = "financialImpact", source = "financialImpactCode", qualifiedByName = "getFinancialImpact")
    @Mapping(target = "status", source = "statusCode", qualifiedByName = "getStatus")
    @Mapping(target = "specificNature", source = "specificNatureCode", qualifiedByName = "getNatureSpecific")
    @Mapping(target = "subCategory", source = "subCategoryCode", qualifiedByName = "getSubCategory")
    @Mapping(target = "supervisor", source = "supervisorCode", qualifiedByName = "getStructure")
    @Mapping(target = "dseppSupervisor", source = "dseppSupervisorCode", qualifiedByName = "getDseppSupervisor")
    @Mapping(target = "source", source = "sourceCode", qualifiedByName = "getSource")
    @Mapping(target = "minister", source = "ministerCode", qualifiedByName = "getStructure")
    public abstract IssueLogEntity asEntity(IssueLogExcelDTO dto);

//    @Mapping(source = "criticity.code", target= "criticityCode")
//    @Mapping(source = "delayImpact.code", target = "delayImpactCode")
//    @Mapping(source = "financialImpact.code", target = "financialImpactCode")
    @Mapping(source = "status.code", target = "statusCode")
    @Mapping(source = "specificNature.code", target = "specificNatureCode")
    @Mapping(source = "subCategory.code", target = "subCategoryCode")
    @Mapping(source = "supervisor.code", target = "supervisorCode")
    @Mapping(source = "dseppSupervisor.matricule", target = "dseppSupervisorCode")
    @Mapping(source = "source.code", target = "sourceCode")
    @Mapping(source = "minister.code", target = "ministerCode")
    public abstract IssueLogExcelDTO asExcelDto(IssueLogEntity entity);

    @Named("getProject")
    public ManagementUnitEntity getProject(Long projectId) {
        if(Objects.nonNull(projectId)) {
            return managementUnitRepository.findById(projectId).orElseThrow();
        }else {
            return null;
        }
    }

    @Named("getCriticity")
    public LabelEntity getCriticity(String criticityCode) {

        if(StringUtils.isNotEmpty(criticityCode)){
            return labelRepository.findByCode(criticityCode)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code criticité n'existe pas {}", criticityCode)));
        }
        return null;
    }

//    @Named("getDelayImpact")
//    public DelayImpactEntity getDelayImpact(String delayImpactCode) {
//        if(StringUtils.isNotEmpty(delayImpactCode)){
//            var delayimpact = delayImpactRepository.findByCode(delayImpactCode)
//                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code delai n'existe pas {}", delayImpactCode)));
//            return delayimpact;
//
//        }
//        return null;
//    }

//    @Named("getFinancialImpact")
//    public FinancialImpactEntity getFinancialImpact(String financialImpactCode) {
//
//        if(StringUtils.isNotEmpty(financialImpactCode)){
//            var financialImpact = financialImpactRepository.findByCode(financialImpactCode)
//                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code d'impact financier n'existe pas {}", financialImpactCode)));
//            return financialImpact;
//
//        }
//        return null;
//    }

    @Named("getStatus")
    public StatusEntity getStatus(String statusCode) {
        if(StringUtils.isNotEmpty(statusCode)){
            var status = statusRepository.findByCode(statusCode)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce statut n'existe pas {}", statusCode)));
            return status;

        }
        return null;
    }

    @Named("getNatureSpecific")
    public SpecificNatureEntity getNatureSpecific(String natureCodeSpecific) {
        if(StringUtils.isNotEmpty(natureCodeSpecific)){
            var specificNature = specificNatureRepository.findByCode(natureCodeSpecific)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Cette nature specifc n'existe pas {}", natureCodeSpecific)));
            return specificNature;
        }
        return null;
    }

    @Named("getSubCategory")
    public SubCategoryEntity getSubCategory(String code) {
        if(StringUtils.isNotEmpty(code)){
            var subCategory = subCategoryRepository.findByCode(code)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Cette nature specifc n'existe pas {}", code)));
            return subCategory;
        }
        return null;
    }

    @Named("getStructure")
    public StructureEntity getStructure(String code) {
        if(StringUtils.isNotEmpty(code)){
            var structure = structureRepository.findByCode(code)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code n'existe pas {}", code)));
            return structure;
        }
        return null;
    }

    @Named("getDseppSupervisor")
    public AgentEntity getDseppSupervisor(String matricule) {
        if(StringUtils.isNotEmpty(matricule)){
            var agent = agentRepository.findByMatricule(matricule)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce matricule n'existe pas {}", matricule)));
            return agent;
        }
        return null;
    }

    @Named("getSource")
    public LabelEntity getSource(String code) {
        if(StringUtils.isNotEmpty(code)){
            return labelRepository.findByCode(code)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code n'existe pas {}", code)));
        }
        return null;
    }
}
