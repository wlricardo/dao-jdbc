package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDAO {
	
	void insert(Department dp);
	void update(Department dp);
	void deletById(Integer id);
	Department findById(Integer id);
	List<Department> findAll();
}
