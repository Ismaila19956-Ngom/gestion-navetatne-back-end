package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.UpdatePasswordDTO;
import com.webgram.dgpsn.models.UserDTO;

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
}
