package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.TypeagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface TypeagRepository extends JpaRepository<TypeagEntity, Long>, QuerydslPredicateExecutor<TypeagEntity> {
}