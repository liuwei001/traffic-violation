package com.traffic.common.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.traffic.common.exception.DalException;

public class BaseDao extends SqlMapClientDaoSupport{
	
	/**
	 * 保存实体对象
	 *saveEntity  
	 *@param entity
	 */
	public <T extends Serializable> void insert(T entity) throws DalException {
		this.getSqlMapClientTemplate().insert(getClass().getName() + ".insert",entity);
	};
	
	/**
	 * 修改信息
	 *updateEntity  
	 *@param entity
	 */
	@SuppressWarnings("deprecation")
	public <T extends Serializable> void update(T entity) throws DalException {
	 	 this.getSqlMapClientTemplate().update(getClass().getName() + ".update",entity);
	};
	
	/**
	 * 根据Id查询实体对象
	 *findById  
	 *@param Id 主键id
	 *@return
	 */
	public <T extends Serializable> T findById(Serializable id) throws DalException {
		return (T)this.getSqlMapClientTemplate().queryForObject(getClass().getName() + ".findById",id);
	};
	
	 /**
     * 根据key删除实体对象
     *deleteByKey  
     *@param entity
     *@throws DalException
     */
    public void deleteByKey(Serializable entity) throws DalException {
    	this.getSqlMapClientTemplate().delete(getClass().getName() + ".deleteByKey",entity);
    };
    
    /**
     * 查询表所有列表
     *findAll  
     *@return
     *@throws DalException
     */
    public <T extends Serializable>  List<T> findAll() throws DalException {
    	return this.getSqlMapClientTemplate().queryForList(getClass().getName() + ".findAll");
    };
    
    /**
     *查询行数
     *getRowCount  
     *@return
     *@throws DalException
     */
    public int getRowCount(Map<String, Object> map) throws DalException {
    	return (Integer)this.getSqlMapClientTemplate().queryForObject(getClass().getName() + ".getOrderMapListCount",map);
    };
    
    /**
     * 分页查询
     * @param map 查询条件 HashMap
     * @return PageList 列表
     */
    public <T extends Serializable> List<T> queryForPageList(Map<String, Object> map) throws DalException {
    	return this.getSqlMapClientTemplate().queryForList(getClass().getName() + ".queryForPageList",map);
    };
    
    /**
     * 不分页查询
     * @param map
     * @return
     * @throws DalException
     */
    public  <T extends Serializable> List<T> queryList(Map<String, Object> map) throws DalException {
    	return this.getSqlMapClientTemplate().queryForList(getClass().getName() + ".queryList",map);
    };
}
