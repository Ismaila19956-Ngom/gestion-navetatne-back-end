package sn.naavetane.backend;

import sn.naavetane.backend.entities.AgentEntity;
import sn.naavetane.backend.entities.DirectionEntity;
import sn.naavetane.backend.entities.ProfileEntity;
import sn.naavetane.backend.entities.UserEntity;
import sn.naavetane.backend.entities.enums.Portee;
import sn.naavetane.backend.repositories.AgentRepository;
import sn.naavetane.backend.repositories.DirectionRepository;
import sn.naavetane.backend.repositories.ProfileRepository;
import sn.naavetane.backend.repositories.UserRepository;
import sn.naavetane.backend.repositories.TypeActualiteRepository;
import sn.naavetane.backend.entities.TypeActualiteEntity;
import sn.naavetane.backend.security.SecurityPermissions;

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
@SpringBootApplication(scanBasePackages = {"sn.naavetane.backend", "com.khoutech.*"})
@EnableScheduling
public class NaavetaneApplication {

    public static void main(String[] args) {
        SpringApplication.run(NaavetaneApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(DirectionRepository directionRepository, UserRepository userRepository, ProfileRepository profileRepository, AgentRepository agentRepository, PasswordEncoder passwordEncoder, TypeActualiteRepository typeActualiteRepository) {
        return args -> {
            createAdminUser(userRepository, profileRepository, agentRepository, passwordEncoder);
            createVendeurUser(userRepository, profileRepository, agentRepository, passwordEncoder);
            createScannerUser(userRepository, profileRepository, agentRepository, passwordEncoder);
            initOrganigramme(directionRepository);
            initTypesActualite(typeActualiteRepository);
        };
    }

    private void initTypesActualite(TypeActualiteRepository typeRepository) {
        if (typeRepository.count() == 0) {
            typeRepository.save(TypeActualiteEntity.builder().code("ACTUALITE").libelle("Actualité Sportive").actif(true).build());
            typeRepository.save(TypeActualiteEntity.builder().code("COMMUNIQUE").libelle("Communiqué CQRP").actif(true).build());
            log.info("Référentiel des types d'actualités initialisé.");
        }
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
                    .login("admin")
                    .password(passwordEncoder.encode("navetane@2026"))
                    .status(true)
                    .firstAttempt(false)
                    .build()
            );
        }
    }

    private void createVendeurUser(UserRepository userRepository, ProfileRepository profileRepository, AgentRepository agentRepository, PasswordEncoder passwordEncoder) {
        var agent = AgentEntity.builder().prenom("Vendeur").nom("Guichet").build();
        var userOpt = userRepository.findByLogin("vendeur");
        var profileOpt = profileRepository.findByCode("VENDEUR");
        if (userOpt.isEmpty()) {
            var profile = profileOpt.isPresent() ? profileOpt.get() : null;
            if (profileOpt.isEmpty()) {
                List<String> permissions = new ArrayList<>();
                profile = profileRepository.save(ProfileEntity.builder()
                        .code("VENDEUR")
                        .libelle("Vendeur Guichet")
                        .portee(Portee.TOUT)
                        .permissions(permissions)
                        .build());
            }
            agent = agentRepository.save(agent);
            userRepository.save(UserEntity.builder()
                    .agent(agent)
                    .profile(profile)
                    .login("vendeur")
                    .password(passwordEncoder.encode("vendeur@2026"))
                    .status(true)
                    .firstAttempt(false)
                    .build()
            );
        }
    }

    private void createScannerUser(UserRepository userRepository, ProfileRepository profileRepository, AgentRepository agentRepository, PasswordEncoder passwordEncoder) {
        var agent = AgentEntity.builder().prenom("Controleur").nom("Scan").build();
        var userOpt = userRepository.findByLogin("scan");
        var profileOpt = profileRepository.findByCode("SCANNER");
        if (userOpt.isEmpty()) {
            var profile = profileOpt.isPresent() ? profileOpt.get() : null;
            if (profileOpt.isEmpty()) {
                List<String> permissions = new ArrayList<>();
                profile = profileRepository.save(ProfileEntity.builder()
                        .code("SCANNER")
                        .libelle("Controleur Billet")
                        .portee(Portee.TOUT)
                        .permissions(permissions)
                        .build());
            }
            agent = agentRepository.save(agent);
            userRepository.save(UserEntity.builder()
                    .agent(agent)
                    .profile(profile)
                    .login("scan")
                    .password(passwordEncoder.encode("scan@2026"))
                    .status(true)
                    .firstAttempt(false)
                    .build()
            );
        }
    }

    private void initOrganigramme(DirectionRepository directionRepository) {
        var directionDG = directionRepository.findByCode("DGPSN");
//     log.info("directionDG {}", directionDG);
        if (directionDG.isEmpty()) {
            var dgpsn = DirectionEntity.builder()
                    .code("DGPSN")
                    .libelle("CONSEIL D'ORIENTATION")
                    .parent(null)
                    .build();

            var dg = DirectionEntity.builder()
                    .code("DG")
                    .libelle("Delegue General")
                    .build();
            dg.addChildren(List.of(
                    DirectionEntity.builder()
                            .code("AI")
                            .libelle("Auditeur Interne")
                            .build(),
                    DirectionEntity.builder()
                            .code("CTPS")
                            .libelle("Conseil Technique en charges des Politiques de Protection Social")
                            .build()
            ));

            directionRepository.save(dgpsn);
        }
    }

}
