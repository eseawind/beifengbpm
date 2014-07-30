package com.beifengbpm.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.beifengbpm.dao.CommonDao;
import com.beifengbpm.entity.Department;
import com.beifengbpm.service.DepartmentService;

public class DepartmentServiceImpl implements DepartmentService{

	private CommonDao commonDao;
	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	@Override
	public void addDepartment(Department depart) throws SQLException {
		commonDao.add(depart);
		
	}

	@Override
	public List<Department> queryAllDeparts() throws SQLException {
		String hql = "FROM DEPARTMENT";
		return (List<Department>) commonDao.queryList(hql);
	}

	@Override
	public Department queryDepartmentById(String departmentId)
			throws SQLException {
		String hql = "FROM DEPARTMENT WHERE DEPARTMENTID=?";
		return (Department) commonDao.queryObject(hql,new String[]{departmentId});
	}

	@Override
	public void updateDepartment(Department department) throws SQLException {
		commonDao.update(department);
		
	}

	@Override
	public boolean ishaveChildrenDepartment(String parentId)
			throws SQLException {
		boolean flag = false;
		String hql = "FROM DEPARTMENT WHERE PARENTID=?";
		List<Department> list = (List<Department>) commonDao.queryList(hql,new String[]{parentId});
		if(list!=null && list.size()>0){
			flag = true;
		}
		return flag;
	}

	@Override
	public void deleteDepartment(Department department) throws SQLException {
		commonDao.del(department);
		
	}
	
}
