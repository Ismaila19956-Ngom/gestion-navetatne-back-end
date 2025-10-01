package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.AgentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<AgentEntity, Long>, QuerydslPredicateExecutor<AgentEntity> {

}
