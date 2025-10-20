package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.HistoryStatusEntity;
import com.webgram.dgpsn.entities.QHistoryStatusEntity;
import com.webgram.dgpsn.entities.enums.StatusType;
import com.webgram.dgpsn.models.responses.StatisticalDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public interface HistoryStatusRepository extends JpaRepository<HistoryStatusEntity, Long>, QuerydslPredicateExecutor<HistoryStatusEntity> {

    default Page<HistoryStatusEntity> readAllByFiltering(Pageable pageable, String startDate, String endDate, Long statusId, Long projetId) throws ParseException {
        var booleanBuider = new BooleanBuilder();

        if(Objects.nonNull(startDate)) {
            var formatStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);

            booleanBuider.and(QHistoryStatusEntity.historyStatusEntity.startDate.eq(formatStartDate));
        }
        if(Objects.nonNull(endDate)) {
            var formatStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);

            booleanBuider.and(QHistoryStatusEntity.historyStatusEntity.endDate.eq(formatStartDate));
        }
        if(Objects.nonNull(statusId)) {
            booleanBuider.and(QHistoryStatusEntity.historyStatusEntity.status.id.eq(statusId));
        }
        if(Objects.nonNull(projetId)) {
            booleanBuider.and(QHistoryStatusEntity.historyStatusEntity.projet.id.eq(projetId));
        }

        return findAll(booleanBuider, pageable);
    }

    default Long getNotClosed(Long projectId, Long historyId) {
        var booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(QHistoryStatusEntity.historyStatusEntity.projet.id.eq(projectId));
        if(Objects.nonNull(historyId)) {
            booleanBuilder.and(QHistoryStatusEntity.historyStatusEntity.id.ne(historyId));
        }
        booleanBuilder.and(QHistoryStatusEntity.historyStatusEntity.endDate.isNull());

        return count(booleanBuilder);

    }
    default Long validPeriod(Long projectId, Long historyId, Date startDate, Date endDate) {
        var booleanBuilderStartDate = new BooleanBuilder();
        var booleanBuilderEndDate = new BooleanBuilder();
        var booleanBuilder = new BooleanBuilder();
        var booleanBuilderEnglob = new BooleanBuilder();
        booleanBuilder.and(QHistoryStatusEntity.historyStatusEntity.projet.id.eq(projectId));
        if(Objects.nonNull(historyId)) {
            booleanBuilder.and(QHistoryStatusEntity.historyStatusEntity.id.ne(historyId));
        }
        if(Objects.nonNull(startDate)) {
            booleanBuilderStartDate.and(QHistoryStatusEntity.historyStatusEntity.startDate.loe(startDate));
            booleanBuilderStartDate.and(QHistoryStatusEntity.historyStatusEntity.endDate.goe(startDate));
            booleanBuilderStartDate.and(QHistoryStatusEntity.historyStatusEntity.projet.id.eq(projectId));
            if(Objects.nonNull(historyId)) {
                booleanBuilderStartDate.and(QHistoryStatusEntity.historyStatusEntity.id.ne(historyId));
            }
        }
        if(Objects.nonNull(endDate)) {
            booleanBuilderEndDate.and(QHistoryStatusEntity.historyStatusEntity.startDate.loe(endDate));
            booleanBuilderEndDate.and(QHistoryStatusEntity.historyStatusEntity.endDate.goe(endDate));
            booleanBuilderEndDate.and(QHistoryStatusEntity.historyStatusEntity.projet.id.eq(projectId));
            if(Objects.nonNull(historyId)) {
                booleanBuilderEndDate.and(QHistoryStatusEntity.historyStatusEntity.id.ne(historyId));
            }
        }
        if(Objects.nonNull(endDate) && Objects.nonNull(startDate)){
            booleanBuilderEnglob.and(QHistoryStatusEntity.historyStatusEntity.startDate.goe(startDate));
            booleanBuilderEnglob.and(QHistoryStatusEntity.historyStatusEntity.endDate.loe(endDate));
            booleanBuilderEnglob.and(QHistoryStatusEntity.historyStatusEntity.projet.id.eq(projectId));
            if(Objects.nonNull(historyId)) {
                booleanBuilderEnglob.and(QHistoryStatusEntity.historyStatusEntity.id.ne(historyId));
            }
        }else if(!Objects.nonNull(endDate)){
            booleanBuilderEnglob.and(QHistoryStatusEntity.historyStatusEntity.startDate.goe(startDate));
            booleanBuilderEnglob.and(QHistoryStatusEntity.historyStatusEntity.projet.id.eq(projectId));
            if(Objects.nonNull(historyId)) {
                booleanBuilderEnglob.and(QHistoryStatusEntity.historyStatusEntity.id.ne(historyId));
            }
        }
        booleanBuilder.and(booleanBuilderStartDate.or(booleanBuilderEndDate).or(booleanBuilderEnglob));

        return count(booleanBuilder);
    }

    default Long getLast(Long projectId, Long historyId, Date startDate) {
        var booleanBuilder = new BooleanBuilder();
        if(Objects.nonNull(historyId)) {
            booleanBuilder.and(QHistoryStatusEntity.historyStatusEntity.id.ne(historyId));
        }
        booleanBuilder.and(QHistoryStatusEntity.historyStatusEntity.projet.id.eq(projectId));
        booleanBuilder.and(QHistoryStatusEntity.historyStatusEntity.endDate.goe(startDate));
        return count(booleanBuilder);
    }
    default HistoryStatusEntity getLastStatut(Long projectId, Date startDate) {
        var booleanBuilder = new BooleanBuilder();
        PageRequest pageRequest = PageRequest.of(0,1, Sort.by("startDate").descending());
        booleanBuilder.and(QHistoryStatusEntity.historyStatusEntity.projet.id.eq(projectId));
//        booleanBuilder.and(QHistoryStatusEntity.historyStatusEntity.endDate.goe(startDate));
        var statuts = findAll(booleanBuilder,pageRequest);
        if(statuts.getContent().isEmpty()) {
            return null;
        }
        return statuts.getContent().get(0);
    }

    @Query("SELECT new com.webgram.dgpsn.models.responses.StatisticalDTO(h.status.libelle, COUNT(h.status.code)) FROM HistoryStatusEntity h WHERE h.status.statusType = :statusType GROUP BY h.status.libelle, h.status.code")
    List<StatisticalDTO> countByStatusCode(@Param("statusType") StatusType statusType);

    List<HistoryStatusEntity> findHistoryStatusEntityByStatusStatusTypeAndAndStatusLibelle(StatusType statusType, String libelle);
}
