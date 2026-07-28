package sn.naavetane.backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.naavetane.backend.models.UpdatePasswordDTO;
import sn.naavetane.backend.models.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO);
    UserDTO readUser(Long id);
    UserDTO readUserByLogin(String login);
    void deleteUser(Long id);
    Page<UserDTO> readAllUsers(Pageable pageable, String login, Long agentId, Boolean status, Long structureId, Long profileId, List<String> loginToExcludes);
    void activeOrDesactiveUser(Long userId);
    void updatePassWordUser(UpdatePasswordDTO user);
    void addFavoriteTeam(String login, java.util.UUID equipeId);
    void removeFavoriteTeam(String login, java.util.UUID equipeId);
    java.util.List<sn.naavetane.backend.dto.EquipeDTO> getFavoriteTeams(String login);
}
