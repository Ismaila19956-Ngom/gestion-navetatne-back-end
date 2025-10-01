package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.ProfileEntity;
import com.webgram.dgpsn.models.ProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProfileMapper extends EntityMapper<ProfileDTO, ProfileEntity> {
}
