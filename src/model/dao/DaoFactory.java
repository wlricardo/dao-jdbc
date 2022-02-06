package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public SellerDAO createSellerDAO() {
		return new SellerDaoJDBC(DB.getConnection());
	}
}
