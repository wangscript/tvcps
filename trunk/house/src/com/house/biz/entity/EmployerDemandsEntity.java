package com.house.biz.entity;

import java.io.Serializable;
import java.util.List;

import com.house.core.entity.GenericEntity;

public class EmployerDemandsEntity extends GenericEntity implements Serializable {
    private static final long serialVersionUID = -778811906649386310L;
    
    private String emDemandId;       //雇员需求主键
    private VillageEntity village;   //小区代码
    private EmployerEntity employer; //雇员
    private String evolvecaseId;     //进展情况
    private String rate;             //频次
    private String hourLength;       //时长
    private String houseArea;        //居家面积
    private String evolveStatus;     //进展情况状态
    private String demandExplain;    //主要需求说明
    private List<EmployerEntity> employers;
    
    public EmployerDemandsEntity(){
        
    }
    
    public EmployerEntity getEmployer() {
        return employer;
    }
    public void setEmployer(EmployerEntity employer) {
        this.employer = employer;
    }
    public String getRate() {
        return rate;
    }
    public void setRate(String rate) {
        this.rate = rate;
    }
    public String getHourLength() {
        return hourLength;
    }
    public void setHourLength(String hourLength) {
        this.hourLength = hourLength;
    }
    public String getHouseArea() {
        return houseArea;
    }
    public void setHouseArea(String houseArea) {
        this.houseArea = houseArea;
    }
    public String getEvolveStatus() {
        return evolveStatus;
    }
    public void setEvolveStatus(String evolveStatus) {
        this.evolveStatus = evolveStatus;
    }
    public String getDemandExplain() {
        return demandExplain;
    }
    public void setDemandExplain(String demandExplain) {
        this.demandExplain = demandExplain;
    }
    public String getEmDemandId() {
        return emDemandId;
    }
    public void setEmDemandId(String emDemandId) {
        this.emDemandId = emDemandId;
    }
    public VillageEntity getVillage() {
        return village;
    }
    public void setVillage(VillageEntity village) {
        this.village = village;
    }
    public String getEvolvecaseId() {
        return evolvecaseId;
    }
    public void setEvolvecaseId(String evolvecaseId) {
        this.evolvecaseId = evolvecaseId;
    }
    public List<EmployerEntity> getEmployers() {
        return employers;
    }
    public void setEmployers(List<EmployerEntity> employers) {
        this.employers = employers;
    }
}
