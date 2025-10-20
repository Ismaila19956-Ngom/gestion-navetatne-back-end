package com.webgram.dgpsn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.PollutiontManagerEntity;
import com.webgram.dgpsn.models.responses.DataPoint;

import java.util.Date;
import java.util.List;

;


@Repository
public interface PollutionManagerRepository extends JpaRepository<PollutiontManagerEntity, Long>, QuerydslPredicateExecutor<PollutiontManagerEntity> {

    @Query("SELECT COALESCE(SUM(p.quantite), 0.0) FROM PollutiontManagerEntity p WHERE YEAR(p.dateMouvement) = :year")
    Double sumQuantiteByYear(int year);

    @Query("SELECT new com.webgram.dgpsn.models.responses.DataPoint(FUNCTION('DATE_FORMAT', p.dateMouvement, '%Y-%m'), SUM(p.quantite)) " +
            "FROM PollutiontManagerEntity p WHERE p.dateMouvement >= :oneYearAgo " +
            "GROUP BY FUNCTION('DATE_FORMAT', p.dateMouvement, '%Y-%m') " +
            "ORDER BY FUNCTION('DATE_FORMAT', p.dateMouvement, '%Y-%m')")
    List<DataPoint<String, Double>> sumVolumeDechetsMensuel(Date oneYearAgo);

    @Query("SELECT new com.webgram.dgpsn.models.responses.DataPoint(CAST(p.typePollution AS string), COUNT(p)) " +
            "FROM PollutiontManagerEntity p " +
            "GROUP BY p.typePollution")
    List<DataPoint<String, Long>> countByTypePollution();
}
