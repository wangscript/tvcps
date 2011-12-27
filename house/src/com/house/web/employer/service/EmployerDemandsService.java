package com.house.web.employer.service;

import java.util.List;

import com.house.biz.entity.EmployerDemandsEntity;
import com.house.core.service.GenericService;

public interface EmployerDemandsService extends GenericService<EmployerDemandsEntity, String> {
    public List<EmployerDemandsEntity> queryEmployerDemands();
    public String saveEmployerDemands(EmployerDemandsEntity employerDemandsEntity);
}
