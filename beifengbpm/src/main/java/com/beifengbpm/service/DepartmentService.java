package com.beifengbpm.service;

import java.sql.SQLException;
import java.util.List;

import com.beifengbpm.entity.Department;

public interface DepartmentService {
	
		public void addDepartment(Department depart) throws SQLException;
		
		public List<Department> queryAllDeparts() throws SQLException;
		
		public Department queryDepartmentById(String departmentId) throws SQLException;
		
		public void updateDepartment(Department department) throws SQLException;
		
		public boolean ishaveChildrenDepartment(String parentId) throws SQLException;
		
		public void deleteDepartment(Department department) throws SQLException;
}
