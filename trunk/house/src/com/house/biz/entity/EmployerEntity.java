package com.house.biz.entity;

import com.house.core.entity.GenericEntity;

public class EmployerEntity extends GenericEntity implements java.io.Serializable{
    private static final long serialVersionUID = -632868898885659059L;
    
    private String employerId;        //雇主Id
    private String linkMan;   //联系人
    private String loginName; //用户名
    private String passWord;  //密码
    private String tel;       //电话
    
    public EmployerEntity(){
        
    }
    
    public String getEmployerId() {
        return employerId;
    }
    public void setEmployerId(String employerId) {
        this.employerId = employerId;
    }
    public String getLinkMan() {
        return linkMan;
    }
    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
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
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    
    
}
