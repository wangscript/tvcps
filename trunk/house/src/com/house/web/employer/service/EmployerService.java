package com.house.web.employer.service;

import com.house.biz.entity.EmployerEntity;
import com.house.core.service.GenericService;

public interface EmployerService extends GenericService<EmployerEntity, String> {
    public EmployerEntity checkLogin(String loginName, String passWord);
    public String saveEmployer(EmployerEntity employerEntity);
}
