package sn.naavetane.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.naavetane.backend.entities.UserEntity;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.naavetane.backend.entities.QUserEntity;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, QuerydslPredicateExecutor<UserEntity> {
    Optional<UserEntity> findByLogin(String login);
    Optional<UserEntity> findByAgentEmailIgnoreCase(String email);
    Optional<UserEntity> findById(Long id);

    default Page<UserEntity> readAllByFilters(Pageable pageable, Long structureId, String login, Long agentId, Boolean status, Long profileId, List<String> loginToExcludes) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (structureId != null) {
            booleanBuilder.and(QUserEntity.userEntity.structure.id.eq(structureId));
        }
        if (StringUtils.isNotEmpty(login)) {
            booleanBuilder.and(QUserEntity.userEntity.login.containsIgnoreCase(login));
        }
        if (agentId != null) {
            booleanBuilder.and(QUserEntity.userEntity.agent.id.eq(agentId));
        }
        if (status != null) {
            booleanBuilder.and(QUserEntity.userEntity.status.eq(status));
        }
        if (profileId != null) {
            booleanBuilder.and(QUserEntity.userEntity.profile.id.eq(profileId));
        }
        if (loginToExcludes != null && !loginToExcludes.isEmpty()) {
            booleanBuilder.and(QUserEntity.userEntity.login.notIn(loginToExcludes));
        }

        return findAll(booleanBuilder, pageable);
    }
}
