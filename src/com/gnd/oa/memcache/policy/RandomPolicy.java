package com.gnd.oa.memcache.policy;

import java.util.LinkedList;

@SuppressWarnings("rawtypes")
public class RandomPolicy extends LinkedList
  implements IPolicy
{
  private static final long serialVersionUID = 1L;

public Object getPolicyObject()
    throws Exception
  {
    return super.get(getRandom(0, super.size() - 1));
  }

  public Object[] toArrsy() {
    return super.toArray();
  }

  public void clear() {
    super.clear();
  }

  private int getRandom(int min, int max)
  {
    return (min + (int)(Math.random() * (max - min + 1)));
  }
}