    package com.webgram.dgpsn.mappers;

    import com.webgram.dgpsn.entities.BudgetPassationEntity;
    import com.webgram.dgpsn.entities.RecrutementEntity;
    import com.webgram.dgpsn.models.BudgetPassationDTO;
    import com.webgram.dgpsn.models.RecrutementDTO;
    import org.mapstruct.*;

    @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",  uses = {CaracteristiqueExigeMapper.class} )
    public interface RecrutementMapper extends EntityMapper<RecrutementDTO, RecrutementEntity> {
        @Mapping(source = "typeContrat.id", target = "typeContratId")

        @Override
        RecrutementDTO asDto(RecrutementEntity entity);
        @Mapping(source = "typeContratId", target = "typeContrat.id")

        @Override
        RecrutementEntity asEntity(RecrutementDTO dto);

    }