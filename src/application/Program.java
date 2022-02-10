package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		SellerDAO sellerDao = new DaoFactory().createSellerDAO();

		System.out.println("==== TEST 1 : Seller findById ====");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		System.out.println("--------------------------------");

		System.out.println("\n==== TEST 2 : Seller findById ====");
		Department dp = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(dp);
		for (Seller s : list) {
			System.out.println(s);
		}
		System.out.println("--------------------------------");

		System.out.println("\n==== TEST 3 : Seller findAll ====");
		list = sellerDao.findAll();
		for (Seller s : list) {
			System.out.println(s);
		}
		System.out.println("--------------------------------");

		System.out.println("\n==== TEST 4 : Seller insert ====");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, dp);
		sellerDao.insert(newSeller);
		System.out.println("New seller inserted with Id : " + newSeller.getId());
		System.out.println("--------------------------------");

		System.out.println("\n==== TEST 5 : Seller update ====");
		seller = sellerDao.findById(1);
		seller.setName("John Orange");
		sellerDao.update(seller);
		System.out.println("Update completed !");
		System.out.println("--------------------------------");

		System.out.println("\n==== TEST 6 : Seller deleteById ====");
		System.out.print("Enter ID for delete test: ");
		int id = sc.nextInt();
		sellerDao.deletById(id);
		System.out.println("Delete completed !");
		System.out.println("--------------------------------");

		sc.close();
	}
}
