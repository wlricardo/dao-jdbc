package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDAO {
	
	void insert(Seller dp);
	void update(Seller dp);
	void deletById(Seller dp);
	Seller findById(Integer id);
	List<Seller> findAll();

}
