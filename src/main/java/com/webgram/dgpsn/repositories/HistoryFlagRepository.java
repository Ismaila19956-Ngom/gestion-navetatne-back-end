package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.HistoryFlagEntity;
import com.webgram.dgpsn.entities.QHistoryFlagEntity;
import com.webgram.dgpsn.models.responses.StatisticalDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public interface HistoryFlagRepository extends JpaRepository<HistoryFlagEntity, Long>, QuerydslPredicateExecutor<HistoryFlagEntity> {

    default Page<HistoryFlagEntity> readAllByFiltering(Pageable pageable, String startDate, String endDate, Long flagId, Long projetId) throws ParseException {
        var booleanBuider = new BooleanBuilder();

        if(Objects.nonNull(startDate)) {
            var formatStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);

            booleanBuider.and(QHistoryFlagEntity.historyFlagEntity.startDate.eq(formatStartDate));
        }
        if(Objects.nonNull(endDate)) {
            var formatStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
            booleanBuider.and(QHistoryFlagEntity.historyFlagEntity.endDate.eq(formatStartDate));
        }
        if(Objects.nonNull(flagId)) {
            booleanBuider.and(QHistoryFlagEntity.historyFlagEntity.flag.id.eq(flagId));
        }
        if(Objects.nonNull(projetId)) {
            booleanBuider.and(QHistoryFlagEntity.historyFlagEntity.projet.id.eq(projetId));
        }

        return findAll(booleanBuider, pageable);
    }

    default Long getNotClosed(Long projectId, Long historyId) {
        var booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(QHistoryFlagEntity.historyFlagEntity.projet.id.eq(projectId));
        if(Objects.nonNull(historyId)) {
            booleanBuilder.and(QHistoryFlagEntity.historyFlagEntity.id.ne(historyId));
        }
        booleanBuilder.and(QHistoryFlagEntity.historyFlagEntity.endDate.isNull());

        return count(booleanBuilder);

    }
    default Long validPeriod(Long projectId, Long historyId, Date startDate, Date endDate) {
        var booleanBuilderStartDate = new BooleanBuilder();
        var booleanBuilderEndDate = new BooleanBuilder();
        var booleanBuilder = new BooleanBuilder();
        var booleanBuilderEnglob = new BooleanBuilder();
        booleanBuilder.and(QHistoryFlagEntity.historyFlagEntity.projet.id.eq(projectId));
        if(Objects.nonNull(historyId)) {
            booleanBuilder.and(QHistoryFlagEntity.historyFlagEntity.id.ne(historyId));
        }
        if(Objects.nonNull(startDate)) {
            booleanBuilderStartDate.and(QHistoryFlagEntity.historyFlagEntity.startDate.loe(startDate));
            booleanBuilderStartDate.and(QHistoryFlagEntity.historyFlagEntity.endDate.goe(startDate));
            booleanBuilderStartDate.and(QHistoryFlagEntity.historyFlagEntity.projet.id.eq(projectId));
            if(Objects.nonNull(historyId)) {
                booleanBuilderStartDate.and(QHistoryFlagEntity.historyFlagEntity.id.ne(historyId));
            }
        }
        if(Objects.nonNull(endDate)) {
            booleanBuilderEndDate.and(QHistoryFlagEntity.historyFlagEntity.startDate.loe(endDate));
            booleanBuilderEndDate.and(QHistoryFlagEntity.historyFlagEntity.endDate.goe(endDate));
            booleanBuilderEndDate.and(QHistoryFlagEntity.historyFlagEntity.projet.id.eq(projectId));
            if(Objects.nonNull(historyId)) {
                booleanBuilderEndDate.and(QHistoryFlagEntity.historyFlagEntity.id.ne(historyId));
            }
        }
        if(Objects.nonNull(endDate) && Objects.nonNull(startDate)){
            booleanBuilderEnglob.and(QHistoryFlagEntity.historyFlagEntity.startDate.goe(startDate));
            booleanBuilderEnglob.and(QHistoryFlagEntity.historyFlagEntity.endDate.loe(endDate));
            booleanBuilderEnglob.and(QHistoryFlagEntity.historyFlagEntity.projet.id.eq(projectId));
            if(Objects.nonNull(historyId)) {
                booleanBuilderEnglob.and(QHistoryFlagEntity.historyFlagEntity.id.ne(historyId));
            }
        }else if(!Objects.nonNull(endDate)){
            booleanBuilderEnglob.and(QHistoryFlagEntity.historyFlagEntity.startDate.goe(startDate));
            booleanBuilderEnglob.and(QHistoryFlagEntity.historyFlagEntity.projet.id.eq(projectId));
            if(Objects.nonNull(historyId)) {
                booleanBuilderEnglob.and(QHistoryFlagEntity.historyFlagEntity.id.ne(historyId));
            }
        }
        booleanBuilder.and(booleanBuilderStartDate.or(booleanBuilderEndDate).or(booleanBuilderEnglob));

        return count(booleanBuilder);
    }
    default Long getLast(Long projectId, Long historyId, Date startDate) {
        var booleanBuilder = new BooleanBuilder();
        if(Objects.nonNull(historyId)) {
            booleanBuilder.and(QHistoryFlagEntity.historyFlagEntity.id.ne(historyId));
        }
        booleanBuilder.and(QHistoryFlagEntity.historyFlagEntity.projet.id.eq(projectId));
        booleanBuilder.and(QHistoryFlagEntity.historyFlagEntity.endDate.goe(startDate));
        return count(booleanBuilder);
    }
    default HistoryFlagEntity getLastEtat(Long projectId, Date startDate) {
        var booleanBuilder = new BooleanBuilder();
        PageRequest pageRequest = PageRequest.of(0,1, Sort.by("startDate").descending());
        booleanBuilder.and(QHistoryFlagEntity.historyFlagEntity.projet.id.eq(projectId));
//        booleanBuilder.and(QHistoryFlagEntity.historyFlagEntity.endDate.goe(startDate));
        var etats = findAll(booleanBuilder,pageRequest);
        if(etats.getContent().isEmpty()) {
            return null;
        }
        return etats.getContent().get(0);
    }

    @Query("SELECT new com.webgram.dgpsn.models.responses.StatisticalDTO(h.flag.code, COUNT(h.flag.code)) FROM HistoryFlagEntity h GROUP BY h.flag.code")
    List<StatisticalDTO> countByFlagCode();

    List<HistoryFlagEntity> findHistoryFlagEntityByFlagCode(String code);

}
