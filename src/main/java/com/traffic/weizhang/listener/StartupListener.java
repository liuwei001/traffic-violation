package com.traffic.weizhang.listener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 启动监听器
 * @author Administrator
 *
 */
@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent>{

	private final Logger logger = Logger.getLogger(StartupListener.class);
	
	@Override
    public void onApplicationEvent(ContextRefreshedEvent evt) {
        if (evt.getApplicationContext().getParent() == null) {
        	logger.info("启动监听器，初始化加载开始..............");
        	SuppliersLoader.init();
        	logger.info("启动监听器，初始化加载结束..............");
        }
    }
}
