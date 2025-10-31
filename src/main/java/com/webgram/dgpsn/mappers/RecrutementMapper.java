    package com.webgram.dgpsn.mappers;

    import com.webgram.dgpsn.entities.BudgetPassationEntity;
    import com.webgram.dgpsn.entities.RecrutementEntity;
    import com.webgram.dgpsn.models.BudgetPassationDTO;
    import com.webgram.dgpsn.models.RecrutementDTO;
    import org.mapstruct.*;

    @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",  uses = {CaracteristiqueExigeMapper.class} )
    public interface RecrutementMapper extends EntityMapper<RecrutementDTO, RecrutementEntity> {
        @Override
        RecrutementDTO asDto(RecrutementEntity entity);

        @Override
        RecrutementEntity asEntity(RecrutementDTO dto);

    }