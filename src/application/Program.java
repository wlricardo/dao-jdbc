package application;

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		SellerDAO sellerDao = new DaoFactory().createSellerDAO();

		System.out.println("==== TEST 1 : Seller find by Id ====");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
	}
}
