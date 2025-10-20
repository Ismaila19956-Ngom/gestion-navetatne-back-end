package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.ActorProjetEntity;
import com.webgram.dgpsn.entities.QActorProjetEntity;

import java.util.List;
import java.util.Objects;

@Repository
public interface ActorProjetRepository extends JpaRepository<ActorProjetEntity, Long>, QuerydslPredicateExecutor<ActorProjetEntity> {

    List<ActorProjetEntity> findByProjetId(Long id);

    default Page<ActorProjetEntity> readAllByFiltering(Pageable pageable, Long projectId, Long agentId, Long roleId) {
        var booleanBuider = new BooleanBuilder();

        if(Objects.nonNull(projectId)) {
            booleanBuider.and(QActorProjetEntity.actorProjetEntity.projet.id.eq(projectId));
        }
        if(Objects.nonNull(agentId)) {
            booleanBuider.and(QActorProjetEntity.actorProjetEntity.agent.id.eq(agentId));
        }
        if(Objects.nonNull(roleId)) {
            booleanBuider.and(QActorProjetEntity.actorProjetEntity.role.id.eq(roleId));
        }

        return findAll(booleanBuider, pageable);
    }

    @Query("select ap.projet.id " +
            "from ActorProjetEntity ap where ap.agent.id = :actorId")
    List<Long> getProjectIdByActor(@Param("actorId") Long actorId);

}
