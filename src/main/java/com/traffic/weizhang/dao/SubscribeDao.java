package com.traffic.weizhang.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.traffic.common.base.dao.BaseDao;
import com.traffic.weizhang.entity.TSubscribe;

/**
 * 订阅
 * @author Administrator
 *
 */
@Repository("subscribeDao")
public class SubscribeDao extends  BaseDao {

	@SuppressWarnings("unchecked")
	public List<TSubscribe> queryForCheck(Map<String, String> mapparam){
		return super.getSqlMapClientTemplate().queryForList(QueryHistoryDao.class.getName()+ ".queryForCheck",mapparam);
	};
}
