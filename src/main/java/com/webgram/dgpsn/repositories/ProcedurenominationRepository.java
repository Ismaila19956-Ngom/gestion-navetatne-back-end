package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.ProcedurenominationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface ProcedurenominationRepository extends JpaRepository<ProcedurenominationEntity, Long>, QuerydslPredicateExecutor<ProcedurenominationEntity> {
}