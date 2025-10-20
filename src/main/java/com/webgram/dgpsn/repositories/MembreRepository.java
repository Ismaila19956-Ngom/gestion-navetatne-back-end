package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.MembreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface MembreRepository extends JpaRepository<MembreEntity, Long>, QuerydslPredicateExecutor<MembreEntity> {
    List<MembreEntity> findByConseiladministratifId(Long conseiladministratifId);
}