package com.house.core.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.house.core.entity.Pagination;
import com.house.core.util.GenericsUtils;

public class GenericDaoImpl<T extends Serializable, PK extends Serializable> extends SqlMapClientDaoSupport implements GenericDao<T, PK>  {  

    protected final Logger log = Logger.getLogger(getClass());
    
    /**
     * 通过主键Id获取一条记录
     */
    private static final String POSTFIX_SELECTBYID = ".selectById"; 
    
    /**
     * 通过主键Id序列获取一条记录
     */
    private static final String POSTFIX_SELECTBYIDS = ".selectByIds"; 
    
    
    /**
     * 通过条件(属性-值) 查询记录
     */
    private static final String POSTFIX_SELECTBYMAP = ".selectByMap";
    
    /**
     * 通过条件(对象) 查询记录
     */
    private static final String POSTFIX_SELECTBYOBJECT = ".selectByObject";
    
    /**
     * 通过条件(对象) 查询 分页 记录
     */
    private static final String POSTFIX_SELECTBYOBJECTFORPAGINATION = ".selectByObjectForPagination";
    
    /**
     * 通过条件(对象) 查询主键id列表
     */
    private static final String POSTFIX_PKSELECTBYOBJECT = ".pkSelectByObject";  
    
    /**
     * 通过条件(属性-值) 查询主键id列表
     */
    private static final String POSTFIX_PKSELECTBYMAP = ".pkSelectByMap";  
    
    /**
     * 统计数量
     */
    private static final String POSTFIX_COUNT = ".count"; 
    
    /**
     * 通过条件(对象) 统计数量
     */
    private static final String POSTFIX_COUNTBYOBJECT = ".countByObject"; 
    
    /**
     * 通过条件(属性-值) 统计数量
     */
    private static final String POSTFIX_COUNTBYMAP = ".countByMap"; 
    
    /**
     * 插入一条记录
     */
    private static final String POSTFIX_INSERT = ".insert";  
    
    /**
     * 通过主键删除一条记录
     */
    private static final String POSTFIX_DELETEBYID = ".deleteById";  
    
    /**
     * 通过主键序列删除数个记录
     */
    private static final String POSTFIX_DELETEBYIDS = ".deleteByIds"; 
    
    /**
     * 通过条件(属性-值) 删除数个记录 
     */
    private static final String POSTFIX_DELETEBYOBJECT = ".deleteByObject";
    
    /**
     * 通过对象更新记录
     */
    private static final String POSTFIX_UPDATEBYOBJECT = ".updateByObject";  
    
    /**
     * 通过条件(属性-值) 更新记录
     */
    private static final String POSTFIX_UPDATEBYMAP = ".updateByMap";
    
    protected Class<T> clazz;  
    
    protected String clazzName;  
    
    protected String packageName;
  
    protected T t; 
    
	public GenericDaoImpl() {  
        // 通过范型反射，取得在子类中定义的class.  
      //  clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		clazz = GenericsUtils.getSuperClassGenricType(getClass());   

        clazzName = clazz.getSimpleName(); 
        packageName = clazzName.toLowerCase();
    } 
	
	/**
	 * <p>方法说明：设置对象字段值</p> 
	 * <p>创建时间：2011-10-14上午11:35:20</p>
	 * <p>作者: 刘加东</p>
	 * @param t 对象
	 * @param fieldName 字段名
	 * @param fileValue void 字段值
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void setClassFieldValue(T t,String fieldName,Object fieldValue) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		Class<?> parentClazz = clazz.getSuperclass();
		Field f = parentClazz.getDeclaredField(fieldName);
		f.set(t, fieldValue);
	}

	/* (non-Javadoc)
	 * @see com.ackwin.common.dao.GenericDao#queryObjectsByPaginationAndObject(com.ackwin.common.dao.Pagination, java.io.Serializable)
	 */
	@Override
	public Pagination queryObjectsByPaginationAndObject(Pagination pagination, T t) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		//-- 封装ibatis传入参数的 分页信息
		if(pagination == null){
			pagination = new Pagination();
		}
		int rowsPerPage = pagination.getRowsPerPage();
		long currPage = pagination.getCurrPage();
		setClassFieldValue(t,"currPage",currPage);
		setClassFieldValue(t,"rowsPerPage",rowsPerPage);
		setClassFieldValue(t,"rowscount",((int)currPage-1)*(int)rowsPerPage);  //从多少页开始显示：测试
		//-- 查询分页数据
		List<T> list = selectByObjectForPagination(t);
		//获取总记录集数量
		long maxRowCount = countObjecsByObject(t);
		//--封装分页 - 开始
		pagination.setData(list);
		pagination.setMaxRowCount(maxRowCount);
		return pagination;
	}

	/* (non-Javadoc)
	 * @see com.ackwin.common.dao.GenericDao#selectByObjectForPagination(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> selectByObjectForPagination(T t) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList(packageName + POSTFIX_SELECTBYOBJECTFORPAGINATION,t);
	}

	/* (non-Javadoc)
	 * @see com.ackwin.common.dao.GenericDao#countObjecs(java.util.Map)
	 */
	@Override
	public Long countObjecs() {
		// TODO Auto-generated method stub
		return (Long)getSqlMapClientTemplate().queryForObject(packageName + POSTFIX_COUNT);
	}

	/* (non-Javadoc)
	 * @see com.ackwin.common.dao.GenericDao#countObjecsByMap(java.util.Map)
	 */
	@Override
	public Long countObjecsByMap(Map map) {
		// TODO Auto-generated method stub
		return (Long)getSqlMapClientTemplate().queryForObject(packageName + POSTFIX_COUNTBYMAP,map);
	}

	/* (non-Javadoc)
	 * @see com.ackwin.common.dao.GenericDao#countObjecsByObject(java.io.Serializable)
	 */
	@Override
	public Long countObjecsByObject(T t) {
		// TODO Auto-generated method stub
		return (Long)getSqlMapClientTemplate().queryForObject(packageName + POSTFIX_COUNTBYOBJECT,t);
	}

	/* (non-Javadoc)
	 * @see com.ackwin.common.dao.GenericDao#deleteById(java.io.Serializable)
	 */
	@Override
	public int deleteById(PK id) {
		// TODO Auto-generated method stub
		int count = getSqlMapClientTemplate().delete(packageName + POSTFIX_DELETEBYID, id);
		return count;
	}

	/* (non-Javadoc)
	 * @see com.ackwin.common.dao.GenericDao#deleteByIds(java.util.List)
	 */
	@Override
	public int deleteByIds(List<PK> id) {
		// TODO Auto-generated method stub
		int count = getSqlMapClientTemplate().delete(packageName + POSTFIX_DELETEBYIDS, id);
		return count;
	}

	/* (non-Javadoc)
	 * @see com.ackwin.common.dao.GenericDao#deleteByMap(java.util.Map)
	 */
	@Override
	public int deleteByObject(T t) {
		// TODO Auto-generated method stub
		int count = getSqlMapClientTemplate().delete(packageName + POSTFIX_DELETEBYOBJECT, t);
		return count;
	}

	/* (non-Javadoc)
	 * @see com.ackwin.common.dao.GenericDao#insertObject(java.io.Serializable)
	 */
	@Override
	public Object insertObject(T t) {
		// TODO Auto-generated method stub
		Object obj = getSqlMapClientTemplate().insert(packageName + POSTFIX_INSERT, t);
		return obj;
	}

	/* (non-Javadoc)
	 * @see com.ackwin.common.dao.GenericDao#queryObject(java.io.Serializable)
	 */
	@Override
	public T queryObject(PK id) {
		// TODO Auto-generated method stub
		return (T)getSqlMapClientTemplate().queryForObject(packageName + POSTFIX_SELECTBYID, id);
	}

	/* (non-Javadoc)
	 * @see com.ackwin.common.dao.GenericDao#queryObjects(java.util.List)
	 */
	@Override
	public List<T> queryObjects(List<PK> ids) {
		// TODO Auto-generated method stub
		
		return getSqlMapClientTemplate().queryForList(packageName + POSTFIX_SELECTBYIDS,ids);
	}

	/* (non-Javadoc)
	 * @see com.ackwin.common.dao.GenericDao#queryObjectsByMap(java.io.Serializable)
	 */
	@Override
	public List<T> queryObjectsByObject(T t) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList(packageName + POSTFIX_SELECTBYOBJECT,t);
	}

	/* (non-Javadoc)
	 * @see com.ackwin.common.dao.GenericDao#queryObjectsByMap(java.util.Map)
	 */
	@Override
	public List<T> queryObjectsByMap(Map map) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList(packageName + POSTFIX_SELECTBYMAP,map);
	}

	/* (non-Javadoc)
	 * @see com.ackwin.common.dao.GenericDao#queryPKsByObject(java.io.Serializable)
	 */
	@Override
	public List<PK> queryPKsByObject(T t) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList(packageName + POSTFIX_PKSELECTBYOBJECT,t);
	}

	/* (non-Javadoc)
	 * @see com.ackwin.common.dao.GenericDao#queryPKsByMap(java.util.Map)
	 */
	@Override
	public List<PK> queryPKsByMap(Map map) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList(packageName + POSTFIX_PKSELECTBYMAP,map);
	}

	/* (non-Javadoc)
	 * @see com.ackwin.common.dao.GenericDao#updateByMap(java.util.Map)
	 */
	@Override
	public int updateByMap(Map map) {
		// TODO Auto-generated method stub
		int count = getSqlMapClientTemplate().update(packageName + POSTFIX_UPDATEBYMAP, map);
		return count;
	}

	/* (non-Javadoc)
	 * @see com.ackwin.common.dao.GenericDao#updateByObject(java.io.Serializable)
	 */
	@Override
	public int updateByObject(T t) {
		// TODO Auto-generated method stub
		int count = getSqlMapClientTemplate().update(packageName + POSTFIX_UPDATEBYOBJECT, t);
		return count;
	}
}  