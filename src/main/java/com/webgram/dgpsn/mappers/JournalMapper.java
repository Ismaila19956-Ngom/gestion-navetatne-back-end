package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.JournalEntity;
import com.webgram.dgpsn.models.JournalDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface JournalMapper extends EntityMapper<JournalDTO, JournalEntity> {

}
