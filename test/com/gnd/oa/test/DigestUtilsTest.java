package com.gnd.oa.test;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class DigestUtilsTest {

	@Test
	public void test01(){
		String md5Hex = DigestUtils.md5Hex("123456");
		System.out.println(md5Hex);
	}
}
