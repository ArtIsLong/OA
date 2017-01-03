package com.gnd.oa.service;

import java.util.List;

import com.gnd.oa.domain.Department;

public interface DepartmentService {

	/**
	 * 查询所有部门
	 * @return List<Department>
	 */
	public List<Department> findAll();

	/**
	 * 根据id删除
	 * @param id
	 */
	public void delete(Long id);

	/**
	 * 保存部门
	 * @param model
	 */
	public void save(Department model);

	/**
	 * 根据id查询部门
	 * @param id
	 * @return Department
	 */
	public Department getById(Long id);

	/**
	 * 更新部门数据
	 * @param department
	 */
	public void update(Department department);

	/**
	 * 查询顶级部门列表
	 * @return List<Department>
	 */
	public List<Department> findTopList();

	/**
	 * 查询子部门列表
	 * @param parentId
	 * @return List<Department>
	 */
	public List<Department> findChildren(Long parentId);

}
