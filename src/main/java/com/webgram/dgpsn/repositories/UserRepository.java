package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import com.webgram.dgpsn.entities.ProfileEntity;
import com.webgram.dgpsn.entities.QUserEntity;
import com.webgram.dgpsn.entities.UserEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>, QuerydslPredicateExecutor<UserEntity> {
    Optional<UserEntity> findByLogin(String login);
    List<UserEntity> findByProfile(ProfileEntity profile);
    default Page<UserEntity> readAllByFilters(Pageable pageable, Long structureId, String login,  Long agentId, Boolean status, Long profileId, List<String> loginToExcludes) {
        var booleanBuilder = new BooleanBuilder();
        if(Objects.nonNull(login)){
            booleanBuilder.and(QUserEntity.userEntity.login.containsIgnoreCase(login));
        }
        if(Objects.nonNull(agentId)){
            booleanBuilder.and(QUserEntity.userEntity.agent.id.eq(agentId));
        }

        if(Objects.nonNull(status)){
            booleanBuilder.and(QUserEntity.userEntity.status.eq(status));
        }
        if(Objects.nonNull(structureId)){
            booleanBuilder.and(QUserEntity.userEntity.agent.structure.id.eq(structureId));
        }
        if(Objects.nonNull(profileId)){
            booleanBuilder.and(QUserEntity.userEntity.profile.id.eq(profileId));
        }
        if(Objects.nonNull(loginToExcludes) && !loginToExcludes.isEmpty()){
            booleanBuilder.and(QUserEntity.userEntity.login.notIn(loginToExcludes));
        }
        return findAll(booleanBuilder, pageable);
    }

    Optional<UserEntity> findByAgentEmailIgnoreCase(String email);
}
