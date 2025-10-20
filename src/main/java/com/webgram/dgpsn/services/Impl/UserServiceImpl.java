package com.webgram.dgpsn.services.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.webgram.dgpsn.exceptions.PasswordException;
import com.webgram.dgpsn.exceptions.ResourceAlreadyExistException;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.UserMapper;
import com.webgram.dgpsn.models.UpdatePasswordDTO;
import com.webgram.dgpsn.models.UserDTO;
import com.webgram.dgpsn.repositories.UserRepository;
import com.webgram.dgpsn.services.UserService;

import java.text.MessageFormat;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class UserServiceImpl implements UserService {

   private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final String USER_IDENTIFIER_NOT_FOUND_MESSAGE = "In valide id User: {0}";
    private final String USER_DIPLICATE_LOGIN_MESSAGE = "Ce login existe déjà: {0}";
    private final String NOT_MACTH_PASSWORD_MESSAGE = "L'ancien mot de passe ne correspond pas";


    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if(userRepository.findByLogin(userDTO.getLogin()).isPresent()) {
            throw new ResourceAlreadyExistException(MessageFormat.format(USER_DIPLICATE_LOGIN_MESSAGE, userDTO.getLogin()));
        }
        var userEntity = userMapper.asEntity(userDTO);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        var userCreated = userRepository.save(userEntity);
        log.info("create user ok id {}", userCreated.getId());
        log.trace("create user ok  {}", userCreated);
        return userMapper.asDto(userCreated);

    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        if(!userRepository.existsById(userDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(USER_IDENTIFIER_NOT_FOUND_MESSAGE, userDTO.getId()));
        }
        var updatedUser = userRepository.save(userMapper.asEntity(userDTO));
        log.info("update user ok id {}", updatedUser.getId());
        log.trace("update user ok  {}", updatedUser);
        return userMapper.asDto(updatedUser);
    }

    @Override
    public UserDTO readUser(Long id) {
        var user = userRepository.findById(id)
                .map(userMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(USER_IDENTIFIER_NOT_FOUND_MESSAGE, id)));
        log.info("read user end ok - Id: {}", id);
        log.trace("read user end ok - user: {}", user);
        return user;
    }

    @Override
    public UserDTO readUserByLogin(String login) {
        var user = userRepository.findByLogin(login)
                .map(userMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(USER_IDENTIFIER_NOT_FOUND_MESSAGE, login)));
        log.info("read user end ok - Login: {}", login);
        log.trace("read user end ok - user: {}", user);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException(MessageFormat.format(USER_IDENTIFIER_NOT_FOUND_MESSAGE, id));
        }
        userRepository.deleteById(id);
        log.info("delete user ok id {}", id);

    }

    @Override
    public Page<UserDTO> readAllUsers(Pageable pageable, String login,  Long agentId, Boolean status, Long structureId, Long profileId, List<String> loginToExcludes) {
        var users = userRepository.readAllByFilters(pageable, structureId, login, agentId, status, profileId, loginToExcludes)
                .map(userMapper::asDto);
        log.trace("list user get ok {}", users);
        return users;
    }

    @Override
    public void activeOrDesactiveUser(Long userId) {
        var user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException(String.format("User with id %d not found", userId)));
        user.setStatus(user.getStatus()? false: true);
        userRepository.save(user);
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
    }
}
