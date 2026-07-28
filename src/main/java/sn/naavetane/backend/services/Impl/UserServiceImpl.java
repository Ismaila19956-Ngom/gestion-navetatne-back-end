package sn.naavetane.backend.services.Impl;

import sn.naavetane.backend.exceptions.PasswordException;
import sn.naavetane.backend.exceptions.ResourceAlreadyExistException;
import sn.naavetane.backend.exceptions.ResourceNotFoundException;
import sn.naavetane.backend.mappers.UserMapper;
import sn.naavetane.backend.models.UpdatePasswordDTO;
import sn.naavetane.backend.models.UserDTO;
import sn.naavetane.backend.repositories.UserRepository;
import sn.naavetane.backend.services.UserService;
import sn.naavetane.backend.services.AuditService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final sn.naavetane.backend.repositories.EquipeRepository equipeRepository;
    final UserMapper userMapper;
    final PasswordEncoder passwordEncoder;
    final AuditService auditService;

    private final String USER_IDENTIFIER_NOT_FOUND_MESSAGE = "In valide id User: {0}";
    private final String USER_DIPLICATE_LOGIN_MESSAGE = "Ce login existe déjà: {0}";
    private final String NOT_MACTH_PASSWORD_MESSAGE = "L'ancien mot de passe ne correspond pas";

    private String getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return "Système";
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if(userRepository.findByLogin(userDTO.getLogin()).isPresent()) {
            throw new ResourceAlreadyExistException(MessageFormat.format(USER_DIPLICATE_LOGIN_MESSAGE, userDTO.getLogin()));
        }
        var userEntity = userMapper.asEntity(userDTO);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        var userCreated = userRepository.save(userEntity);
        log.info("create user ok id {}", userCreated.getId());
        
        auditService.logAction(getCurrentUser(), "CREATION", "Utilisateurs", "Création de l'utilisateur: " + userDTO.getLogin(), "IP_LOCALE");
        
        return userMapper.asDto(userCreated);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        var existingUser = userRepository.findById(userDTO.getId())
            .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(USER_IDENTIFIER_NOT_FOUND_MESSAGE, userDTO.getId())));
        
        existingUser.setLogin(userDTO.getLogin());
        existingUser.setStatus(userDTO.isStatus());
        
        if (userDTO.getAgentId() != null) {
            existingUser.setAgent(sn.naavetane.backend.entities.AgentEntity.builder().id(userDTO.getAgentId()).build());
        } else {
            existingUser.setAgent(null);
        }
        
        if (userDTO.getProfileId() != null) {
            existingUser.setProfile(sn.naavetane.backend.entities.ProfileEntity.builder().id(userDTO.getProfileId()).build());
        } else {
            existingUser.setProfile(null);
        }
        
        if (userDTO.getStructureId() != null) {
            existingUser.setStructure(sn.naavetane.backend.entities.StructureEntity.builder().id(userDTO.getStructureId()).build());
        } else {
            existingUser.setStructure(null);
        }
        
        if (StringUtils.isNotBlank(userDTO.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        
        var updatedUser = userRepository.save(existingUser);
        log.info("update user ok id {}", updatedUser.getId());
        
        auditService.logAction(getCurrentUser(), "MODIFICATION", "Utilisateurs", "Mise à jour de l'utilisateur: " + userDTO.getLogin(), "IP_LOCALE");
        
        return userMapper.asDto(updatedUser);
    }

    @Override
    public UserDTO readUser(Long id) {
        var user = userRepository.findById(id)
                .map(userMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(USER_IDENTIFIER_NOT_FOUND_MESSAGE, id)));
        return user;
    }

    @Override
    public UserDTO readUserByLogin(String login) {
        var user = userRepository.findByLogin(login)
                .map(userMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(USER_IDENTIFIER_NOT_FOUND_MESSAGE, login)));
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException(MessageFormat.format(USER_IDENTIFIER_NOT_FOUND_MESSAGE, id));
        }
        UserDTO u = readUser(id);
        userRepository.deleteById(id);
        log.info("delete user ok id {}", id);
        
        auditService.logAction(getCurrentUser(), "SUPPRESSION", "Utilisateurs", "Suppression de l'utilisateur: " + u.getLogin(), "IP_LOCALE");
    }

    @Override
    public Page<UserDTO> readAllUsers(Pageable pageable, String login,  Long agentId, Boolean status, Long structureId, Long profileId, List<String> loginToExcludes) {
        var users = userRepository.readAllByFilters(pageable, structureId, login, agentId, status, profileId, loginToExcludes)
                .map(userMapper::asDto);
        return users;
    }

    @Override
    public void activeOrDesactiveUser(Long userId) {
        var user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException(String.format("User with id %d not found", userId)));
        user.setStatus(user.getStatus()? false: true);
        userRepository.save(user);
        
        auditService.logAction(getCurrentUser(), "MODIFICATION", "Utilisateurs", "Changement de statut (Actif/Inactif) pour l'utilisateur ID: " + userId, "IP_LOCALE");
    }

    @Override
    public void updatePassWordUser(UpdatePasswordDTO user) {
        var userDTO = readUserByLogin(user.getLogin());
        var userEntity = userRepository.findById(userDTO.getId()).orElseThrow(()-> new RuntimeException(String.format("User with id %d not found", userDTO.getId())));
        if(StringUtils.isNotEmpty(user.getHoldPassword())) {
            if(!passwordEncoder.matches(user.getHoldPassword(), userEntity.getPassword())) {
                throw new PasswordException(MessageFormat.format(NOT_MACTH_PASSWORD_MESSAGE,""));
            }
        }
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(userEntity);
        
        auditService.logAction(getCurrentUser(), "SECURITE", "Utilisateurs", "Mise à jour du mot de passe pour: " + user.getLogin(), "IP_LOCALE");
    }

    @Override
    public void addFavoriteTeam(String login, java.util.UUID equipeId) {
        var userEntity = userRepository.findByLogin(login).orElseThrow(()-> new RuntimeException("User not found"));
        var equipeEntity = equipeRepository.findById(equipeId).orElseThrow(()-> new RuntimeException("Equipe not found"));
        if (userEntity.getFavoriteEquipes() == null) {
            userEntity.setFavoriteEquipes(new java.util.HashSet<>());
        }
        userEntity.getFavoriteEquipes().add(equipeEntity);
        userRepository.save(userEntity);
    }

    @Override
    public void removeFavoriteTeam(String login, java.util.UUID equipeId) {
        var userEntity = userRepository.findByLogin(login).orElseThrow(()-> new RuntimeException("User not found"));
        var equipeEntity = equipeRepository.findById(equipeId).orElseThrow(()-> new RuntimeException("Equipe not found"));
        if (userEntity.getFavoriteEquipes() != null) {
            userEntity.getFavoriteEquipes().remove(equipeEntity);
            userRepository.save(userEntity);
        }
    }

    @Override
    public java.util.List<sn.naavetane.backend.dto.EquipeDTO> getFavoriteTeams(String login) {
        var userEntity = userRepository.findByLogin(login).orElseThrow(()-> new RuntimeException("User not found"));
        if (userEntity.getFavoriteEquipes() == null) {
            return new java.util.ArrayList<>();
        }
        return userEntity.getFavoriteEquipes().stream()
                .map(e -> sn.naavetane.backend.dto.EquipeDTO.builder()
                        .id(e.getId())
                        .nom(e.getNom())
                        .build())
                .collect(java.util.stream.Collectors.toList());
    }
}
