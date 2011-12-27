package com.house.web.employer.action;

import com.house.biz.entity.EmployerDemandsEntity;
import com.house.biz.entity.EmployerEntity;
import com.house.core.action.GenericAction;
import com.house.web.employer.service.EmployerDemandsService;

public class EmployerDemandsAction extends GenericAction {

    private static final long serialVersionUID = -6707968396223420655L;
    private EmployerDemandsService employerDemandsService;
    private EmployerDemandsEntity employerDemandsEntity;
    private EmployerEntity employer = new EmployerEntity();

    public String queryEmployerDemands() throws Exception {
        if(employerDemandsEntity == null){
            employerDemandsEntity = new EmployerDemandsEntity();
        }
        if(employer == null){
            employer = new EmployerEntity();
        }
        employerDemandsEntity.setEmployer(employer);
        pagination = employerDemandsService.queryObjectsByPaginationAndObject(employerDemandsEntity, pagination);
        return SUCCESS;
    }
    
    public String findEmployerDemandsById(){
        employerDemandsEntity = employerDemandsService.findObjectById(strChecked);
        return SUCCESS;
    }
    public String detailEmployerDemands(){
        return SUCCESS;
    }
    
    public String deleteEmployerDemandsByIds(){
        addActionMessage(employerDemandsService.deleteObjectByIds(strChecked));
        return SUCCESS;
    }
    public String addEmployerDemands(){
        addActionMessage(employerDemandsService.saveEmployerDemands(employerDemandsEntity));
        return SUCCESS;
    }
    
    public EmployerDemandsService getEmployerDemandsService() {
        return employerDemandsService;
    }
    public void setEmployerDemandsService(
            EmployerDemandsService employerDemandsService) {
        this.employerDemandsService = employerDemandsService;
    }

    public EmployerDemandsEntity getEmployerDemandsEntity() {
        return employerDemandsEntity;
    }

    public void setEmployerDemandsEntity(EmployerDemandsEntity employerDemandsEntity) {
        this.employerDemandsEntity = employerDemandsEntity;
    }

    public EmployerEntity getEmployer() {
        return employer;
    }

    public void setEmployerEntity(EmployerEntity employer) {
        this.employer = employer;
    }
}
