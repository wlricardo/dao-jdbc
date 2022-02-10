package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		SellerDAO sellerDao = new DaoFactory().createSellerDAO();

		System.out.println("==== TEST 1 : Seller findById ====");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);

		System.out.println("\n==== TEST 2 : Seller findById ====");
		Department dp = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(dp);
		for (Seller s : list) {
			System.out.println(s);
		}

		System.out.println("\n==== TEST 3 : Seller findAll ====");
		list = sellerDao.findAll();
		for (Seller s : list) {
			System.out.println(s);
		}

		System.out.println("\n==== TEST 4 : Seller insert ====");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, dp);
		sellerDao.insert(newSeller);
		System.out.println("New seller inserted with Id : " + newSeller.getId());
	}
}
