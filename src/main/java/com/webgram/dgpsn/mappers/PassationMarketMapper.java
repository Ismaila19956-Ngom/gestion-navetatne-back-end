package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.PassationMarketDTO;


import java.util.Objects;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PassationMarketMapper extends EntityMapper<PassationMarketDTO, PassationMarketEntity> {



    @Mapping(target = "passationPlanEntity", source = "passationPlanId", qualifiedByName = "getPassationPlan")
    @Mapping(target = "typeMarket", source = "typeMarketId",qualifiedByName ="getTypeMarket")
    @Mapping(target = "typePassation", source = "typePassationId",qualifiedByName ="getTypePassation")
    @Mapping(target = "cible", source = "cibleId",qualifiedByName ="getCible")
    PassationMarketEntity asEntity(PassationMarketDTO dto);


//    @Mapping(source = "passationMarketCritereEntityList.id", target = "criteresIds",qualifiedByName = ("getCriteres"))
    @Mapping(source = "passationPlanEntity.id", target = "passationPlanId")
    @Mapping(source = "typeMarket.id", target= "typeMarketId")
    @Mapping(source = "typePassation.id", target = "typePassationId")
    @Mapping(source = "cible.id", target = "cibleId")
    PassationMarketDTO asDto( PassationMarketEntity passationMarketEntity);

//    @Named("getCriteres")
//    default List<Long> getCritereIds() {
//        if (criteresIds == null) {
//            // Charger les critères associés à partir de l'entité
//            List<PassationMarketCritereEntity> criteres = passationMarketEntity.getPassationMarketCritereEntityList();
//            criteresIds = criteres.stream().map(PassationMarketCritereEntity::getId).collect(Collectors.toList());
//        }
//        return criteresIds;
//    }

    @Named("getPassationPlan")
    default PassationPlanEntity builtTutele(Long passationPlanId) {
        if(Objects.nonNull(passationPlanId)){
            return PassationPlanEntity.builder().id(passationPlanId).build();
        }
        return null;
    }

    @Named("getTypeMarket")
    default LabelEntity getTypeMarket(Long typeMarketId) {
        if(Objects.nonNull(typeMarketId)){
            return LabelEntity.builder().id(typeMarketId).build();
        }
        return null;
    }

    @Named("getTypePassation")
    default LabelEntity getTypepassation(Long typePassationId) {
        if(Objects.nonNull(typePassationId)){
            return LabelEntity.builder().id(typePassationId).build();
        }
        return null;
    }

    @Named("getCible")
    default LabelEntity getCible(Long cibleId) {
        if(Objects.nonNull(cibleId)){
            return LabelEntity.builder().id(cibleId).build();
        }
        return null;
    }






}
