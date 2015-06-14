package com.traffic.weizhang.mapper;

import java.util.List;
import java.util.Map;

import com.traffic.common.annotation.MyBatisDao;
import com.traffic.common.base.mapper.BaseMapper;
import com.traffic.weizhang.entity.TSubscribe;

/**
 * 订阅
 * @author Administrator
 *
 */
@MyBatisDao
public interface SubscribeMapper extends BaseMapper {

	public List<TSubscribe> queryForCheck(Map<String, String> mapparam);
}
