package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.MarketFileDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MarketFileMapper extends EntityMapper<MarketFileDTO, MarketFileEntity> {


    @Mapping(target = "timeReceptFile", source = "timeReceptFile", qualifiedByName = "parseTimes")
    @Mapping(target = "receptionStatus", source = "receptionStatusId", qualifiedByName = "getReceptionStatus")
    @Mapping(target = "passationMarket", source = "passationMarketId",qualifiedByName ="getPassationMarket")
    MarketFileEntity asEntity(MarketFileDTO dto);


    @Mapping(source = "receptionStatus.id", target = "receptionStatusId")
    @Mapping(source = "passationMarket.id", target = "passationMarketId")
    MarketFileDTO asDto( MarketFileEntity marketFileEntity);

    @Mapping(source = "receptionStatus.id", target = "receptionStatusId")
    @Mapping(source = "passationMarket.id", target = "passationMarketId")
    List<MarketFileDTO> parse(List<MarketFileEntity>entityList);
    @Named("getPassationMarket")
    default PassationMarketEntity builtPassationMarket(Long passationMarketId) {
        if(Objects.nonNull(passationMarketId)){
            return PassationMarketEntity.builder().id(passationMarketId).build();
        }
        return null;
    }

    @Named("getReceptionStatus")
    default LabelEntity getReceptionStatus(Long receptionStatusId) {
        if(Objects.nonNull(receptionStatusId)){
            return LabelEntity.builder().id(receptionStatusId).build();
        }
        return null;
    }

    @Named("parseTimes")
    default Date parseTimes(String time) {
        try {
            return new SimpleDateFormat("HH:MM").parse(time);
        } catch (ParseException ex) {
            System.out.println("Parsing heureDebutPrevue... error");
            return null;
        }
    }
}
