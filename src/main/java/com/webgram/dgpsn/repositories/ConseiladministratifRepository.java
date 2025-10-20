package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.ConseiladministratifEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface ConseiladministratifRepository extends JpaRepository<ConseiladministratifEntity, Long>, QuerydslPredicateExecutor<ConseiladministratifEntity> {
    List<ConseiladministratifEntity> findByEntrepriseId(Long entrepriseId);
}