package com.traffic.common.exception;

import org.apache.log4j.Logger;

/**
 * 
 * @Description:持久层异常
 * @Copyright Copyright 2014-2015 traffic Tech. Co. Ltd. All Rights
 *            Reserved.<BR>
 * @author： liuwei <BR>
 * @Time： 2015年1月27日 <BR>
 * @version 1.0.0 <BR>
 */
public class DalException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(DalException.class);

	private Long code = 0l;
	
	private Exception exception;
	
	public DalException(Exception exception) {
		this.exception = exception;
		logger.error(exception);
	}

	public DalException(Long code, Exception exception) {
		this.code = code;
		this.exception = exception;
		logger.error(exception);
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}
}
