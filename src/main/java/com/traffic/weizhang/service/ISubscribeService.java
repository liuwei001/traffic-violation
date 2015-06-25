package com.traffic.weizhang.service;

import java.util.List;
import java.util.Map;

import com.traffic.common.base.service.IBaseService;
import com.traffic.weizhang.entity.TSubscribe;

public interface ISubscribeService extends IBaseService{

	public List<TSubscribe> queryForCheck(Map<String, String> mapparam);
}
