package sn.naavetane.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.naavetane.backend.entities.RoleEntity;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.naavetane.backend.entities.QRoleEntity;
import org.apache.commons.lang3.StringUtils;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>, QuerydslPredicateExecutor<RoleEntity> {
    Optional<RoleEntity> findByCode(String code);

    default Page<RoleEntity> readAllByFilters(Pageable pageable, String code, String libelle, Boolean ugp) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (StringUtils.isNotEmpty(code)) {
            booleanBuilder.and(QRoleEntity.roleEntity.code.containsIgnoreCase(code));
        }
        if (StringUtils.isNotEmpty(libelle)) {
            booleanBuilder.and(QRoleEntity.roleEntity.libelle.containsIgnoreCase(libelle));
        }
        if (ugp != null) {
            booleanBuilder.and(QRoleEntity.roleEntity.ugp.eq(ugp));
        }

        return findAll(booleanBuilder, pageable);
    }
}
