package com.webgram.dgpsn.mappers;

import org.mapstruct.*;
import com.webgram.dgpsn.entities.AgentEntity;
import com.webgram.dgpsn.entities.ProfileEntity;
import com.webgram.dgpsn.entities.StructureEntity;
import com.webgram.dgpsn.entities.UserEntity;
import com.webgram.dgpsn.models.UserDTO;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring", uses = {AgentMapper.class, StructureMapper.class, ProfileMapper.class})
public interface UserMapper extends EntityMapper<UserDTO, UserEntity> {

    @Override
    @Mapping(target = "agentId", ignore = true)
    @Mapping(target = "structureId", ignore = true)
    @Mapping(target = "profileId", ignore = true)
    UserDTO asDto(UserEntity userEntity);

    @Override
    @Mapping(target = "agent", source = "userDTO", qualifiedByName = "agentId")
    @Mapping(target = "profile", source = "userDTO", qualifiedByName = "profileId")
    @Mapping(target = "structure", source = "userDTO", qualifiedByName = "structureId")
    UserEntity asEntity(UserDTO userDTO);

   @Named("agentId")
    default AgentEntity agent(UserDTO userDTO) {
        if(Objects.nonNull(userDTO.getAgentId())){
            return AgentEntity.builder().id(userDTO.getAgentId()).build();
        }
        return null;
    }

    @Named("profileId")
    default ProfileEntity profile(UserDTO userDTO) {
        if(Objects.nonNull(userDTO.getProfileId())){
            return ProfileEntity.builder().id(userDTO.getProfileId()).build();
        }
        return null;
    }

    @Named("structureId")
    default StructureEntity structure(UserDTO userDTO) {
        if(Objects.nonNull(userDTO.getStructureId())){
            return StructureEntity.builder().id(userDTO.getStructureId()).build();
        }
        return null;
    }

}
