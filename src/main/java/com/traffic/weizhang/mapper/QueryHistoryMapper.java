package com.traffic.weizhang.mapper;

import java.util.List;
import java.util.Map;

import com.traffic.common.annotation.MyBatisDao;
import com.traffic.common.base.mapper.BaseMapper;
import com.traffic.weizhang.entity.TQueryHistory;

/**
 * 查询车辆历史
 * @author Administrator
 *
 */
@MyBatisDao
public interface QueryHistoryMapper extends BaseMapper{

	public List<TQueryHistory> queryForCheck(Map<String, String> mapparam);
}
