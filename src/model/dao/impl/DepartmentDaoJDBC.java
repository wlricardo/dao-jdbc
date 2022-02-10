package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDAO {

	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department dp) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("" + "INSERT INTO department (Name) VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, dp.getName());
			int rows = st.executeUpdate();

			if (rows > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					dp.setId(id);
				}
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
	public void update(Department dp) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("" + "UPDATE department SET Name = ? \r\n" + "WHERE Id = ?");

			st.setString(1, dp.getName());
			st.setInt(2, dp.getId());

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
			st = conn.prepareStatement("" + "DELETE FROM department\r\n" + "WHERE Id = ?");

			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("" + "SELECT * FROM department\r\n" + "WHERE Id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Department dp = new Department();
				dp.setId(rs.getInt("Id"));
				dp.setName(rs.getString("Name"));
				return dp;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * FROM department ORDER BY Name");
			rs = st.executeQuery();

			List<Department> list = new ArrayList<Department>();
			while (rs.next()) {
				Department dp = new Department();
				dp.setId(rs.getInt("Id"));
				dp.setName(rs.getString("Name"));
				list.add(dp);
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
