package sn.naavetane.backend.mappers;

import sn.naavetane.backend.exceptions.ResourceNotFoundException;
import sn.naavetane.backend.repositories.UserRepository;
import org.mapstruct.*;
import sn.naavetane.backend.entities.AgentEntity;
import sn.naavetane.backend.entities.ProfileEntity;
import sn.naavetane.backend.entities.StructureEntity;
import sn.naavetane.backend.entities.UserEntity;
import sn.naavetane.backend.models.UserDTO;
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
    @Mapping(target = "favoriteEquipes", ignore = true)
    @Mapping(target = "favoriteEquipesIds", ignore = true)
   public abstract UserDTO asDto(UserEntity userEntity);

    @Override
    @Mapping(target = "agent", source = "userDTO", qualifiedByName = "agentId")
    @Mapping(target = "profile", source = "userDTO", qualifiedByName = "profileId")
    @Mapping(target = "structure", source = "userDTO", qualifiedByName = "structureId")
    @Mapping(target = "favoriteEquipes", ignore = true)
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
