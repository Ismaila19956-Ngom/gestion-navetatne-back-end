package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.FormejuridiqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface FormejuridiqueRepository extends JpaRepository<FormejuridiqueEntity, Long>, QuerydslPredicateExecutor<FormejuridiqueEntity> {
}