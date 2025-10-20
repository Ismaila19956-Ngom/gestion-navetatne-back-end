package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.QRoleEntity;
import com.webgram.dgpsn.entities.RoleEntity;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>, QuerydslPredicateExecutor<RoleEntity> {

    default Page<RoleEntity> readAllByFilter(Pageable pageable,String code, String libelle, Boolean ugp,  String sortBy,
                                             Boolean ascending) {
        var booleanBuider = new BooleanBuilder();
        Sort sort = Sort.unsorted();
        if(StringUtils.isNotEmpty(code)) {
            booleanBuider.and(QRoleEntity.roleEntity.code.containsIgnoreCase(code));
        }
        if(StringUtils.isNotEmpty(libelle)) {
            booleanBuider.and(QRoleEntity.roleEntity.libelle.containsIgnoreCase(libelle));
        }
        if(Objects.nonNull(ugp)) {
            booleanBuider.and(QRoleEntity.roleEntity.ugp.eq(ugp));
        }
        if(StringUtils.isNotEmpty(sortBy)) {
            sort = Sort.by(sortBy);
        }
        if((Objects.nonNull(ascending))) {
            sort.ascending();
        }
        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return findAll(booleanBuider, pageRequest);
    }

    Set<RoleEntity> findByUgpTrue();

    Optional<RoleEntity> findByCode(String code);

}
