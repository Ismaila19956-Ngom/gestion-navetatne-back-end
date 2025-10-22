package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.repositories.UserRepository;
import org.mapstruct.*;
import com.webgram.dgpsn.entities.AgentEntity;
import com.webgram.dgpsn.entities.ProfileEntity;
import com.webgram.dgpsn.entities.StructureEntity;
import com.webgram.dgpsn.entities.UserEntity;
import com.webgram.dgpsn.models.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

//@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring", uses = {AgentMapper.class, StructureMapper.class, ProfileMapper.class})
//public interface UserMapper extends EntityMapper<UserDTO, UserEntity> {
@Mapper(
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        componentModel = "spring",
        uses = {AgentMapper.class, StructureMapper.class, ProfileMapper.class}
)
public abstract class UserMapper implements EntityMapper<UserDTO, UserEntity> {

    @Autowired
    private UserRepository userRepository;
    @Override
    @Mapping(target = "agentId", ignore = true)
    @Mapping(target = "structureId", ignore = true)
    @Mapping(target = "profileId", ignore = true)
   public abstract UserDTO asDto(UserEntity userEntity);

    @Override
    @Mapping(target = "agent", source = "userDTO", qualifiedByName = "agentId")
    @Mapping(target = "profile", source = "userDTO", qualifiedByName = "profileId")
    @Mapping(target = "structure", source = "userDTO", qualifiedByName = "structureId")
    public abstract UserEntity asEntity(UserDTO userDTO);

   @Named("agentId")
   public  AgentEntity agent(UserDTO userDTO) {
        if(Objects.nonNull(userDTO.getAgentId())){
            return AgentEntity.builder().id(userDTO.getAgentId()).build();
        }
        return null;
    }

    @Named("profileId")
    public ProfileEntity profile(UserDTO userDTO) {
        if(Objects.nonNull(userDTO.getProfileId())){
            return ProfileEntity.builder().id(userDTO.getProfileId()).build();
        }
        return null;
    }

    @Named("structureId")
    public StructureEntity structure(UserDTO userDTO) {
        if(Objects.nonNull(userDTO.getStructureId())){
            return StructureEntity.builder().id(userDTO.getStructureId()).build();
        }
        return null;
    }
     public Set<UserEntity> mapIdsToEntities(Set<Long> ids) {
        return ids.stream()
                .map(id -> userRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(String.format("User avec cet id '%d' n'existe pas", id))))
                .collect(Collectors.toSet());
    }

}
