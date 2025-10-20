package com.webgram.dgpsn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.MilieuxPollutiontEntity;
import com.webgram.dgpsn.models.responses.DataPoint;

import java.util.Date;
import java.util.List;

;


@Repository
public interface MilieuxPollutionRepository extends JpaRepository<MilieuxPollutiontEntity, Long>, QuerydslPredicateExecutor<MilieuxPollutiontEntity> {

    @Query("SELECT COUNT(m) FROM MilieuxPollutiontEntity m WHERE m.datePrelevement >= :sinceDate AND m.turbidite > 50")
    Long countAlertesSince(Date sinceDate);

    @Query("SELECT new com.webgram.dgpsn.models.responses.DataPoint(FUNCTION('DATE_FORMAT', m.datePrelevement, '%Y-%m'), AVG(m.turbidite)) " +
            "FROM MilieuxPollutiontEntity m WHERE m.datePrelevement >= :oneYearAgo " +
            "GROUP BY FUNCTION('DATE_FORMAT', m.datePrelevement, '%Y-%m') " +
            "ORDER BY FUNCTION('DATE_FORMAT', m.datePrelevement, '%Y-%m')")
    List<DataPoint<String, Double>> findTendanceTurbidite(Date oneYearAgo);
}
