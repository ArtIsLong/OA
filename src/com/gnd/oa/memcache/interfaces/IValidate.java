package com.gnd.oa.memcache.interfaces;

import java.util.Properties;

import com.danga.MemCached.MemCachedClient;

public abstract interface IValidate
{
  public abstract boolean validate(MemCachedClient client, Properties properties)
    throws Exception;
}