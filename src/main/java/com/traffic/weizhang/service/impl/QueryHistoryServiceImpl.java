package com.traffic.weizhang.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traffic.common.base.mapper.BaseMapper;
import com.traffic.common.base.service.impl.BaseServiceImpl;
import com.traffic.weizhang.entity.TQueryHistory;
import com.traffic.weizhang.mapper.QueryHistoryMapper;
import com.traffic.weizhang.service.IQueryHistoryService;

@Service("queryHistoryService")
public class QueryHistoryServiceImpl extends BaseServiceImpl implements IQueryHistoryService{

	@Autowired
	private QueryHistoryMapper queryHistoryMapper;
	
	@Override
	public BaseMapper getMapper() {
		// TODO Auto-generated method stub
		return queryHistoryMapper;
	}

	public List<TQueryHistory> queryForCheck(Map<String, String> mapparam) {
		return queryHistoryMapper.queryForCheck(mapparam);
	}
}
