package QLBH;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class accountDAO {
	public accountDAO() {}
	private List<Account> accountList = new ArrayList<Account>();

	public List<Account> listALLaccount() {
		List<Account> listAccount = new ArrayList<>();
		String sql = "SELECT* FROM tbluser";

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connection = DriverManager.getConnection("jdbc:ucanaccess://lib/account.accdb", "", "");
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery(sql);

			while (resultset.next()) {
				String username = resultset.getString("username");
				String password = resultset.getString("password");
				Integer role = resultset.getInt("role");
				Account Account = new Account(username, password,role);
				listAccount.add(Account);
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

		return listAccount;
	}
	public boolean checkAccount(Account account) {
		accountList = listALLaccount();
		for(Account ac : accountList) {
			if(ac.equals(account)) {
				return true;
			}
		}
		return false;
	}
	public boolean addAccount(Account account) {
		String URL = "jdbc:ucanaccess://lib/account.accdb";
		String dbUser = "";
		String dbPassWord = "";

		boolean rowInserted = false;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connection = DriverManager.getConnection(URL, dbUser, dbPassWord);
			String sql = "INSERT INTO tbluser(username,password,role) VALUES(?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, account.getUserName());
			statement.setString(2, account.getPassWord());
			statement.setInt(3, account.getRole());

			rowInserted = statement.executeUpdate() > 0;
			statement.close();
			connection.close();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rowInserted;
	}
	public Account checkAccount(String username) {
		String URL = "jdbc:ucanaccess://lib/account.accdb";
		String dbUser = "";
		String dbPassWord = "";

		Account account = null;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connection = DriverManager.getConnection(URL, dbUser, dbPassWord);
			String sql = "SELECT*FROM tbluser WHERE username=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet resultset = statement.executeQuery();
			account = null;
			if (resultset.next()) {
				account = new Account();
				account.setPassWord(resultset.getString("password"));
				account.setUserName(username);
			}
			statement.close();
			resultset.close();
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return account;
	}
	@Override
	public String toString() {
		String accountListStr = "";
		List<Account> accountList = new accountDAO().listALLaccount();
		for (Account user : accountList) {
			accountListStr += user.getUserName() + "\n";
		}
		return accountListStr;
	}

}
