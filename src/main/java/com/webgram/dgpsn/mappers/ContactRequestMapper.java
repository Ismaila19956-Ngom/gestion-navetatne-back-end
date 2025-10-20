package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.ContactRequestEntity;
import com.webgram.dgpsn.entities.DirectionEntity;
import com.webgram.dgpsn.models.ContactRequestDTO;
import com.webgram.dgpsn.repositories.DirectionRepository;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class ContactRequestMapper implements EntityMapper<ContactRequestDTO, ContactRequestEntity> {

    @Autowired
    private DirectionRepository directionRepository;

    @Override
//    @Mapping(target = "service", source = "serviceId", qualifiedByName = "getService")
    @Mapping(target = "serviceId", source = "service.id") // Map service ID to DTO
    @Mapping(target = "serviceLibelle", source = "service.libelle") // Map service libelle to DTO
    public abstract ContactRequestDTO asDto(ContactRequestEntity entity);

    @Override
    @Mapping(target = "service", source = "serviceId", qualifiedByName = "getService")
    public abstract ContactRequestEntity asEntity(ContactRequestDTO contactRequestDTO);

    @Named("getService")
    public DirectionEntity getService(Long serviceId) {
        if (Objects.nonNull(serviceId)) {
            return DirectionEntity.builder().id(serviceId).build();
        }
        return null;
    }
}