package com.traffic.weizhang.service;

import java.util.List;
import java.util.Map;

import com.traffic.common.base.service.IBaseService;
import com.traffic.weizhang.entity.TQueryHistory;

public interface IQueryHistoryService extends IBaseService {

	public List<TQueryHistory> queryForCheck(Map<String, String> mapparam);
}
