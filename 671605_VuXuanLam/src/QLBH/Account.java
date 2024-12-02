package QLBH;


public class Account {
	private  String userName;
	private String passWord;
	private Integer role;
	public Account() {

	}
	public Account(String userName,String passWord) {
		this.userName=userName;
		this.passWord= passWord;
	}

	public Account(String userName,String passWord,Integer role) {
		this.userName=userName;
		this.passWord= passWord;
		this.role= role;
	}

	public String getUserName() {

		return userName;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public  void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public boolean equals(Account account) {
		if(this.getUserName().equals(account.getUserName()) && this.getPassWord().equals(account.getPassWord()) ) {
			return true;
		}
		return false;
	}
}
