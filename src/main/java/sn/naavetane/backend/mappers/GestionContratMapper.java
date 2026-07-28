package sn.naavetane.backend.mappers;

import sn.naavetane.backend.entities.GestionContratEntity;
import sn.naavetane.backend.models.GestionContratDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GestionContratMapper extends EntityMapper<GestionContratDTO, GestionContratEntity> {
    @Mapping(source = "typeContratId", target = "typeContrat.id")
    @Override
    GestionContratEntity asEntity(GestionContratDTO dto);

    @Override
    GestionContratDTO asDto(GestionContratEntity entity);
}
