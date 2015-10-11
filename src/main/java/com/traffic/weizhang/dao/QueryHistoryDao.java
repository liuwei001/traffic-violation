package com.traffic.weizhang.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.traffic.common.base.dao.BaseDao;
import com.traffic.weizhang.entity.TQueryHistory;

/**
 * 查询车辆历史
 * @author Administrator
 *
 */
@Repository("queryHistoryDao")
public class QueryHistoryDao extends BaseDao{
	
	public  QueryHistoryDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<TQueryHistory> queryForCheck(Map<String, String> mapparam) {
		return super.getSqlMapClientTemplate().queryForList(QueryHistoryDao.class.getName()+ ".queryForCheck",mapparam);
	}
}
