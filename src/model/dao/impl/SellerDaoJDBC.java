package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDAO {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller dp) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement(""
					+ "INSERT INTO seller\r\n"
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId)\r\n" 
					+ "VALUES\r\n" + "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, dp.getName());
			st.setString(2, dp.getEmail());
			st.setDate(3, new java.sql.Date(dp.getBirthDate().getTime()));
			st.setDouble(4, dp.getBaseSalary());
			st.setInt(5, dp.getDepartment().getId());

			int rows = st.executeUpdate();
			if (rows > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					dp.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error. No rows affected !");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Seller dp) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement(""
					+ "UPDATE seller SET Name = ?, Email = ?,	BirthDate = ?, BaseSalary = ?, DepartmentId = ? \r\n"
					+ "WHERE Id = ?");

			st.setString(1, dp.getName());
			st.setString(2, dp.getEmail());
			st.setDate(3, new java.sql.Date(dp.getBirthDate().getTime()));
			st.setDouble(4, dp.getBaseSalary());
			st.setInt(5, dp.getDepartment().getId());
			st.setInt(6, dp.getId());

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deletById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(""
					+ "DELETE FROM seller\r\n" 
					+ "WHERE Id = ?");

			st.setInt(1, id);
			st.executeUpdate();
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(""
					+ "SELECT department.*,department.Name as DepName\r\n"
					+ "FROM seller INNER JOIN department\r\n"
					+ "ON seller.DepartmentId = department.Id\r\n"
					+ "WHERE seller.Id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Department dp = instantiateDepartment(rs);
				Seller sl = instantiateSeller(rs, dp);
				return sl;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	private Seller instantiateSeller(ResultSet rs, Department dp) throws SQLException {
		Seller sl = new Seller();
		sl.setId(rs.getInt("Id"));
		sl.setName(rs.getString("Name"));
		sl.setEmail(rs.getString("Email"));
		sl.setBaseSalary(rs.getDouble("BaseSalary"));
		sl.setBirthDate(rs.getDate("BirthDate"));
		sl.setDepartment(dp);
		return sl;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dp = new Department();
		dp.setId(rs.getInt("DepartmentId"));
		dp.setName(rs.getString("DepName"));
		return dp;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"" + "SELECT seller.*,department.Name as DepName\n" + "FROM seller INNER JOIN department\n"
							+ "ON seller.DepartmentId = department.Id\n" + "ORDER BY Name");

			rs = st.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller sl = instantiateSeller(rs, dep);
				list.add(sl);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department dp) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("" + "SELECT seller.*,department.Name as DepName\n"
					+ "FROM seller INNER JOIN department\n" + "ON seller.DepartmentId = department.Id\n"
					+ "WHERE DepartmentId = ?\n" + "ORDER BY Name");

			st.setInt(1, dp.getId());
			rs = st.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller sl = instantiateSeller(rs, dep);
				list.add(sl);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}
}
