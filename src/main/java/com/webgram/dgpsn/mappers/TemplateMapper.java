package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.ProfileEntity;
import com.webgram.dgpsn.models.TemplateDto;
import com.webgram.dgpsn.entities.TemplateEntity;

import java.util.HashSet;
import java.util.Set;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TemplateMapper extends EntityMapper<TemplateDto, TemplateEntity> {

    @Mapping(target = "profiles", source = "profileIds", qualifiedByName = "setProfiles")
    TemplateEntity asEntity(TemplateDto dto);

    @Named("setProfiles")
    default Set<ProfileEntity> setProfiles(Set<Long> profileIds) {
        Set<ProfileEntity> profiles = new HashSet<>();
        profileIds.forEach(profileId -> {
            profiles.add(ProfileEntity.builder().id(profileId).build());
        });
        return profiles;
    }

}
