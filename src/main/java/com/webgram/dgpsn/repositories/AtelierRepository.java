package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.AtelierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AtelierRepository extends JpaRepository<AtelierEntity, Long>, QuerydslPredicateExecutor<AtelierEntity>, JpaSpecificationExecutor<AtelierEntity> {
}