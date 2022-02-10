package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public SellerDAO createSellerDAO() {
		return new SellerDaoJDBC(DB.getConnection());
	}

	public DepartmentDAO createDepartmentDAO() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
}
