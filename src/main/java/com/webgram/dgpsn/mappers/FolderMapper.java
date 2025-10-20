package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.FolderEntity;
import com.webgram.dgpsn.models.FolderDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface FolderMapper extends EntityMapper<FolderDto, FolderEntity> {
}
