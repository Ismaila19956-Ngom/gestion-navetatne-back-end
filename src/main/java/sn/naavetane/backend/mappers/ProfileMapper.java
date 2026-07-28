package sn.naavetane.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import sn.naavetane.backend.models.ProfileDTO;
import sn.naavetane.backend.entities.ProfileEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProfileMapper extends EntityMapper<ProfileDTO, ProfileEntity> {
}
