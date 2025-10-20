package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.models.AssignmentDTO;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.repositories.StructureRepository;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class AssignmentMapper implements EntityMapper<AssignmentDTO, AssignmentEntity> {

//    @Autowired
//    private AssignmentTypeRepository assignmentTypeRepository;

    @Autowired
    private StructureRepository structureRepository;

    @Override
    @Mapping(target = "assignmentType", source = "assignmentTypeId", qualifiedByName = "getAssignmentType")
//    @Mapping(target = "partener", source = "partenerId", qualifiedByName = "getPartener")
    @Mapping(target = "structure", source = "structureId", qualifiedByName = "getStructure")
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
    public abstract AssignmentEntity asEntity(AssignmentDTO assignmentDTO);

//    @Named("getAssignmentType")
//    public LabelEntity getAssignmentType(Long assignmentTypeId) {
//        if(Objects.nonNull(assignmentTypeId)) {
//            return LabelEntity.builder().id(assignmentTypeId).build();
//        }
//        return null;
//    }
//
//    @Named("getPartener")
//    public StructureEntity getPartener(Long partenerId) {
//
//       if(Objects.nonNull(partenerId)) {
//           return StructureEntity.builder().id(partenerId).build();
//       }
//       return null;
//    }
   @Named("getAssignmentType")
  public LabelEntity getAssignmentType(Long assignmentTypeId) {
    return LabelEntity.builder().build().builder().id(assignmentTypeId).build();
}
    @Named("getStructure")
    public StructureEntity getStructure(Long structureId) {
        return StructureEntity.builder().id(structureId).build();
    }

    @Named("getProjet")
    public ManagementUnitEntity getProjet(Long projetId) {
        return ManagementUnitEntity.builder().id(projetId).build();
    }

//    @Mapping(target = "assignmentType", source = "codeAssignmentType", qualifiedByName = "getAssignmentType")
//    @Mapping(target = "partener", source = "codePartenaire", qualifiedByName = "getPartenaire")
//    @Mapping(target = "structureExecution", source = "codeStructureExecution", qualifiedByName = "getStructureExecution")
////    public abstract AssignmentEntity asEntity(AssignmentExcelDTO dto);

//    @Named("getAssignmentType")
//    public AssignmentTypeEntity getAssignmentType(String code) {
//        if(StringUtils.isNotEmpty(code)){
//            var assignmentTypeEntity = assignmentTypeRepository.findByCode(code)
//                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code typeAssignemnt n'existe pas", code)));
//            return assignmentTypeEntity;
//        }
//        return null;
//    }

//    @Named("getPartenaire")
//    public StructureEntity getPartenaire(String code) {
//        if(StringUtils.isNotEmpty(code)){
//            var partener = structureRepository.findByCode(code)
//                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code partnaire n'existe pas", code)));
//            return partener;
//        }
//        return null;
//    }
//
//    @Named("getStructure")
//    public StructureEntity getStructureExecution(String code) {
//        if(StringUtils.isNotEmpty(code)){
//            var structure = structureRepository.findByCode(code)
//                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code structureExecution n'existe pas", code)));
//            return structure;
//        }
//        return null;P
//    }
//
//    @Mapping(source = "assignmentType.code", target = "codeAssignmentType")
//    @Mapping(source = "partener.code", target = "codePartenaire")
//    @Mapping(source = "structureExecution.code", target = "codeStructureExecution")
//    public abstract AssignmentExcelDTO asExcelDto(AssignmentEntity entity);
}
