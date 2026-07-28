package sn.naavetane.backend.mappers;

import sn.naavetane.backend.entities.PointDeVenteEntity;
import sn.naavetane.backend.entities.TicketEntity;
import sn.naavetane.backend.models.TicketDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = {PointDeVenteMapper.class}
)
public abstract class TicketMapper implements EntityMapper<TicketDTO, TicketEntity> {

    @Override
    @Mapping(target = "pointDeVenteId", source = "pointDeVente.id")
    public abstract TicketDTO asDto(TicketEntity entity);

    @Override
    @Mapping(target = "pointDeVente", source = "dto", qualifiedByName = "pointDeVenteId")
    public abstract TicketEntity asEntity(TicketDTO dto);

    @Named("pointDeVenteId")
    public PointDeVenteEntity mapPointDeVente(TicketDTO dto) {
        if (Objects.nonNull(dto.getPointDeVenteId())) {
            return PointDeVenteEntity.builder().id(dto.getPointDeVenteId()).build();
        }
        return null;
    }
}
