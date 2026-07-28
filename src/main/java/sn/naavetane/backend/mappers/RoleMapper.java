package sn.naavetane.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import sn.naavetane.backend.entities.RoleEntity;
import sn.naavetane.backend.models.RoleDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDTO, RoleEntity> {

}
