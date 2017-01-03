package com.gnd.oa.memcache.policy;

import java.util.LinkedList;

@SuppressWarnings("rawtypes")
public class RoundRobinPolicy extends LinkedList implements IPolicy {
	private static final long serialVersionUID = 1L;
	private int position;

	public synchronized Object getPolicyObject() throws Exception {
		if (this.position >= super.size() - 1) {
			this.position = 0;
		} else {
			this.position += 1;
		}

		return super.get(this.position);
	}

	public void clear() {
		super.clear();
	}
}