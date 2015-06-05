package com.traffic.common.base.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.traffic.common.base.mapper.BaseMapper;
import com.traffic.common.base.service.IBaseService;
import com.traffic.common.exception.DalException;
import com.traffic.common.page.PageInfo;

/**
 * 公共抽象的base服务实现类
 * @Description:TODO
 * @Copyright Copyright 2014-2015 traffic Tech. Co. Ltd. All Rights Reserved.<BR>
 * @author： liuwei <BR>
 * @Time： 2015年1月29日 <BR>
 * @version 1.0.0 <BR>
 */
public abstract class BaseServiceImpl	implements IBaseService {
	
	private final int PAGE_SIZE = 10;

	/*** 获取dao类 **/
	public abstract BaseMapper getMapper();
	
	/**
	 * 根据id查询实体
	 */
	@Override
	public <T extends Serializable> T findById(Serializable id)
			throws DalException {
		return getMapper().findById(id);
	}

	/**
	 * 保存实体对象
	 */
	@Override
	public <T extends Serializable> void saveEntity(T entity) throws DalException {
		getMapper().insert(entity);
	}

	/**
	 * 修改实体对象
	 */
	@Override
	public <T extends Serializable> void updateEntity(T entity) throws DalException {
		getMapper().update(entity);
	}

	/**
	 * 根据key删除
	 */
	@Override
	public void deleteByKey(Serializable entity) throws DalException {
		getMapper().deleteByKey(entity);
	}

	
	/**
	 * 查询所有列表
	 */
	@Override
	public <T extends Serializable> List<T> findAll()
			throws DalException {
		return getMapper().findAll();
	}

	/**
	 * 分页查询列表
	 * 
	 * mysql脚本：
	 * 
	 * select * from VW_L0114  order by BILL_DATE desc  limit #{skipIndex} , #{pageSize}
	 * 
	 * 
	 * oracle脚本：
	 * SELECT *
  			from (select t.*, rownum rn
          			from (select * from VW_L0114 A 
        					order by BILL_DATE desc) t
			   where rownum <= #{skipIndex} - 1 + #{pageSize}) 
		 where rn > #{skipIndex} - 1
	 * 
	 */
	@Override
	public <T extends Serializable> PageInfo<T> queryForPageList(
			Map<String, Object> map)
			throws DalException {
		int currPage = (Integer)map.get(PageInfo.CURRENT_PAGE);
		int pageSize = map.containsKey(PageInfo.PAGE_SIZE) ? (Integer)map.get(PageInfo.PAGE_SIZE) : PAGE_SIZE;
		
		//举例：当前页为第2页，每页显示10条，则开始记录数为11，结束记录数为20
        //得到记录的开始数
        int skipIndex = (currPage - 1) * pageSize + 1;
        int totalRows = 0;
        
//        long startTime = System.currentTimeMillis();
        try
        {
        	 totalRows = getMapper().getRowCount(map);
        }catch (Exception e) {
            throw new DalException(e);
        }
//        long endTime1 = System.currentTimeMillis();
//        System.out.println("time1 ====== " +(endTime1-startTime));
        // 带分页参数的列表
        PageInfo<T> pageList = new PageInfo<T>(currPage, pageSize, totalRows);
        // 支持物理分页页码从1开始
        try
        {
        	if(map == null) {
        		map = new HashMap<String, Object>();
        	}
        	map.put("skipIndex", skipIndex);
        	map.put(PageInfo.PAGE_SIZE, pageSize);
        	List<T> queryResult = getMapper().queryForPageList(map);
        	
            if (queryResult != null)
            {
                pageList.setItems(queryResult);
            }
        }
        catch (Exception e)
        {
            throw new DalException(e);
        }
        
//        long endTime2 = System.currentTimeMillis();
//        System.out.println("time2 ====== " +(endTime2-endTime1));
        
        return pageList;
	}

}
