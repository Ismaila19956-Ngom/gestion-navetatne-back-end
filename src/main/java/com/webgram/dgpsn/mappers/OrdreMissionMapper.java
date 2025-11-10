package com.webgram.dgpsn.mappers;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.AgentEntity;
import com.webgram.dgpsn.entities.DocumentEntity;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.OrdreMissionEntity;
import com.webgram.dgpsn.entities.enums.FamilyRelashionshipType;
import com.webgram.dgpsn.models.OrdreMissionDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface OrdreMissionMapper extends EntityMapper<OrdreMissionDTO, OrdreMissionEntity> {

    @Mapping(target = "agent", source = "agentIds",qualifiedByName ="getAgent")
    @Mapping(target = "frais", source = "fraisId",qualifiedByName ="getFrais")
    @Mapping(target = "moyenTranport", source = "moyenTranportId",qualifiedByName ="getMoyenTranport")
    @Mapping(target = "priseEnCharge", source = "priseEnChargeId",qualifiedByName ="getPriseEnCharge")
    @Mapping(target = "document", source = "documentIds",qualifiedByName ="mapDocument")
    OrdreMissionEntity asEntity(OrdreMissionDTO dto);


    @Mapping(source = "frais.id", target = "fraisId")
    OrdreMissionDTO asDto(OrdreMissionEntity ordreMission);


    @Named("mapDocument")
    default   public List<DocumentEntity> mapDocument(List<Long> documentIds ) {
        List<DocumentEntity> documentEntities = new ArrayList<>();
        if(Objects.nonNull(documentIds)) {
            for(Long documentId: documentIds) {
                documentEntities.add(DocumentEntity.builder().id(documentId).build());
            }
            return documentEntities;
        }
        return null;
    }

    @Named("getAgent")
    default List<AgentEntity> builtAgent(List<Long> agentIds) {
        List<AgentEntity> agentEntities = new ArrayList<>();

        if(Objects.nonNull(agentIds)){
            for(Long agentId: agentIds) {
                agentEntities.add(AgentEntity.builder().id(agentId).build());
            }
            return agentEntities;
        }
        return null;
    }

    @Named("getPriseEnCharge")
    default List<LabelEntity> builtPriseEnCharge(List<Long> priseEnChargeId) {
        List<LabelEntity> priseEntities = new ArrayList<>();

        if(Objects.nonNull(priseEnChargeId)){
            for(Long priseChargeId: priseEnChargeId) {
                priseEntities.add(LabelEntity.builder().id(priseChargeId).build());
            }
            return priseEntities;
        }
        return null;

    }

    @Named("getFrais")
    default LabelEntity builtFrais(Long fraisId) {
        if(Objects.nonNull(fraisId)){
            return LabelEntity.builder().id(fraisId).build();
        }
        return null;
    }

    @Named("getMoyenTranport")
    default LabelEntity getMoyenTranport(Long moyenTranportd) {
        if(Objects.nonNull(moyenTranportd)){
            return LabelEntity.builder().id(moyenTranportd).build();
        }
        return null;
    }


    @Named("getFamilyRelashion")
    default FamilyRelashionshipType getTypeFamilyRelashion(String stringType) {
        if(StringUtils.isNotEmpty(stringType)){
            return FamilyRelashionshipType.valueOf(stringType);
        }
        return null;
    }


}
