package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.ParticipantMissionEntity;
import com.webgram.dgpsn.entities.QParticipantMissionEntity;

import java.util.Objects;

@Repository
public interface ParticipantMissionRepository extends JpaRepository<ParticipantMissionEntity, Long>, QuerydslPredicateExecutor<ParticipantMissionEntity> {

    default Page<ParticipantMissionEntity> readAllByFiltering(Pageable pageable, Long assignmentId, Long actorId, Long roleId) {
        var booleanBuider = new BooleanBuilder();

        if(Objects.nonNull(assignmentId)) {
            booleanBuider.and(QParticipantMissionEntity.participantMissionEntity.assignment.id.eq(assignmentId));
        }
        if(Objects.nonNull(actorId)) {
            booleanBuider.and(QParticipantMissionEntity.participantMissionEntity.actor.id.eq(actorId));
        }
        if(Objects.nonNull(roleId)) {
            booleanBuider.and(QParticipantMissionEntity.participantMissionEntity.role.id.eq(roleId));
        }

        return findAll(booleanBuider, pageable);
    }
}
