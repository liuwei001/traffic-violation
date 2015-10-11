package com.traffic.weizhang.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traffic.common.base.dao.BaseDao;
import com.traffic.common.base.service.impl.BaseServiceImpl;
import com.traffic.weizhang.dao.SubscribeDao;
import com.traffic.weizhang.entity.TSubscribe;
import com.traffic.weizhang.service.ISubscribeService;

@Service("subscribeService")
public class SubscribeServiceImpl extends BaseServiceImpl implements ISubscribeService{
	
	@Autowired
	private SubscribeDao subscribeDao;

	@Override
	public BaseDao getDao() {
		// TODO Auto-generated method stub
		return subscribeDao;
	}

	public List<TSubscribe> queryForCheck(Map<String, String> mapparam) {
		return subscribeDao.queryForCheck(mapparam);
	}
}
