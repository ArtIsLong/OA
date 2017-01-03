package com.gnd.oa.memcache.policy;

public abstract interface IPolicy {
	public abstract Object getPolicyObject() throws Exception;

	public abstract boolean add(Object paramObject);

	public abstract boolean remove(Object paramObject);

	public abstract void clear();

	public abstract int size();

	public abstract Object[] toArray();

	public abstract boolean contains(Object paramObject);
}