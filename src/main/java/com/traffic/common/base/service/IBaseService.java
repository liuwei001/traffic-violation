package com.traffic.common.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.traffic.common.exception.DalException;
import com.traffic.common.page.PageInfo;


/**
 * 
 * 所有service的顶层接口
 * 定义了一些通用的方法
 * @author  t2w
 * @version  [V100R001, 2009-11-23]
 * @see  Serializable
 * @since  [SDP.BASE.SSI-V100R001]
 */
public interface IBaseService 
{
	 /**
     * 按主键编号查找一个实体对象
     * @param id 主键编号
     * @return Object [实体对象]
     * @exception throws [DalException] [发生异常包装后抛出]
     * @see DalException
     */
    public <T extends Serializable> T findById(Serializable id) throws DalException;
	
    /**
     * 保存一个实体对象
     * @param entity 实体对象
     * @exception throws [DalException] [发生异常包装后抛出]
     * @see DalException
     */
    public <T extends Serializable> void saveEntity(T entity) throws DalException;
    
    /**
     * 更新一个实体对象
     * @param entity 实体对象
     * @exception throws [DalException] [发生异常包装后抛出]
     * @see DalException
     */
    public <T extends Serializable> void updateEntity(T entity) throws DalException;

    /**
     * 根据key删除实体对象
     *deleteByKey  
     *@param entity
     *@throws DalException
     */
    public void deleteByKey(Serializable entity) throws DalException;
    
    /**
     * 查找所有符合条件的实体对象
     * @return List<Serializable> [返回所有符合条件的实体对象]
     * @exception throws [DalException] [发生异常包装后抛出]
     * @see DalException
     */
    public <T extends Serializable> List<T> findAll() throws DalException;
    
    /**
	 * 查询列表
	 * @param map
	 * @return
	 * @throws DalException
	 */
	public <T extends Serializable> List<T> queryList(Map<String, Object> map)
			throws DalException;
    
    /**
     * 分页查询满足条件的实体对象
     * @param map 查询需要的参数,可以是MAP或实体,当为NULL时表示没有参数
     * @param currPage 当前页码
     * @param pageSize 每页显示的记录数
     * @return PageList<Serializable> [返回所有符合条件的实体对象]
     * @exception throws [DalException] [发生异常包装后抛出]
     * @see DalException
     */
    public  <T extends Serializable> PageInfo<T> queryForPageList(Map<String,Object> map)
            throws DalException;

}
