package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		DepartmentDAO departmentDAO = new DaoFactory().createDepartmentDAO();

		System.out.println("==== TEST 1 : Department findById ====");
		Department department = departmentDAO.findById(2);
		System.out.println(department);
		System.out.println("--------------------------------");

		System.out.println("\n=== TEST 2: Department findAll =======");
		List<Department> list = departmentDAO.findAll();
		for (Department d : list) {
			System.out.println(d);
		}
		System.out.println("--------------------------------");

		System.out.println("\n==== TEST 3 : Department insert ====");
		Department newDepartment = new Department(null, "Geek");
		departmentDAO.insert(newDepartment);
		System.out.println("New department inserted with Id : " + newDepartment.getId());
		System.out.println("--------------------------------");

		System.out.println("\n==== TEST 4 : Department update ====");
		Department dep = departmentDAO.findById(1);
		dep.setName("Nerd");
		departmentDAO.update(dep);
		System.out.println("Update completed !");
		System.out.println("--------------------------------");

		System.out.println("\n==== TEST 5 : Department deleteById ====");
		System.out.print("Enter ID for delete test: ");
		int id = sc.nextInt();
		departmentDAO.deletById(id);
		System.out.println("Delete completed !");
		System.out.println("--------------------------------");
		
		sc.close();
	}
}
