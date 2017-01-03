package com.gnd.oa.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogTest {
	private static Log log = LogFactory.getLog(LogTest.class);

	public static void main(String[] args) {
		log.debug("这是debug信息");
		log.info("这是info信息");
		log.warn("这是warn信息");
		log.error("这是error信息");
		log.fatal("这是fatal信息");
	}
}
