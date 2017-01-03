package com.gnd.oa.memcache.impl;

import java.util.Properties;

import com.danga.MemCached.MemCachedClient;
import com.gnd.oa.memcache.interfaces.IValidate;

public class NoNeedValidateImpl implements IValidate {

	public boolean validate(MemCachedClient client, Properties properties)
			throws Exception {
		return true;
	}
}