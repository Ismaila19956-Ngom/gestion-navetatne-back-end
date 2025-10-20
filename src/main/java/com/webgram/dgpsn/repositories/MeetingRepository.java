package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.MeetingEntity;
import com.webgram.dgpsn.entities.QMeetingEntity;


import java.sql.Time;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface MeetingRepository extends JpaRepository<MeetingEntity, Long>, QuerydslPredicateExecutor<MeetingEntity> {
    Optional<MeetingEntity> findById(Long projetId);
    default Page<MeetingEntity> readAllByFiltering(
            Pageable pageable,
            String libelle,
            Date predicatedDate,
            Date readDate,
            String comment,
            Time heureDebutPrevue,
            Time heureFinPrevue,
            Time heureDebutReelle,
            Time heureFinReelle,
//            Long agentId,
            Long meetingTypeId,
            Long projetId

    ) {
        var booleanBuider = new BooleanBuilder();

        if (Objects.nonNull(libelle)) {
            booleanBuider.and(QMeetingEntity.meetingEntity.libelle.containsIgnoreCase(libelle));
        }
        if (Objects.nonNull(predicatedDate)) {
            booleanBuider.and(QMeetingEntity.meetingEntity.predicatedDate.eq(predicatedDate));
        }
        if (Objects.nonNull(readDate)) {
            booleanBuider.and(QMeetingEntity.meetingEntity.readDate.eq(readDate));
        }
        if (StringUtils.isNotEmpty(comment)) {
            booleanBuider.and(QMeetingEntity.meetingEntity.comment.containsIgnoreCase(comment));
     }
            if (Objects.nonNull(heureDebutPrevue)) {
                booleanBuider.and(QMeetingEntity.meetingEntity.heureDebutPrevue.eq(heureDebutPrevue));
            }
            if (Objects.nonNull(heureFinPrevue)) {
                booleanBuider.and(QMeetingEntity.meetingEntity.heureFinPrevue.eq(heureFinPrevue));
            }
            if (Objects.nonNull(heureDebutReelle)) {
                booleanBuider.and(QMeetingEntity.meetingEntity.heureDebutReelle.eq(heureDebutReelle));
            }
            if (Objects.nonNull(heureFinReelle)) {
                booleanBuider.and(QMeetingEntity.meetingEntity.heureFinReelle.eq(heureFinReelle));
            }

//        if(Objects.nonNull(agentId)) {
//            booleanBuider.and(QMeetingEntity.meetingEntity.agent.id.eq(agentId));
//        }
            if (Objects.nonNull(meetingTypeId)) {
                booleanBuider.and(QMeetingEntity.meetingEntity.meetingType.id.eq(meetingTypeId));
            }
            if (Objects.nonNull(projetId)) {
                booleanBuider.and(QMeetingEntity.meetingEntity.projet.id.eq(projetId));
            }

            return findAll(booleanBuider, pageable);
        }

}
