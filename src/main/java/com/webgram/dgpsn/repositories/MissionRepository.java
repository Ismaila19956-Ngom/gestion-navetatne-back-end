package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.MissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends JpaRepository<MissionEntity, Long>, QuerydslPredicateExecutor<MissionEntity>, JpaSpecificationExecutor<MissionEntity> {
}