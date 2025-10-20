package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.NaturedelarecetteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface NaturedelarecetteRepository extends JpaRepository<NaturedelarecetteEntity, Long>, QuerydslPredicateExecutor<NaturedelarecetteEntity> {
}