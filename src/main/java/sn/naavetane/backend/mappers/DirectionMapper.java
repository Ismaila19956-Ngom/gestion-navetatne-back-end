package sn.naavetane.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import sn.naavetane.backend.entities.DirectionEntity;
import sn.naavetane.backend.models.DirectionDTO;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class DirectionMapper implements EntityMapper<DirectionDTO, DirectionEntity> {
   public abstract DirectionEntity asEntity(DirectionDTO directionDto);

   public DirectionDTO asDto(DirectionEntity directionEntity) {
      if(Objects.isNull(directionEntity.getChildren())) {
         directionEntity.setChildren(new ArrayList<>());
      }

      return DirectionDTO.builder()
              .id(directionEntity.getId())
              .code(directionEntity.getCode())
              .libelle(directionEntity.getLibelle())
              .children(directionEntity.getChildren().stream()
                      .map(this::asDto)
                      .collect(Collectors.toList()))
              .build();
   }
}
