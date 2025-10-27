package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.CongeEntity;
import com.webgram.dgpsn.entities.DocumentEntity;
import com.webgram.dgpsn.entities.enums.StatutType;
import com.webgram.dgpsn.models.CongeDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class CongeMapper implements EntityMapper<CongeDTO, CongeEntity> {

    @Mapping(target = "agent.id", source = "agentId")
//    @Mapping(target = "country", source = "countryId" ,qualifiedByName = "getCountry")
    @Mapping(target = "document", source = "documentIds",qualifiedByName ="mapDocument")
    @Mapping(target = "statutType", source = "statutType",qualifiedByName ="mapStatutType")
    @Mapping(target = "soldTotal", source = "soldTotal")
    public abstract  CongeEntity asEntity(CongeDTO dto);

    @Named("mapDocument")
    public List<DocumentEntity> mapDocument(List<Long> documentIds ) {
        List<DocumentEntity> documentEntities = new ArrayList<>();
        if(Objects.nonNull(documentIds)) {
            for(Long documentId: documentIds) {
                documentEntities.add(DocumentEntity.builder().id(documentId).build());
            }
            return documentEntities;
        }
        return null;
    }
    @Named("mapStatutType")
    public StatutType mapStatutType(StatutType statutType) {
        if (Objects.isNull(statutType)) {
            return StatutType.TRAITEMENT_ENCOUR;
        }
        return statutType;
    }

}
