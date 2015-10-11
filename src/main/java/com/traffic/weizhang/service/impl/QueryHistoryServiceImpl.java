package com.traffic.weizhang.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traffic.common.base.dao.BaseDao;
import com.traffic.common.base.service.impl.BaseServiceImpl;
import com.traffic.weizhang.dao.QueryHistoryDao;
import com.traffic.weizhang.entity.TQueryHistory;
import com.traffic.weizhang.service.IQueryHistoryService;

@Service("queryHistoryService")
public class QueryHistoryServiceImpl extends BaseServiceImpl implements IQueryHistoryService{

	@Autowired
	private QueryHistoryDao queryHistoryDao;
	
	@Override
	public BaseDao getDao() {
		// TODO Auto-generated method stub
		return queryHistoryDao;
	}

	public List<TQueryHistory> queryForCheck(Map<String, String> mapparam) {
		return queryHistoryDao.queryForCheck(mapparam);
	}
}
