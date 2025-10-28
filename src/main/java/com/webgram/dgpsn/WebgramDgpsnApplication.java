package com.webgram.dgpsn;

import com.webgram.dgpsn.entities.AgentEntity;
import com.webgram.dgpsn.entities.ProfileEntity;
import com.webgram.dgpsn.entities.UserEntity;
import com.webgram.dgpsn.entities.enums.Portee;
import com.webgram.dgpsn.repositories.AgentRepository;
import com.webgram.dgpsn.repositories.ProfileRepository;
import com.webgram.dgpsn.repositories.UserRepository;
import com.webgram.dgpsn.security.SecurityPermissions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@SpringBootApplication(scanBasePackages = {"com.webgram.dgpsn", "com.khoutech.*"})
@EnableScheduling
public class WebgramDgpsnApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebgramDgpsnApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(UserRepository userRepository, ProfileRepository profileRepository, AgentRepository agentRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            createAdminUser(userRepository, profileRepository, agentRepository, passwordEncoder);
        };
    }

    private void createAdminUser(UserRepository userRepository, ProfileRepository profileRepository, AgentRepository agentRepository, PasswordEncoder passwordEncoder) {
        var agent = AgentEntity.builder().prenom("Administrateur").build();
        var userOpt = userRepository.findByLogin("admin");
        var profileOpt = profileRepository.findByCode("ROOT_ADMIN");
        if (userOpt.isEmpty()) {
            var profile = profileOpt.isPresent() ? profileOpt.get() : null;
            if (profileOpt.isEmpty()) {
                List<String> permissions = new ArrayList<>();
                permissions.add(SecurityPermissions.ALL_ACCESS.name());
                profile = profileRepository.save(ProfileEntity.builder()
                        .code("ROOT_ADMIN")
                        .libelle("Super admin")
                        .portee(Portee.TOUT)
                        .permissions(permissions)
                        .build());
            }
            agent = agentRepository.save(agent);
            userRepository.save(UserEntity.builder()
                    .agent(agent)
                    .profile(profile)
                    .login("admin")//
                    .password(passwordEncoder.encode("dgpsn@2025"))
                    .status(true)
                    .firstAttempt(false)
                    .build()
            );
        }
    }

}
