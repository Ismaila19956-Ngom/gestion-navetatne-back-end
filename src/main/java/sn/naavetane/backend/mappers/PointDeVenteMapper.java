package sn.naavetane.backend.mappers;

import sn.naavetane.backend.entities.PointDeVenteEntity;
import sn.naavetane.backend.models.PointDeVenteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE, // IGNORE used for simplicity over ERROR to avoid deep dependency issues initially
        componentModel = "spring"
)
public abstract class PointDeVenteMapper implements EntityMapper<PointDeVenteDTO, PointDeVenteEntity> {

    @Override
    public abstract PointDeVenteDTO asDto(PointDeVenteEntity entity);

    @Override
    public abstract PointDeVenteEntity asEntity(PointDeVenteDTO dto);
}
