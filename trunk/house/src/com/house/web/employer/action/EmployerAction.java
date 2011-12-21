package com.house.web.employer.action;

import com.house.biz.entity.EmployerEntity;
import com.house.core.action.GenericAction;
import com.house.core.sys.GlobalConfig;
import com.house.core.util.IDFactory;
import com.house.web.employer.service.EmployerService;

public class EmployerAction extends GenericAction {

    private static final long serialVersionUID = 3264906604941987857L;
    private EmployerService employerService;
    private EmployerEntity employer;
    private String loginName;
    private String passWord;
    private String errorMessage;
    
    public String loginEmployer(){
        employer = employerService.checkLogin(loginName, passWord);
        if(employer == null){
            addActionMessage(GlobalConfig.getConfProperty("100001"));
            return INPUT;
        }
        return SUCCESS;
    }
    
    public String regEmployer(){
        return SUCCESS;
    }
    
    public String saveEmployer(){
        employer.setEmployerId(IDFactory.getId()); 
        employerService.saveObject(employer);
        return SUCCESS;
    }
    
    public String listEmployer(){
        return SUCCESS;
    }
    
    public String queryEmployer() throws Exception {
        if(employer == null){
            employer = new EmployerEntity();
        }
        pagination = employerService.queryObjectsByPaginationAndObject(employer, pagination);
        return SUCCESS;
    }
    
    public EmployerService getEmployerService() {
        return employerService;
    }
    public void setEmployerService(EmployerService employerService) {
        this.employerService = employerService;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public EmployerEntity getEmployer() {
        return employer;
    }

    public void setEmployer(EmployerEntity employer) {
        this.employer = employer;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
