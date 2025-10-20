package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.NaturedepenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface NaturedepenseRepository extends JpaRepository<NaturedepenseEntity, Long>, QuerydslPredicateExecutor<NaturedepenseEntity> {
}