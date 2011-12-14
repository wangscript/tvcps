package com.house.web.index.action;
import java.util.List;
import com.house.biz.demandapply.service.DemandApplyService;
import com.house.biz.entity.DemandApplyEntity;
import com.house.biz.entity.ServicePersonEntity;
import com.house.biz.serviceperson.service.ServicePersonService;
import com.house.core.action.GenericAction;

@SuppressWarnings("serial")
public class IndexAction extends GenericAction {
    private DemandApplyService demandApplyService;
    private ServicePersonService servicePersonService;
    private List<DemandApplyEntity> demandApplyEntityList;
    private List<ServicePersonEntity> servicePersonList;

    /**
     * 首页->查询业主钟点工需求列表和家政服务人员列表
     * @return
     */
    public String index(){
    	demandApplyEntityList = demandApplyService.queryDemandApply();
    	servicePersonList = servicePersonService.queryServicePerson();
        return SUCCESS;
    }
    
    public void setDemandApplyService(DemandApplyService demandApplyService) {
        this.demandApplyService = demandApplyService;
    }
    
    public List<DemandApplyEntity> getDemandApplyEntityList() {
        return demandApplyEntityList;
    }

    public void setDemandApplyEntityList(List<DemandApplyEntity> demandApplyEntityList) {
        this.demandApplyEntityList = demandApplyEntityList;
    }

    public void setServicePersonService(ServicePersonService servicePersonService) {
        this.servicePersonService = servicePersonService;
    }

    public List<ServicePersonEntity> getServicePersonList() {
        return servicePersonList;
    }

    public void setServicePersonList(List<ServicePersonEntity> servicePersonList) {
        this.servicePersonList = servicePersonList;
    }
}
