package test;

import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.driver.OracleTypes;

import com.house.biz.entity.AdminUserEntity;
import com.house.core.action.GenericAction;

public class Test extends GenericAction
{
	private String name;
	private String project;
	
	private List adminUserList=new ArrayList();

	private AdminUserEntity adminUserEntity;
	
	public String test(){
	 	name="娄伟峰";
	 	project="joyque";
	 //	java.sql.Types.
	 	AdminUserEntity adminUserEntity=new AdminUserEntity();
	 	adminUserEntity.setEmail("lwf4423@163.com");
	 	adminUserEntity.setAdminuser_id("1111111111111111");
	 	adminUserEntity.setLoginName("222222222222222222222");
	       
	       AdminUserEntity adminUserEntity2=new AdminUserEntity();
	       adminUserEntity2.setEmail("test@163.com");
	       adminUserEntity2.setAdminuser_id("wqeewewew");
	       adminUserEntity2.setLoginName("333333333");
	       adminUserList.add(adminUserEntity);
	       adminUserList.add(adminUserEntity2);

        return SUCCESS;
    }
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the project
	 */
	public String getProject() {
		return project;
	}
	/**
	 * @param project the project to set
	 */
	public void setProject(String project) {
		this.project = project;
	}
	/**
	 * @return the adminUserEntity
	 */
	public AdminUserEntity getAdminUserEntity() {
		return adminUserEntity;
	}
	/**
	 * @param adminUserEntity the adminUserEntity to set
	 */
	public void setAdminUserEntity(AdminUserEntity adminUserEntity) {
		this.adminUserEntity = adminUserEntity;
	}
	/**
	 * @return the adminUserList
	 */ 
	public List getAdminUserList() {
		return adminUserList;
	}
	/**
	 * @param adminUserList the adminUserList to set
	 */
	public void setAdminUserList(List adminUserList) {
		this.adminUserList = adminUserList;
	}


}

