package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.JournalEntity;
import com.webgram.dgpsn.models.JournalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface JournalMapper extends EntityMapper<JournalDTO, JournalEntity> {

}
