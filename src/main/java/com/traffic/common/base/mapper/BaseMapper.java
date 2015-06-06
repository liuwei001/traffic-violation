package com.traffic.common.base.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.traffic.common.exception.DalException;

/**
 * mybatis 公共mapper
 * @Description:TODO
 * @Copyright Copyright 2014-2015 foresealife Tech. Co. Ltd. All Rights Reserved.<BR>
 * @author： liuwei <BR>
 * @Time： 2015年2月2日 <BR>
 * @version 1.0.0 <BR>
 */
public interface BaseMapper {

	
	/**
	 * 保存实体对象
	 *saveEntity  
	 *@param entity
	 */
	public <T extends Serializable> void insert(T entity) throws DalException;
	
	/**
	 * 修改信息
	 *updateEntity  
	 *@param entity
	 */
	public <T extends Serializable> void update(T entity) throws DalException;
	
	/**
	 * 根据Id查询实体对象
	 *findById  
	 *@param Id 主键id
	 *@return
	 */
	public <T extends Serializable> T findById(Serializable Id) throws DalException;
	
	 /**
     * 根据key删除实体对象
     *deleteByKey  
     *@param entity
     *@throws DalException
     */
    public void deleteByKey(Serializable entity) throws DalException;
    
    /**
     * 查询表所有列表
     *findAll  
     *@return
     *@throws DalException
     */
    public <T extends Serializable> List<T> findAll() throws DalException;
    
    /**
     *查询行数
     *getRowCount  
     *@return
     *@throws DalException
     */
    public int getRowCount(Map<String, Object> map) throws DalException;
    
    /**
     * 分页查询
     * @param map 查询条件 HashMap
     * @return PageList 列表
     */
    public <T extends Serializable> List<T> queryForPageList(Map<String, Object> map) throws DalException;
    
    /**
     * 不分页查询
     * @param map
     * @return
     * @throws DalException
     */
    public <T extends Serializable> List<T> queryList(Map<String, Object> map) throws DalException;
}
