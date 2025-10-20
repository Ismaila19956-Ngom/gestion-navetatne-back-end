package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.UserEntity;
import com.webgram.dgpsn.entities.WorkflowStepEntity;
import com.webgram.dgpsn.entities.WorkflowValidationHistoriqueEntity;
import com.webgram.dgpsn.models.WorkflowValidationHistoriqueDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class WorkflowValidationHistoriqueMapper implements EntityMapper<WorkflowValidationHistoriqueDTO, WorkflowValidationHistoriqueEntity> {
    @Override
    @Mapping(target = "etape", source = "etapeId", qualifiedByName = "getEtape")
    @Mapping(source = "year", target = "year")
    @Mapping(source = "month", target = "month")
    public abstract WorkflowValidationHistoriqueEntity asEntity(WorkflowValidationHistoriqueDTO dto);

    @Override
    @Mapping(target = "user", source = "user", qualifiedByName = "getData")
    public abstract WorkflowValidationHistoriqueDTO asDto(WorkflowValidationHistoriqueEntity entity);

    @Named("getData")
    public WorkflowValidationHistoriqueDTO.UserData getData(UserEntity user){
        if (Objects.isNull(user)) return null;
        return WorkflowValidationHistoriqueDTO.UserData.builder()
                .id(user.getId())
                .prenom(user.getAgent().getPrenom())
                .nom(user.getAgent().getNom())
                .profil(user.getProfile().getLibelle())
                .build();
    }

    @Named("getEtape")
    public WorkflowStepEntity getEtape(Long etapeId){
        if (Objects.isNull(etapeId)) return null;
        else {
            return WorkflowStepEntity.builder().id(etapeId).build();
        }
    }
}
