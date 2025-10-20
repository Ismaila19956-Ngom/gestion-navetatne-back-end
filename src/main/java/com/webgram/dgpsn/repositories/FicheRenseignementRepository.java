package com.webgram.dgpsn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.FicheRenseignementEntity;

@Repository
public interface FicheRenseignementRepository extends JpaRepository<FicheRenseignementEntity, Long>, QuerydslPredicateExecutor<FicheRenseignementEntity> {
}