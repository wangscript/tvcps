package com.house.web.employer.service;

import java.util.List;

import com.house.biz.entity.EmployerDemandsEntity;
import com.house.core.service.GenericServiceImpl;
import com.house.core.util.IDFactory;
import com.house.web.employer.dao.EmployerDemandsDao;

public class EmployerDemandsServiceImpl extends GenericServiceImpl<EmployerDemandsEntity, String> implements EmployerDemandsService {
    private EmployerDemandsDao employerDemandsDao;

    /**
     * 设置子类dao
     */
    public void setDao() {
        this.genericDao = employerDemandsDao;
    }
    public EmployerDemandsDao getEmployerDemandsDao() {
        return employerDemandsDao;
    }

    public void setEmployerDemandsDao(EmployerDemandsDao employerDemandsDao) {
        this.employerDemandsDao = employerDemandsDao;
    }
    @Override
    public List<EmployerDemandsEntity> queryEmployerDemands() {
        return employerDemandsDao.queryObjectsByObject(new EmployerDemandsEntity()); 
    }
    
    public String saveEmployerDemands(EmployerDemandsEntity employerDemandsEntity){
        if(employerDemandsEntity.getEmDemandId() != null && !employerDemandsEntity.getEmDemandId().equals("")){
            return updateObject(employerDemandsEntity);
        }else{
            employerDemandsEntity.setEmDemandId(IDFactory.getId());
            return saveObject(employerDemandsEntity);
        }
    }
    
}
