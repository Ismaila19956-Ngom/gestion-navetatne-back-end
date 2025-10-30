package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.entities.QManagementUnitEntity;
import com.webgram.dgpsn.entities.enums.*;
import com.webgram.dgpsn.models.responses.StatisticalDTO;
import com.webgram.dgpsn.models.responses.StatisticalTrancheAgeDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface ManagementUnitRepository extends JpaRepository<ManagementUnitEntity, Long>, QuerydslPredicateExecutor<ManagementUnitEntity> {

    default Page<ManagementUnitEntity> readAllByFiltering(
            Pageable pageable,
            String code,
            String name,
            TypeProjet type,
            String dateDebut,
            String dateFin,
            Double budget,
            Long poids,
            Long responsibleId,
            String tag,
            Long parentId,
            Long axePSEId,
            Boolean publish,
            List<Long> projectIds
    ) {
        var booleanBuider = new BooleanBuilder();
        if (Objects.nonNull(type)) {
            booleanBuider.and(QManagementUnitEntity.managementUnitEntity.type.eq(type));
        }
        if (Objects.nonNull(responsibleId)) {
            booleanBuider.and(QManagementUnitEntity.managementUnitEntity.responsible.id.eq(responsibleId));
        }
        if (Objects.nonNull(poids)) {
            booleanBuider.and(QManagementUnitEntity.managementUnitEntity.poids.eq(poids));
        }
        if (StringUtils.isNotEmpty(code)) {
            booleanBuider.and(QManagementUnitEntity.managementUnitEntity.code.containsIgnoreCase(code));
        }
        if (StringUtils.isNotEmpty(name)) {
            booleanBuider.and(QManagementUnitEntity.managementUnitEntity.name.containsIgnoreCase(name));
        }
        if (Objects.nonNull(dateDebut) && Objects.isNull(dateFin)) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateDebut);
                booleanBuider.and(QManagementUnitEntity.managementUnitEntity.expectedStartDate.eq(date));
            } catch (ParseException ex) {
                new ParseException(ex.getMessage(), ex.getErrorOffset());
            }
        }
        if (Objects.nonNull(dateFin) && Objects.isNull(dateDebut)) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateFin);
                booleanBuider.and(QManagementUnitEntity.managementUnitEntity.expectedEndDate.eq(date));
            } catch (ParseException ex) {
                new ParseException(ex.getMessage(), ex.getErrorOffset());
            }
        }
        if (Objects.nonNull(dateDebut) && Objects.nonNull(dateFin)) {
            try {
                Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateDebut);
                Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateFin);
                booleanBuider.and(QManagementUnitEntity.managementUnitEntity.expectedStartDate.between(startDate, endDate));
            } catch (ParseException ex) {
                new ParseException(ex.getMessage(), ex.getErrorOffset());
            }
        }
        if (Objects.nonNull(budget)) {
            booleanBuider.and(QManagementUnitEntity.managementUnitEntity.budget.eq(budget));
        }

        if (StringUtils.isNotEmpty(tag)) {
            booleanBuider.and(QManagementUnitEntity.managementUnitEntity.tag.containsIgnoreCase(tag));
        }
        if (Objects.nonNull(axePSEId)) {
            booleanBuider.and(QManagementUnitEntity.managementUnitEntity.axe.id.eq(axePSEId));
        }
        if (Objects.nonNull(parentId)) {
            booleanBuider.and(QManagementUnitEntity.managementUnitEntity.parent.id.eq(parentId));
        }
        if (Objects.nonNull(projectIds) && projectIds.size() > 0) {
            booleanBuider.and(QManagementUnitEntity.managementUnitEntity.id.in(projectIds));
        }
        if(Objects.nonNull(publish)) {
            booleanBuider.and(QManagementUnitEntity.managementUnitEntity.publish.eq(publish));
        }

        booleanBuider.and(QManagementUnitEntity.managementUnitEntity.actif.isTrue());

        return findAll(booleanBuider, pageable);
    }

//    @Query("select new sn.webg.suivievaluation.models.responses.StatisticalDTO(p.status.libelle, count(p)) from ManagementUnitEntity p group by p.status.libelle")
//    List<StatisticalDTO> findStatByStatus();

//    @Query("select new sn.webg.suivievaluation.models.responses.StatisticalDTO(p.flag.libelle, count(p)) from ManagementUnitEntity p group by p.flag.libelle")
//    List<StatisticalDTO> findStatByFlag();

    @Query("select sum(p.budget) from ManagementUnitEntity p")
    Long getTotalBudget();

//    ManagementUnitEntity findByFlag(FlagDTO flag);

//    @Query("select new sn.webg.suivievaluation.models.responses.StatisticalDTO(p.subSectors.libelle, count(p)) from ManagementUnitEntity p group by p.subSectors.libelle")
//    List<StatisticalDTO> findStatBySectors();

    @Query("select new com.webgram.dgpsn.models.responses.StatisticalDTO(ss.libelle, count(mu)) from ManagementUnitEntity mu join mu.subSectors ss group by ss.libelle")
    List<StatisticalDTO> findStatBySectors();

    //    @Query("select new sn.webg.suivievaluation.models.responses.StatisticalDTO(lab.libelle, count(mu)) from ManagementUnitEntity mu join mu.executionZones lab group by lab.libelle")
//    List<StatisticalDTO> findStatByExecutionZone();
    @Query("select new com.webgram.dgpsn.models.responses.StatisticalDTO(lab.libelle, count(mu)) " +
            "from ManagementUnitEntity mu " +
            "join mu.executionZones lab " +
            "where mu.type = :typeProjet " +
            "and lab.referentielType = :referentielType " +
            "group by lab.libelle")
    List<StatisticalDTO> findStatByExecutionZoneAndReferentielType(@Param("typeProjet") TypeProjet typeProjet, @Param("referentielType") ReferentielType referentielType);


//    @Query("select new sn.webg.suivievaluation.models.responses.StatisticalDTO(p.structure.code, count(p.id)) from ManagementUnitEntity p where p.structure.typeStructure = sn.webg.suivievaluation.entities.enums.TypeStructure.Ministere group by p.structure.code")
//    List<StatisticalDTO> countByStructure();

    @Query("SELECT new com.webgram.dgpsn.models.responses.StatisticalDTO(p.name, COUNT(p)) FROM ManagementUnitEntity p WHERE p.type = com.webgram.dgpsn.entities.enums.TypeProjet.PROGRAMME GROUP BY p.name")
    List<StatisticalDTO> countProjectsByProgramme();

    @Query(value = "select " +
            "count(case when (datediff(p.prj_actual_end_date, p.prj_actual_start_date) / 365 < 3) then 1 end) as jeune, " +
            "count(case when (datediff(p.prj_actual_end_date, p.prj_actual_start_date) / 365 >= 3) and (datediff(current_date, p.prj_actual_start_date) / 365 < 6) then 1 end) as mature, " +
            "count(case when (datediff(p.prj_actual_end_date, p.prj_actual_start_date) / 365 > 5) and 6 <= (datediff(current_date, p.prj_actual_start_date) / 365) then 1 end) as veillissant " +
            "from management_unite p",
            nativeQuery = true)
    StatisticalTrancheAgeDTO countProjetByTranche();




    Optional<ManagementUnitEntity> findById(Long id);

//    @Query("select count(p.id) from ManagementUnitEntity p where p.actif = true ")
//    Optional<Double> countTotalProjects();
@Query("SELECT COUNT(p.id) FROM ManagementUnitEntity p WHERE p.actif = true AND p.type = 'PROJECT'")
Optional<Double> countTotalProjects();


    List<ManagementUnitEntity> findAllByPublish(boolean publish);

    Long countAllByPublish(boolean publish);


    List<ManagementUnitEntity> findByParent(ManagementUnitEntity managementUnit);

    List<ManagementUnitEntity> findByType(TypeProjet typeProjet);

    @Query("SELECT new com.webgram.dgpsn.models.responses.StatisticalDTO(cl.libelle, COUNT(mu)) " +
            "FROM CadreLogiqueEntity cl " +
            "LEFT JOIN GeographicalLocationEntity gl ON cl.id = gl.cadreLogique.id " +
            "LEFT JOIN gl.projet mu " +
            "WHERE cl.typeCadreLogique = :regionType " +
            "GROUP BY cl.libelle")
    List<StatisticalDTO> countProjectsByRegion(@Param("regionType") CadreLogiqueType regionType);

    List<ManagementUnitEntity> findByTypeIn(List<TypeProjet> types);

    List<ManagementUnitEntity> findAllByActifIsTrue();

    //////////
    /**
     * Trouve tous les enfants d'un parent par type
     */
    @Query("SELECT m FROM ManagementUnitEntity m WHERE m.parent.id = :parentId AND m.type = :type ORDER BY m.code ASC")
    List<ManagementUnitEntity> findByParentIdAndType(@Param("parentId") Long parentId, @Param("type") TypeProjet type);

    /**
     * Trouve tous les enfants d'un parent
     */
    @Query("SELECT m FROM ManagementUnitEntity m WHERE m.parent.id = :parentId ORDER BY m.code ASC")
    List<ManagementUnitEntity> findByParentId(@Param("parentId") Long parentId);

    /**
     * Trouve toutes les unités de gestion par type
     */
//    @Query("SELECT m FROM ManagementUnitEntity m WHERE m.type = :type ORDER BY m.code ASC")
//    List<ManagementUnitEntity> findByType(@Param("type") TypeProjet type);
}
