package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.InstructionEntity;
import com.webgram.dgpsn.entities.TdrEntity;
import com.webgram.dgpsn.models.TdrDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class TdrMapper implements EntityMapper<TdrDTO, TdrEntity> {

    @Override
    @Mapping(source = "instructionId", target = "instruction", qualifiedByName = "getInstructionById")
    public abstract TdrEntity asEntity(TdrDTO dto);

    @Override
    @Mapping(source = "instruction.id", target = "instructionId")
    public abstract TdrDTO asDto(TdrEntity entity);
    
    @Named("getInstructionById")
    public InstructionEntity getInstructionById(Long id) {
        if (id == null) return null;
        return InstructionEntity.builder().id(id).build();
    }
}