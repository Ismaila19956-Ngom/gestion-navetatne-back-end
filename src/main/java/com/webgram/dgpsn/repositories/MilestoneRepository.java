package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.MilestoneEntity;
import com.webgram.dgpsn.entities.QMilestoneEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

@Repository
public interface MilestoneRepository extends JpaRepository<MilestoneEntity, Long>, QuerydslPredicateExecutor<MilestoneEntity> {

    List<MilestoneEntity> findByProjetId(Long id);

    default Page<MilestoneEntity> readAllByFiltering(
            Pageable pageable,
            String libelle,
            String predicatedDate,
            String readDate,
            Long projetId
    ) throws ParseException {
        var booleanBuider = new BooleanBuilder();

        if(Objects.nonNull(libelle)) {
            booleanBuider.and(QMilestoneEntity.milestoneEntity.libelle.containsIgnoreCase(libelle));
        }
        if(Objects.nonNull(predicatedDate)) {
            var formatPredicatedDate = new SimpleDateFormat("yyyy-MM-dd").parse(predicatedDate);
            booleanBuider.and(QMilestoneEntity.milestoneEntity.predicatedDate.eq(formatPredicatedDate));
        }
        if(Objects.nonNull(readDate)) {
            var formatReadDate = new SimpleDateFormat("yyyy-MM-dd").parse(readDate);
            booleanBuider.and(QMilestoneEntity.milestoneEntity.readDate.eq(formatReadDate));
        }
        if(Objects.nonNull(projetId)) {
            booleanBuider.and(QMilestoneEntity.milestoneEntity.projet.id.eq(projetId));
        }

        return findAll(booleanBuider, pageable);
    }
}
