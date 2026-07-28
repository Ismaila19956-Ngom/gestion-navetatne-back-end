package sn.naavetane.backend.services.Impl;

import sn.naavetane.backend.models.requests.SignUpDTO;
import sn.naavetane.backend.models.responses.SignInAuthentication;
import sn.naavetane.backend.repositories.AgentRepository;
import sn.naavetane.backend.repositories.ProfileRepository;
import sn.naavetane.backend.repositories.UserRepository;
import sn.naavetane.backend.entities.AgentEntity;
import sn.naavetane.backend.entities.UserEntity;
import sn.naavetane.backend.security.jwt.TokenProvider;
import sn.naavetane.backend.services.AuthenticationService;
import sn.naavetane.backend.services.AuditService;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    static final String TOKEN_TYPE ="Bearer";

    final AuthenticationManagerBuilder authenticationManagerBuilder;
    final TokenProvider tokenProvider;
    final UserRepository userRepository;
    final AgentRepository agentRepository;
    final ProfileRepository profileRepository;
    final PasswordEncoder passwordEncoder;
    final AuditService auditService;

    static final String ACCOUNT_LOCKED = "Ce compte est bloqué: {0}";

    @Override
    public SignInAuthentication signIn(String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username,
                password
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = tokenProvider.createToken(authentication, true);
        
        var user = userRepository.findByLogin(username).orElseThrow();
        user.setOnline(Boolean.TRUE);
        user.setLastConnexion(LocalDateTime.now());
        userRepository.save(user);
        
        auditService.logAction(username, "CONNEXION", "Système", "L'utilisateur s'est connecté", "IP_LOCALE");

        return SignInAuthentication
                .builder()
                .tokenType(TOKEN_TYPE)
                .accessToken(accessToken)
                .build();
    }

    @Override
    public SignInAuthentication signUp(SignUpDTO signUpDTO) {
        if (userRepository.findByLogin(signUpDTO.getTelephone()).isPresent()) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Un utilisateur avec ce numéro existe déjà.");
        }

        var agent = AgentEntity.builder()
                .prenom(signUpDTO.getPrenom())
                .nom(signUpDTO.getNom())
                .telephone(signUpDTO.getTelephone())
                .build();
        agent = agentRepository.save(agent);

        var profile = profileRepository.findByCode("PARTICIPANT").orElse(null);

        var user = UserEntity.builder()
                .agent(agent)
                .profile(profile)
                .login(signUpDTO.getTelephone())
                .password(passwordEncoder.encode(signUpDTO.getPassword()))
                .status(true)
                .firstAttempt(false)
                .build();
        userRepository.save(user);
        
        auditService.logAction(signUpDTO.getTelephone(), "INSCRIPTION", "Utilisateur", "Création de compte via Inscription", "IP_LOCALE");

        return signIn(signUpDTO.getTelephone(), signUpDTO.getPassword());
    }

    @Override
    public void signOut(String username) {
        var user = userRepository.findByLogin(username).orElseThrow();
        user.setOnline(Boolean.FALSE);
        userRepository.save(user);
        
        auditService.logAction(username, "DECONNEXION", "Système", "L'utilisateur s'est déconnecté", "IP_LOCALE");
    }
}
