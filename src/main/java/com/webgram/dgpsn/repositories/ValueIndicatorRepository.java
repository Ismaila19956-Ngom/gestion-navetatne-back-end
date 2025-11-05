package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.IndicatorProjetEntity;
import com.webgram.dgpsn.entities.QValueIndicatorEntity;
import com.webgram.dgpsn.entities.ValueIndicatorEntity;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface ValueIndicatorRepository extends JpaRepository<ValueIndicatorEntity, Long>, QuerydslPredicateExecutor<ValueIndicatorEntity> {

    default Page<ValueIndicatorEntity> readAllByFilters(Pageable pageable,  Long projetId, Long activityId, Long indicatorId
        , String period, Double targetValue, Double valueReched, Date startDate, Date endDate) {
        var booleanBuilder = new BooleanBuilder();

        if(Objects.nonNull(indicatorId)){
            booleanBuilder.and(QValueIndicatorEntity.valueIndicatorEntity.indicatorProjet.indicator.id.eq(indicatorId));
        }
        if(Objects.nonNull(targetValue)){
            booleanBuilder.and(QValueIndicatorEntity.valueIndicatorEntity.targetValue.eq(targetValue));
        }
        if(Objects.nonNull(valueReched)){
            booleanBuilder.and(QValueIndicatorEntity.valueIndicatorEntity.valueReched.eq(valueReched));
        }
        if(Objects.nonNull(projetId)){
            booleanBuilder.and(QValueIndicatorEntity.valueIndicatorEntity.projet.id.eq(projetId));
        }
        if(Objects.nonNull(activityId)){
            booleanBuilder.and(QValueIndicatorEntity.valueIndicatorEntity.activity.id.eq(activityId));
        }
        if(Objects.nonNull(startDate)){
            booleanBuilder.and(QValueIndicatorEntity.valueIndicatorEntity.startDate.eq(startDate));
        }
        if(Objects.nonNull(endDate)){
            booleanBuilder.and(QValueIndicatorEntity.valueIndicatorEntity.endDate.eq(endDate));
        }
        return findAll(booleanBuilder, pageable);
    }

    List<ValueIndicatorEntity> findByActivityId(Long activityId);

    Optional<List<ValueIndicatorEntity>> findByIndicatorProjet(IndicatorProjetEntity indicatorProjet);

    /**
     * Trouve toutes les valeurs d'indicateur pour un projet
     */
    @Query("SELECT v FROM ValueIndicatorEntity v WHERE v.projet.id = :projetId")
    List<ValueIndicatorEntity> findByProjetId(@Param("projetId") Long projetId);

    /**
     * Trouve toutes les valeurs d'indicateur pour une activité
     */
    @Query("SELECT v FROM ValueIndicatorEntity v WHERE v.activity.id = :activityId")
    List<ValueIndicatorEntity> findByActivityIdPerso(@Param("activityId") Long activityId);

    /**
     * Trouve toutes les valeurs d'indicateur pour un indicateur de projet
     */
    @Query("SELECT v FROM ValueIndicatorEntity v WHERE v.indicatorProjet.id = :indicatorProjetId")
    List<ValueIndicatorEntity> findByIndicatorProjetId(@Param("indicatorProjetId") Long indicatorProjetId);

    /**
     * Trouve toutes les valeurs d'indicateur pour une année donnée
     */
    @Query("SELECT v FROM ValueIndicatorEntity v WHERE v.year = :year")
    List<ValueIndicatorEntity> findByYear(@Param("year") Integer year);

    /**
     * Trouve toutes les valeurs d'indicateur pour une liste d'activités en une seule requête.
     */
    @Query("SELECT v FROM ValueIndicatorEntity v WHERE v.activity.id IN :activityIds")
    List<ValueIndicatorEntity> findByActivityIdIn(@Param("activityIds") List<Long> activityIds);
}
