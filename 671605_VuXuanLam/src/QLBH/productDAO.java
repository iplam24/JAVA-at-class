package QLBH;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class productDAO {
	public productDAO() {}


	public static Connection connectionPRD(){
		String URL = "jdbc:ucanaccess://lib/product1.accdb";
		String dbUser = "";
		String dbPassWord = "";
		Connection connection = null;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			connection = DriverManager.getConnection(URL, dbUser, dbPassWord);
			return connection;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	public List<Product> listALLProducts() {
		List<Product> listProducts = new ArrayList<>();
		String sql = "SELECT codeprd,nameprd,memoryprd,requireprd,priceprd,img_path from tblproduct"; 
		String jdbURL="jdbc:ucanaccess://lib/product1.accdb";


		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connection = DriverManager.getConnection(jdbURL, "", "");

			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery(sql);

			while (resultset.next()) {
				String codeprd = resultset.getString("codeprd");
				String nameprd = resultset.getString("nameprd");
				String memoryprd = resultset.getString("memoryprd");
				String requireprd = resultset.getString("requireprd");
				String priceprd = resultset.getString("priceprd");
				String imgpath= resultset.getString("img_path");
				Product Product = new Product(codeprd, nameprd, memoryprd, requireprd, priceprd,imgpath);
				listProducts.add(Product);
			}
			statement.close();
			resultset.close();
			connection.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Lỗi không tìm thấy driver");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Sai câu truy vấn");
		}

		return listProducts;
	}

	@Override
	public String toString() {
		String productListStr = "";
		List<Product> productList = new productDAO().listALLProducts();
		for (Product product : productList) {
			productListStr += product.getCodeprd() +  product.getNameprd() + product.getMemoryprd()+ product.getRequireprd()+ product.getPriceprd()+product.getImg_path();
		}
		return productListStr;
	}

	public Product checkCode(String codeprd) {

		Product product = null;
		String sql = "SELECT * FROM tblproduct WHERE codeprd=?";
		try {
			Connection connection = connectionPRD();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, codeprd);
			ResultSet resultset = statement.executeQuery();

			if (resultset.next()) {
				product = new Product();
				product.setNameprd(resultset.getString("nameprd"));
				product.setMemoryprd(resultset.getString("memoryprd"));
				product.setRequireprd(resultset.getString("requireprd"));
				product.setPriceprd(resultset.getString("priceprd"));
				product.setCodeprd(codeprd);

			}
			statement.close();
			resultset.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
	}



	public boolean addProduct(Product product) {
		String URL = "jdbc:ucanaccess://lib/product1.accdb";
		String dbUser = "";
		String dbPassWord = "";

		boolean rowInserted = false;

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connection = DriverManager.getConnection(URL, dbUser, dbPassWord);
			String sql = "INSERT INTO tblproduct(codeprd,nameprd,memoryprd,requireprd,priceprd) VALUES(?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, product.getCodeprd());
			statement.setString(2, product.getNameprd());
			statement.setString(3, product.getMemoryprd());
			statement.setString(4, product.getRequireprd());
			statement.setString(5, product.getPriceprd());
			rowInserted = statement.executeUpdate() > 0;
			statement.close();
			connection.close();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rowInserted;
	}
	public boolean updates(Product product) {
		boolean result = false;
		String sql = "UPDATE tblproduct SET nameprd=?, memoryprd=?, requireprd=?, priceprd=? WHERE codeprd=?";
		try {
			Connection connection = connectionPRD();
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, product.getNameprd());
			statement.setString(2, product.getMemoryprd());
			statement.setString(3, product.getRequireprd());
			statement.setString(4, product.getPriceprd());
			statement.setString(5, product.getCodeprd());
			int rowsUpdated = statement.executeUpdate();
			result = (rowsUpdated == 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public boolean Delete(String codeprd) {
		boolean result = false;
		String sql = "DELETE FROM tblproduct WHERE codeprd = ?";
		try {
			Connection connection = connectionPRD();
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, codeprd);
			int rowsDeleted = statement.executeUpdate();
			result = (rowsDeleted == 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return result;

	}
}



