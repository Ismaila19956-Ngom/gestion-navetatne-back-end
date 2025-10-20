package com.webgram.dgpsn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.RejetPollutiontEntity;

import java.util.Date;


@Repository
public interface RejetPollutionRepository extends JpaRepository<RejetPollutiontEntity, Long>, QuerydslPredicateExecutor<RejetPollutiontEntity> {
    @Query("SELECT COUNT(r) FROM RejetPollutiontEntity r WHERE r.datePrelevement >= :sinceDate AND (r.ph < 6 OR r.ph > 9 OR r.dco > 125)")
    Long countNonConformitesSince(Date sinceDate);
}
