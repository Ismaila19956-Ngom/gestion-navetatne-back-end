package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.EntrepriseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface EntrepriseRepository extends JpaRepository<EntrepriseEntity, Long>, QuerydslPredicateExecutor<EntrepriseEntity> {
}