//package sn.naavetane.backend.services.Impl;// package sn.webg.suivievaluation.services.Impl;
//
//import sn.naavetane.backend.annotations.Journal;
//import sn.naavetane.backend.entities.TemporaryAccessCodeEntity;
//import sn.naavetane.backend.entities.UserEntity;
//import sn.naavetane.backend.exceptions.UserDisabledException;
//import sn.naavetane.backend.models.TemporaryAccessCodeDTO;
//import sn.naavetane.backend.models.responses.SignInAuthentication;
//import sn.naavetane.backend.repositories.TemporaryAccessCodeRepository;
//import sn.naavetane.backend.repositories.UserRepository;
//import sn.naavetane.backend.security.jwt.TokenProvider;
//import sn.naavetane.backend.services.TemporaryAccessCodeService;
//import sn.naavetane.backend.tools.ActionType;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.text.MessageFormat;
//import java.time.LocalDateTime;
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class TemporaryAccessCodeServiceImpl implements TemporaryAccessCodeService {
//
//    private final UserRepository userRepository;
//    private final TemporaryAccessCodeRepository temporaryAccessCodeRepository;
//    private final TokenProvider tokenProvider;
//
//    // Vous pouvez injecter JwtProperties pour la durée de validité par défaut si nécessaire
//    // ou la passer en paramètre
//
//    @Override
//    @Transactional
//    @Journal(actionType = ActionType.GENERATE_TEMPORARY_CODE)
//    public String generateTemporaryCode(String username, int validityInMinutes) {
//        UserEntity user = userRepository.findByLogin(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + username));
//
//        if (Boolean.FALSE.equals(user.getStatus())) {
//            throw new UserDisabledException(MessageFormat.format(TokenProvider.ACCOUNT_LOCKED, username));
//        }
//
//        // Optionnel: invalider les anciens codes actifs pour cet utilisateur
//        temporaryAccessCodeRepository.findByUserAndActiveTrueAndExpiryDateAfter(user, LocalDateTime.now())
//            .ifPresent(existingCode -> {
//                existingCode.setActive(false);
//                temporaryAccessCodeRepository.save(existingCode);
//                log.info("Ancien code temporaire {} pour l'utilisateur {} a été désactivé.", existingCode.getCode(), username);
//            });
//
//
//        String code = RandomStringUtils.randomAlphanumeric(8).toUpperCase(); // Génère un code de 8 caractères alphanumériques
//        // Assurez-vous que le code est unique, bien que la probabilité de collision soit faible avec 8 caractères.
//        // Pour une robustesse maximale, vous pourriez boucler et régénérer si une collision se produit,
//        // mais c'est généralement excessif pour cette longueur.
//
//        TemporaryAccessCodeEntity tempCodeEntity = new TemporaryAccessCodeEntity();
//        tempCodeEntity.setUser(user);
//        tempCodeEntity.setCode(code);
//        tempCodeEntity.setExpiryDate(LocalDateTime.now().plusMinutes(validityInMinutes));
//        tempCodeEntity.setActive(true);
//
//        temporaryAccessCodeRepository.save(tempCodeEntity);
//        log.info("Code temporaire {} généré pour l'utilisateur {} , valide jusqu'à {}", code, username, tempCodeEntity.getExpiryDate());
//
//        // Ici, vous enverriez typiquement le code à l'utilisateur (par email, SMS, etc.)
//        // Pour l'instant, nous le retournons simplement.
//        return code;
//    }
//
//    @Override
//    @Transactional
//    @Journal(actionType = ActionType.SIGN_TEMPORARY_CODE)
//    public SignInAuthentication signInWithTemporaryCode(String code) {
//        TemporaryAccessCodeEntity tempCodeEntity = temporaryAccessCodeRepository
//                .findByCodeAndActiveTrueAndExpiryDateAfter(code, LocalDateTime.now())
//                .orElseThrow(() -> new IllegalArgumentException("Code temporaire invalide ou expiré."));
//
//        UserEntity userEntity = tempCodeEntity.getUser();
//
//        if (Boolean.FALSE.equals(userEntity.getStatus())) {
////            tempCodeEntity.setActive(false); // Invalider le code même si le user est désactivé
//            temporaryAccessCodeRepository.save(tempCodeEntity);
//            // Utilisation de la constante publique ACCOUNT_LOCKED de TokenProvider
//            throw new UserDisabledException(MessageFormat.format(TokenProvider.ACCOUNT_LOCKED, userEntity.getLogin()));
//        }
//
//        // Préparer l'objet Authentication pour TokenProvider
//        Collection<? extends GrantedAuthority> authorities = userEntity.getProfile().getPermissions()
//                .stream()
//                .filter(permission -> permission != null && !permission.trim().isEmpty())
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//
//        User principal = new User(userEntity.getLogin(), "", authorities); // Le mot de passe n'est pas pertinent ici
//        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, code, authorities);
//
//        // Créer le token JWT en utilisant la date d'expiration du code
//        // On récupère la date d'expiration du code temporaire...
////        LocalDateTime expirationDateTime = tempCodeEntity.getExpiryDate();
////        String accessToken = tokenProvider.createTokenForTemporaryAccess(authentication, expirationDateTime);
//
//        LocalDateTime expirationDateTime = tempCodeEntity.getExpiryDate();
//        String accessToken = tokenProvider.createTokenForTemporaryAccess(authentication, expirationDateTime, code);
//
//
//        // Mettre à jour l'état en ligne de l'utilisateur
//        userEntity.setOnline(Boolean.TRUE);
//        userEntity.setLastConnexion(LocalDateTime.now());
//        userRepository.save(userEntity);
//
//        // Invalider le code temporaire après utilisation
////        tempCodeEntity.setActive(false);
//        temporaryAccessCodeRepository.save(tempCodeEntity);
//        log.info("Code temporaire {} utilisé avec succès par l'utilisateur {}.", code, userEntity.getLogin());
//
//        return SignInAuthentication.builder()
//                .tokenType(AuthenticationServiceImpl.TOKEN_TYPE) // "Bearer"
//                .accessToken(accessToken)
//                .build();
//    }
//
//    //getTemporaryCodes
//    @Override
//    @Transactional
//    @Journal(actionType = ActionType.READ_TEMPORARY_CODE)
//    public List<TemporaryAccessCodeDTO> getTemporaryCodes(String username) {
//        UserEntity userEntity = userRepository.findByLogin(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + username));
//
////        if (Boolean.FALSE.equals(userEntity.getStatus())) {
////            throw new UserDisabledException(MessageFormat.format(TokenProvider.ACCOUNT_LOCKED, username));
////        }
//
//        List<TemporaryAccessCodeEntity> temporaryAccessCodeEntities = temporaryAccessCodeRepository.findByUser_Login(username);
//
//        return temporaryAccessCodeEntities.stream()
//                .map(entity -> {
//                    // On détermine le statut "réel" du code
//                    boolean isEffectivelyActive = entity.isActive() && entity.getExpiryDate().isAfter(LocalDateTime.now());
//
//                    return TemporaryAccessCodeDTO.builder()
//                            .id(entity.getId()) // NOUVEAU : C'est bien de renvoyer l'ID pour les futures opérations
//                            .code(entity.getCode())
//                            .expiryDate(entity.getExpiryDate())
//                            .active(isEffectivelyActive) // On utilise le statut "réel"
//                            .createdAt(entity.getCreatedAt())
//                            .build();
//                })
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    @Transactional
//    public TemporaryAccessCodeDTO updateCodeStatus(String code, boolean active) {
//        log.info("Tentative de mise à jour du statut pour le code {} à {}", code, active);
//
//        // On utilise la nouvelle méthode du repository
//        TemporaryAccessCodeEntity tempCodeEntity = temporaryAccessCodeRepository.findByCode(code)
//                .orElseThrow(() -> new IllegalArgumentException("Aucun code temporaire trouvé avec la valeur: " + code));
//
//        tempCodeEntity.setActive(active);
//        TemporaryAccessCodeEntity updatedEntity = temporaryAccessCodeRepository.save(tempCodeEntity);
//
//        log.info("Le statut du code {} a été mis à jour à {}", code, active);
//
//        // On retourne le DTO mis à jour, en réutilisant la logique de statut "effectif"
//        boolean isEffectivelyActive = updatedEntity.isActive() && updatedEntity.getExpiryDate().isAfter(LocalDateTime.now());
//
//        return TemporaryAccessCodeDTO.builder()
//                .id(updatedEntity.getId())
//                .code(updatedEntity.getCode())
//                .expiryDate(updatedEntity.getExpiryDate())
//                .active(isEffectivelyActive)
//                .createdAt(updatedEntity.getCreatedAt())
//                .build();
//    }
//
//}
