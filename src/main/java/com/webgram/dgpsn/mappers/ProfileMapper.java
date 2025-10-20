package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.models.ProfileDTO;
import com.webgram.dgpsn.entities.ProfileEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProfileMapper extends EntityMapper<ProfileDTO, ProfileEntity> {
}
