package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.ParticipantagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface ParticipantagRepository extends JpaRepository<ParticipantagEntity, Long>, QuerydslPredicateExecutor<ParticipantagEntity> {
    List<ParticipantagEntity> findByAssemblegeneralId(Long assemblegeneralId);
}