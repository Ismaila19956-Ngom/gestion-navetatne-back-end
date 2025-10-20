package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.TypereunionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface TypereunionRepository extends JpaRepository<TypereunionEntity, Long>, QuerydslPredicateExecutor<TypereunionEntity> {
}