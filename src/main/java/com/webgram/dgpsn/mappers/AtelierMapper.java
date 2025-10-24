package com.webgram.dgpsn.mappers;


import com.webgram.dgpsn.entities.AtelierEntity;
import com.webgram.dgpsn.models.AtelierDTO;
import com.webgram.dgpsn.services.modelExcel.AtelierExcelDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AtelierMapper extends EntityMapper<AtelierDTO, AtelierEntity> {


    AtelierEntity asEntity(AtelierDTO dto);


    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(AtelierDTO dto, @org.mapstruct.MappingTarget AtelierEntity entity);




    @Mapping(target = "titre", source = "titre")
    @Mapping(target = "theme", source = "theme")
    @Mapping(target = "objectif", source = "objectif")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "heurDebut", source = "heurDebut")
    @Mapping(target = "heurFin", source = "heurFin")
    @Mapping(target = "lieu", source = "lieu")
    @Mapping(target = "agent", source = "agent")
    @Mapping(target = "cout", source = "cout")
    AtelierExcelDTO asExcelDto(AtelierEntity entity);


}