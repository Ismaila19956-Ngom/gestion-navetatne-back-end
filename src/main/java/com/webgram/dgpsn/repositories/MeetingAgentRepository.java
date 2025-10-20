package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.*;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface MeetingAgentRepository extends JpaRepository<MeetingAgentEntity, Long>, QuerydslPredicateExecutor<MeetingAgentEntity> {
    boolean existsByMeetingId(Long meetingId);

    void deleteByMeetingId(Long id);


    @Override
    Optional<MeetingAgentEntity> findById(Long meetingAgentId);
    List<MeetingAgentEntity>findByMeetingId(Long meetingAgentId);

    default Page<MeetingAgentEntity> readAllByFiltering(
            Pageable pageable,
            String libelle,
            Date predicatedDate,
            Date readDate,
            String comment,
            Time heureDebutPrevue,
            Time heureFinPrevue,
            Time heureDebutReelle,
            Time heureFinReelle,
            Long meetingTypeId,
            Long projetId,
            Long meetingId,
            List<Long> agentIds) {
        
        var booleanBuilder = new BooleanBuilder();

        if(Objects.nonNull(libelle)) {
            booleanBuilder.and(QMeetingAgentEntity.meetingAgentEntity.meeting.libelle.containsIgnoreCase(libelle));
        }
        if(Objects.nonNull(predicatedDate)) {
            booleanBuilder.and(QMeetingAgentEntity.meetingAgentEntity.meeting.predicatedDate.eq(predicatedDate));
        }
        if(Objects.nonNull(readDate)) {
            booleanBuilder.and(QMeetingAgentEntity.meetingAgentEntity.meeting.readDate.eq(readDate));
        }
        if (StringUtils.isNotEmpty(comment)) {
            booleanBuilder.and(QMeetingAgentEntity.meetingAgentEntity.meeting.comment.containsIgnoreCase(comment));
        }
        if(Objects.nonNull(heureDebutPrevue)) {
            booleanBuilder.and(QMeetingAgentEntity.meetingAgentEntity.meeting.heureDebutPrevue.eq(heureDebutPrevue));
        }
        if(Objects.nonNull(heureFinPrevue)) {
            booleanBuilder.and(QMeetingAgentEntity.meetingAgentEntity.meeting.heureFinPrevue.eq(heureFinPrevue));
        }
        if(Objects.nonNull(heureDebutReelle)) {
            booleanBuilder.and(QMeetingAgentEntity.meetingAgentEntity.meeting.heureDebutReelle.eq(heureDebutReelle));
        }
        if(Objects.nonNull(heureFinReelle)) {
            booleanBuilder.and(QMeetingAgentEntity.meetingAgentEntity.meeting.heureFinReelle.eq(heureFinReelle));
        }
        if(Objects.nonNull(meetingTypeId)) {
            booleanBuilder.and(QMeetingAgentEntity.meetingAgentEntity.meeting.meetingType.id.eq(meetingTypeId));
        }
        if(Objects.nonNull(projetId)) {
            booleanBuilder.and(QMeetingAgentEntity.meetingAgentEntity.meeting.projet.id.eq(projetId));
        }
        if(Objects.nonNull(meetingId)){
            booleanBuilder.and(QMeetingAgentEntity.meetingAgentEntity.meeting.id.eq(meetingId));
        }
        if(Objects.nonNull(agentIds)){
            booleanBuilder.and(QMeetingAgentEntity.meetingAgentEntity.agent.id.in(agentIds));
        }

        return findAll(booleanBuilder, pageable);
    }

    Optional<List<MeetingAgentEntity>> findAllByMeeting(MeetingEntity meeting);

//    void deleteByAgent(AgentEntity agent);
}
