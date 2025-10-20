package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.PreselectionnedFileDTO;

import java.util.List;
import java.util.Objects;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PreselectionnedFileMapper extends EntityMapper<PreselectionnedFileDTO, PreselectionnedFileEntity> {



    @Mapping(target = "passationMarket", source = "passationMarketId", qualifiedByName = "getPassationMarket")
    @Mapping(target = "marketFileEntity", source = "marketFileId",qualifiedByName ="getMarketFile")
    PreselectionnedFileEntity asEntity(PreselectionnedFileDTO dto);


    @Mapping(source = "passationMarket.id", target = "passationMarketId")
    @Mapping(source = "marketFileEntity.id", target = "marketFileId")
    PreselectionnedFileDTO asDto( PreselectionnedFileEntity PreselectionnedFileEntity);

    @Mapping(target = "passationMarket", source = "passationMarketId", qualifiedByName = "getPassationMarket")
    @Mapping(target = "marketFileEntity", source = "marketFileId",qualifiedByName ="getMarketFile")
    List<PreselectionnedFileEntity> parseToEntity(List<PreselectionnedFileDTO> dto);


    @Mapping(source = "passationMarket.id", target = "passationMarketId")
    @Mapping(source = "marketFileEntity.id", target = "marketFileId")
    List<PreselectionnedFileDTO>parse(List<PreselectionnedFileEntity>entityList);


    @Named("getMarketFile")
    default MarketFileEntity builtTutele(Long marketFileId) {
        if(Objects.nonNull(marketFileId)){
            return MarketFileEntity.builder().id(marketFileId).build();
        }
        return null;
    }


    @Named("getPassationMarket")
    default PassationMarketEntity getCible(Long passationMarketId) {
        if(Objects.nonNull(passationMarketId)){
            return PassationMarketEntity.builder().id(passationMarketId).build();
        }
        return null;
    }






}
