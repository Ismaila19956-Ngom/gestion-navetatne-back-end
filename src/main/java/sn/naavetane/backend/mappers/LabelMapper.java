package sn.naavetane.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import sn.naavetane.backend.entities.LabelEntity;
import sn.naavetane.backend.models.LabelDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface LabelMapper extends EntityMapper<LabelDTO, LabelEntity> {
}
