package com.webgram.dgpsn.security.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.entities.ProfileEntity;
import com.webgram.dgpsn.entities.UserEntity;
import com.webgram.dgpsn.exceptions.UserNotActivatedException;
import com.webgram.dgpsn.repositories.ProfileRepository;
import com.webgram.dgpsn.repositories.UserRepository;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DomainUserDetailsService implements UserDetailsService {

    final UserRepository userRepository;

    final ProfileRepository profileRepository;
    static final String ACCOUNT_LOCKED = "Ce compte est bloqué: {0}";


    @Override
    public User loadUserByUsername(final String username) {

        log.debug("Authenticating {}", username);

         var userEntity = userRepository.findByLogin(username)
                 .orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("User {0}  was not found in the database", username)));

         if(userEntity.getStatus() == false) {
            throw new UserNotActivatedException(MessageFormat.format(ACCOUNT_LOCKED, username));
        }

        if (new EmailValidator().isValid(username, null)) {
            return userRepository
                    .findByAgentEmailIgnoreCase(username)
                    .map(user -> createSpringSecurityUser(username, user))
                    .orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("User with email {0} was not found in the database", username)));
        }

        return createSpringSecurityUser(username, userEntity);

    }

    private User createSpringSecurityUser(String username, UserEntity user) {

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (Objects.nonNull(user.getProfile())) {

            /* Reading all role from user*/
            grantedAuthorities.addAll(profileRepository
                    .findByCode(user.getProfile().getCode())
                    .map(ProfileEntity::getPermissions)
                    .map(securityPermissions -> securityPermissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()))
                    .orElse(new ArrayList<>()));

            /* Adding role to permissions */
            grantedAuthorities.add(new SimpleGrantedAuthority(user.getProfile().getCode()));

        }

        return new User(user.getLogin(), user.getPassword(), grantedAuthorities);
    }
}
