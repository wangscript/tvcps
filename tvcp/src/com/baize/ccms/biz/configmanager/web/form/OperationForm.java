package com.baize.ccms.biz.configmanager.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.ccms.biz.usermanager.domain.Operation;
import com.baize.common.core.web.GeneralForm;

/* <p>标题: —— 操作管理form</p>
 * <p>模块: 用户管理</p> */

public class OperationForm extends GeneralForm {
	private static final long serialVersionUID = -2585433256800429859L;
	private Operation operation = new Operation();
	private List list = new ArrayList();
	private String id;
	private String operationName;
	private String ids;
	private String description;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	@Override
	protected void validateData(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperatioName(String operatioName) {
		this.operationName = operationName;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	
}
