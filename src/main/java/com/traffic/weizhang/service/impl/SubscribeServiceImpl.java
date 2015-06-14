package com.traffic.weizhang.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traffic.common.base.mapper.BaseMapper;
import com.traffic.common.base.service.impl.BaseServiceImpl;
import com.traffic.weizhang.entity.TSubscribe;
import com.traffic.weizhang.mapper.SubscribeMapper;
import com.traffic.weizhang.service.ISubscribeService;

@Service("subscribeService")
public class SubscribeServiceImpl extends BaseServiceImpl implements ISubscribeService{
	
	@Autowired
	private SubscribeMapper subscribeMapper;

	@Override
	public BaseMapper getMapper() {
		// TODO Auto-generated method stub
		return subscribeMapper;
	}

	public List<TSubscribe> queryForCheck(Map<String, String> mapparam) {
		return subscribeMapper.queryForCheck(mapparam);
	}
}
