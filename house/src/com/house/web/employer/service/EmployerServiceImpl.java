package com.house.web.employer.service;

import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;

import com.house.biz.entity.EmployerEntity;
import com.house.core.service.GenericServiceImpl;
import com.house.core.util.IDFactory;
import com.house.web.employer.dao.EmployerDao;

public class EmployerServiceImpl extends GenericServiceImpl<EmployerEntity, String> implements EmployerService {
    private EmployerDao employerDao;

    @Override
    public EmployerEntity checkLogin(String loginName, String passWord) {
        if(StringUtils.isBlank(loginName) || StringUtils.isBlank(passWord)){
            return null;
        }
        EmployerEntity employerEntity = new EmployerEntity();
        employerEntity.setLoginName(loginName);
        employerEntity.setPassWord(passWord);
        List<EmployerEntity> list = employerDao.queryObjectsByObject(employerEntity);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }
    
    public String saveEmployer(EmployerEntity employerEntity){
        if(employerEntity.getEmployerId() != null && !employerEntity.getEmployerId().equals("")){
            return updateObject(employerEntity);
        }else{
            employerEntity.setEmployerId(IDFactory.getId());
            return saveObject(employerEntity);
        }
    }

    /**
     * 设置子类dao
     */
    public void setDao() {
        this.genericDao = employerDao;
    }
    public EmployerDao getEmployerDao() {
        return employerDao;
    }

    public void setEmployerDao(EmployerDao employerDao) {
        this.employerDao = employerDao;
    }
}
