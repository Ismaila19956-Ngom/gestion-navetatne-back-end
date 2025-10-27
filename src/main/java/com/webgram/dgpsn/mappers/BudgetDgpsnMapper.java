package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.BudgetDgpsnEntity;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.models.BudgetDgpsnDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class BudgetDgpsnMapper implements EntityMapper<BudgetDgpsnDTO, BudgetDgpsnEntity> {

    @Autowired

    @Override
//    @Mapping(target = "managementUnit", source = "managementUnitId", qualifiedByName = "getManagementUnit")
    public abstract BudgetDgpsnEntity asEntity(BudgetDgpsnDTO dto);

//    @Named("getManagementUnit")
//    public ManagementUnitEntity getManagementUnit(Long managementUnitId) {
//        return ManagementUnitEntity.builder().id(managementUnitId).build();
//    }
}