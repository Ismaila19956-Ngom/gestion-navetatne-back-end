package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.QueryDTO;
import com.webgram.dgpsn.repositories.ActorProjetRepository;
import com.webgram.dgpsn.repositories.StructureRepository;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class QueryMapper implements EntityMapper<QueryDTO, QueryEntity> {
    @Autowired
    private StructureRepository structureRepository;
    private ActorProjetRepository projetRepository;

    @Override
    @Mapping(target = "categorieRequete", source = "categorieRequeteId", qualifiedByName = "getCategorieRequete")
    @Mapping(target = "typeRequete", source = "typeRequeteId", qualifiedByName = "getTypeRequete")
    @Mapping(target = "demandeurActor", source = "demandeurActorId", qualifiedByName = "getDemandeurActor")
    @Mapping(target = "demandeurStructure", source = "demandeurStructureId", qualifiedByName = "getDemandeurStructure")
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
    @Mapping(target = "destinataireActor", source = "destinataireActorId", qualifiedByName = "getDestinataireActor")
    @Mapping(target = "destinataireStructure", source = "destinataireStructureId", qualifiedByName = "getDestinataireStructure")
    public abstract QueryEntity asEntity(QueryDTO dto);

    @Named("getCategorieRequete")
    public LabelEntity getCategorieRequete(Long categorieRequeteId) {
        if (Objects.nonNull(categorieRequeteId)){
        return LabelEntity.builder().build().builder().id(categorieRequeteId).build();
    }
         return null;
    }

    @Named("getTypeRequete")
    public TypeRequeteEntity getTypeRequete(Long typeRequeteId) {
//        if (Objects.nonNull(typeRequeteId)){
            return TypeRequeteEntity.builder().build().builder().id(typeRequeteId).build();
//       }
//       return  null;
}

   @Named("getDemandeurStructure")
    public StructureEntity getDemandeurStructure(Long demandeurStructureId) {

       if (Objects.nonNull(demandeurStructureId)) {
            return StructureEntity.builder().build().builder().id(demandeurStructureId).build();
       }
       return null;
           }

    @Named("getDemandeurActor")
    public ActorProjetEntity getDemandeurActor(Long demandeurActorId) {
        if(Objects.nonNull(demandeurActorId)){
        return ActorProjetEntity.builder().build().builder().id(demandeurActorId).build();
    }
        return null;
    }

    @Named("getDestinataireStructure")
    public StructureEntity getDestinataireStructure(Long destinataireStructureId) {
       if(Objects.nonNull(destinataireStructureId)){
        return StructureEntity.builder().build().builder().id(destinataireStructureId).build();
    }
       return null;
    }

    @Named("getDestinataireActor")
    public ActorProjetEntity getDestinataireActor(Long destinataireActorId) {
       if (Objects.nonNull(destinataireActorId)){
        return ActorProjetEntity.builder().build().builder().id(destinataireActorId).build();
    }
       return  null;
    }
    @Named("getProjet")
    public ManagementUnitEntity getProjet(Long projetId) {
        return ManagementUnitEntity.builder().id(projetId).build();
    }


//    @Mapping(target = "partener", source = "codePartenaire", qualifiedByName = "getStructureByCode")
//    @Mapping(target = "executive", source = "codeStructureExecution", qualifiedByName = "getStructureByCode")
//    public abstract QueryEntity asEntity(QueryExcelDTO dto);
//
//    @Named("getStructureByCode")
//    public StructureEntity getStructureByCode(String code) {
//        if(StringUtils.isNotEmpty(code)){
//            var structure = structureRepository.findByCode(code)
//                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code structure n'existe pas", code)));
//            return structure;
//        }
//        return null;
//    }
//
//    @Mapping(source = "partener.code", target = "codePartenaire")
//    @Mapping(source = "executive.code", target = "codeStructureExecution")
//    public abstract QueryExcelDTO asExcelDto(QueryEntity entity);
}
