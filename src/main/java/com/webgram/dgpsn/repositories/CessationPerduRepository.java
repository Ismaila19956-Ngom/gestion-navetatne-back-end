package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.CessationPerduEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface CessationPerduRepository extends JpaRepository<CessationPerduEntity, Long>, QuerydslPredicateExecutor<CessationPerduEntity> {
    @Query("SELECT c FROM CessationPerduEntity c WHERE c.conge.id = :congeId")
    List<CessationPerduEntity> findByCongeId(@Param("congeId") Long congeId);



}