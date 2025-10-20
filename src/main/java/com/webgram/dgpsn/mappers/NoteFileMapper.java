package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.models.NoteFileDTO;
import com.webgram.dgpsn.repositories.MarketFileRepository;
import com.webgram.dgpsn.repositories.PassationMarketCritereRepository;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class NoteFileMapper implements EntityMapper<NoteFileDTO, NoteFileEntity> {

      @Autowired
      private PassationMarketCritereRepository passationMarketCritereRepository;
      @Autowired
      private MarketFileRepository marketFileRepository;


    @Mapping(target = "passationMarketCritere", source = "passationMarketCritereId", qualifiedByName = "getPassationMarketCritere")
    @Mapping(target = "marketFileEntity", source = "marketFileId",qualifiedByName ="getMaketFile")
    public abstract NoteFileEntity asEntity(NoteFileDTO dto);


    @Mapping(source = "passationMarketCritere.id", target = "passationMarketCritereId")
    @Mapping(source = "marketFileEntity.id", target = "marketFileId")
    public abstract  NoteFileDTO asDto( NoteFileEntity noteFileEntity);


    @Mapping(target = "passationMarketCritere", source = "passationMarketCritereId", qualifiedByName = "getPassationMarketCritere")
    @Mapping(target = "marketFileEntity", source = "marketFileId",qualifiedByName ="getMaketFile")
    public abstract List<NoteFileEntity>parseToEntity(List<NoteFileDTO> dto);


    @Mapping(source = "passationMarketCritere.id", target = "passationMarketCritereId")
    @Mapping(source = "marketFileEntity.id", target = "marketFileId")
    public abstract List<NoteFileDTO>parse(List<NoteFileEntity>entityList);


//    @Named("ignorePermissions")
//    RoleRspDTO toDtoWithoutPermission(Role role);
//
//    default List<RoleRspDTO> toDtoListWithoutPermission(List<Role> entityList) {
//        return entityList.stream().map(this::toDtoWithoutPermission).collect(Collectors.toList());
//    }

    @Named("getPassationMarketCritere")
    public PassationMarketCritereEntity builtPassationCritereMarket(Long passationMarketCritereId) {
        if(Objects.nonNull(passationMarketCritereId)){
            //return PassationMarketCritereEntity.builder().id(passationMarketCritereId).build();
            var passationMarket = passationMarketCritereRepository.findById(passationMarketCritereId)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code statut {} n'existe pas", passationMarketCritereId)));
            return passationMarket;
        }
        return null;
    }

    @Named("getMaketFile")
    public MarketFileEntity getReceptionStatus(Long marketFileId) {
        if(Objects.nonNull(marketFileId)){
//            return MarketFileEntity.builder().id(marketFileId).build();
            var marketFile = marketFileRepository.findById(marketFileId)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code statut {} n'existe pas", marketFileId)));
            return marketFile;
        }
        return null;
    }
}
