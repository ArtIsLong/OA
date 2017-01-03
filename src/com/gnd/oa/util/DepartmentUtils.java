package com.gnd.oa.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.gnd.oa.domain.Department;

public class DepartmentUtils {

	//遍历部门树,把所有的部门遍历出来放到同一个集合中返回,并且其中所有部门的名称都修改了,以表示层次
	public static List<Department> getAllDepartments(List<Department> topList) {
		List<Department> list = new ArrayList<Department>();
		walkDepartmentTreeList(topList,"┣",list);
		return list;
	}
	
	//遍历部门树,把遍历出来的部门信息放到指定的集合中
	private static void walkDepartmentTreeList(Collection<Department> topList,String prefix,List<Department> list){
		for(Department top:topList){
			//顶点
			Department copy = new Department();
			copy.setId(top.getId());
			copy.setName(prefix + top.getName());
			list.add(copy);
			walkDepartmentTreeList(top.getChildren(),"　" + prefix,list);
		}
	}

}
