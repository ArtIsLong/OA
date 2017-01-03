package com.gnd.oa.test;

import java.util.regex.Pattern;

import org.junit.Test;

public class BaseTest {

	@Test
	public void test01() throws Exception{
		String reg = "[a-zA-Z0-9]{0,2}";
		System.out.println(Pattern.matches(reg, "-'d"));
	}
}
