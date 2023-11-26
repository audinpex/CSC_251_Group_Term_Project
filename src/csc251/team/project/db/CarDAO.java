package csc251.team.project.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import DatabasesConnection.DatabasesConection;

//Bear bones ouline.... not done.
public class CarDAO {
	public List<Product> findAll() {
		try {
			List<Product> listProducts = new ArrayList<>();
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT * from product");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = (rs.getString("name"));
				Double price= rs.getDouble("price");
				String description =rs.getString("description");
				int quantity = rs.getInt("quantity");
				Product p = new Product(id, name,price, quantity, description);
				listProducts.add(p);
			}
			return listProducts;
		} catch (Exception e) {
			return null;
		}
	}			//  This is just outline, not done at all...

	public Product find(int id) {
		try {
			Product p = null;
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT * from product WHERE id=?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				//int id = rs.getInt("id");
				String name = (rs.getString("name"));
				Double price= rs.getDouble("price");
				String description =rs.getString("description");
				int quantity = rs.getInt("quantity");
				p = new Product(id, name,price, quantity, description);
			}
			return p;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean create(Product p) {
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(
					"INSERT INTO product(name,price,quantity,description) values(?,?,?,?);");
			ps.setString(1, p.getName());
			ps.setDouble(2, p.getPrice());
			ps.setInt(3, p.getQuantity());
			ps.setString(4, p.getDescription());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean edit(Product p) {
		try {
			PreparedStatement ps = DatabaseConnection.getConnection()
					.prepareStatement("UPDATE product set name=?,price=?,quantity=?,description=?" + " where id=?");
			ps.setString(1, p.getName());
			ps.setDouble(2, p.getPrice());
			ps.setInt(3, p.getQuantity());
			ps.setString(4, p.getDescription());
			ps.setInt(5, p.getId());
			//System.out.println(ps.toString());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delete(Product p) {
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("DELETE FROM product where id=?");
			ps.setInt(1, p.getId());
			//System.out.println(ps.toString());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			return false;
		}
	}
}

}
