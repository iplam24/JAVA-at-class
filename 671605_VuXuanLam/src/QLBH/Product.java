package QLBH;


public class Product {
	private String codeprd;
	private String nameprd;
	private String memoryprd;
	private String requireprd;
	private String priceprd;
	private String img_path;

	public Product() {

	}


	public Product(String codeprd,String nameprd,String memoryprd,String requireprd,String priceprd) {
		this.codeprd=codeprd;
		this.nameprd=nameprd;
		this.memoryprd=memoryprd;
		this.requireprd=requireprd;
		this.priceprd=priceprd;
	}
	public Product(String codeprd,String nameprd,String memoryprd,String requireprd,String priceprd,String img_path) {
		this.codeprd=codeprd;
		this.nameprd=nameprd;
		this.memoryprd=memoryprd;
		this.requireprd=requireprd;
		this.priceprd=priceprd;
		this.img_path=img_path;
	}


	public String getImg_path() {
		return img_path;
	}
	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}
	public String getCodeprd() {
		return codeprd;
	}


	public void setCodeprd(String codeprd) {
		this.codeprd = codeprd;
	}


	public String getNameprd() {
		return nameprd;
	}


	public void setNameprd(String nameprd) {
		this.nameprd = nameprd;
	}


	public String getMemoryprd() {
		return memoryprd;
	}


	public void setMemoryprd(String memoryprd) {
		this.memoryprd = memoryprd;
	}


	public String getRequireprd() {
		return requireprd;
	}


	public void setRequireprd(String requireprd) {
		this.requireprd = requireprd;
	}


	public String getPriceprd() {
		return priceprd;
	}


	public void setPriceprd(String priceprd) {
		this.priceprd = priceprd;
	}


}
