package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.SecteuractiviteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface SecteuractiviteRepository extends JpaRepository<SecteuractiviteEntity, Long>, QuerydslPredicateExecutor<SecteuractiviteEntity> {
}