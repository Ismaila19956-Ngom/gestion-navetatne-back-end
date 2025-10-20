package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.PromoteurEntity;
import com.webgram.dgpsn.entities.QPromoteurEntity;

@Repository
public interface PromoteurRepository extends JpaRepository<PromoteurEntity, Long>, QuerydslPredicateExecutor<PromoteurEntity> {

   default Page<PromoteurEntity> readAllByFilters(Pageable pageable, String nomEntreprise, String personneContact, String fonctionContact, String telephone, String bureauEtudes, String adresseSiege, String adresseSite){
       var booleanBuilder = new BooleanBuilder();
       if(StringUtils.isNotEmpty(nomEntreprise)) {
           booleanBuilder.and(QPromoteurEntity.promoteurEntity.nomEntreprise.containsIgnoreCase(nomEntreprise));
       }
       if(StringUtils.isNotEmpty(personneContact)) {
           booleanBuilder.and(QPromoteurEntity.promoteurEntity.personneContact.containsIgnoreCase(personneContact));
       }
       if(StringUtils.isNotEmpty(fonctionContact)) {
           booleanBuilder.and(QPromoteurEntity.promoteurEntity.fonctionContact.containsIgnoreCase(fonctionContact));
       }
       if(StringUtils.isNotEmpty(telephone)) {
           booleanBuilder.and(QPromoteurEntity.promoteurEntity.telephone.containsIgnoreCase(telephone));
       }
       if(StringUtils.isNotEmpty(bureauEtudes)) {
           booleanBuilder.and(QPromoteurEntity.promoteurEntity.bureauEtudes.containsIgnoreCase(bureauEtudes));
       }
       if(StringUtils.isNotEmpty(adresseSiege)) {
           booleanBuilder.and(QPromoteurEntity.promoteurEntity.adresseSiege.containsIgnoreCase(adresseSiege));
       }
       if(StringUtils.isNotEmpty(adresseSite)) {
           booleanBuilder.and(QPromoteurEntity.promoteurEntity.adresseSite.containsIgnoreCase(adresseSite));
       }
       return findAll(booleanBuilder, pageable);
   }

}