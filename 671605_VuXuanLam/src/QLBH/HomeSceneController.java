package QLBH;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomeSceneController  implements Initializable {

	//Khai bao toan bo thong tin id lien quan toi fxml
	@FXML
	private Label nameLabel;

	@FXML
	private ImageView imgView;

	@FXML
	private TextField codeTF;

	@FXML
	private TextField nameTF;

	@FXML
	private TextField memoryTF;

	@FXML
	private TextField requireTF;

	@FXML
	private TextField priceTF;

	@FXML 
	private Label ppLabel;

	@FXML
	private Button addbtnn;

	@FXML
	private Button deleteBtnn;

	@FXML
	private Button repairBtnn;

	@FXML
	private Button changeImgg;


	@FXML
	private TableView<Product> tableView;

	@FXML
	private TableColumn<Product, String> codeColumn;
	@FXML
	private TableColumn<Product, String> nameColumn;
	@FXML
	private TableColumn<Product, String> memoryColumn;
	@FXML
	private TableColumn<Product, String> requireColumn;
	@FXML
	private TableColumn<Product, String> priceColumn;
	@FXML 
	private TableColumn<Product, String> pathColumn;
	@FXML
	private ImageView rotateImage;

	//FXML


	//Khai bao productDAO
	private productDAO pd = new productDAO(); 
	private FileChooser fc ;
	private File fp;
	private String productList;
	private Account accountLogin;


	public Account getAccountLogin() {
		return accountLogin;
	}

	public void setAccountLogin(Account accountLogin) {
		this.accountLogin = accountLogin;

	}

	private ObservableList<Product> obList;

	public String getProductList() {
		return productList;
	}

	public void setProductList(String productList) {
		this.productList = productList;
	}

	public ImageView getImgView() {
		return imgView;
	}

	public void setImgView(ImageView imgView) {
		this.imgView = imgView;
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//animation
		RotateTransition rotatet = new RotateTransition();
		rotatet.setNode(rotateImage);
		rotatet.setDuration(Duration.millis(5000));
		rotatet.setCycleCount(TranslateTransition.INDEFINITE);
		rotatet.setInterpolator(Interpolator.LINEAR);
		rotatet.setByAngle(360);
		rotatet.setAxis(rotatet.getAxis());
		rotatet.play();
		//animation 

		//Set timeline cho pp label
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.5),e->{
			ppLabel.setText(" ");
		})); 
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();


		//Dua du lieu cho table view va lay ten username cua account
		Platform.runLater(()->{

			nameLabel.setText(accountLogin.getUserName());
			setupRole();
			productDAO pd = new productDAO(); 
			List<Product> productList = pd.listALLProducts();
			codeColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("codeprd"));
			nameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("nameprd"));
			memoryColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("memoryprd"));
			requireColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("requireprd"));
			priceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("priceprd"));
			pathColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("img_path"));
			obList = FXCollections.observableArrayList(productList);
			//obList.add(new Product());
			tableView.setItems(obList); 
		});
	}





	//Hyper link dang xuat va mo lai menu dang nhap
	public void logoutLink() {
		imgView.getScene().getWindow().hide();
		try {
			// Tạo FXMLLoader tương ứng HomeScene.fxml
			FXMLLoader fxmlLoader = new
					FXMLLoader(getClass().getResource("login.fxml"));
			// Lấy về đối tượng root layout
			Parent root = (Parent)fxmlLoader.load();
			Stage loginStage = new Stage();
			loginStage.setTitle("LOGIN SCREEN");
			loginStage.setScene(new Scene(root));
			loginStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}





	//Su kien cho nut ChangeIMG
	public void btImages(ActionEvent e){

		Stage stage = (Stage) ((Node)(e.getSource())).getScene().getWindow();
		fc = new FileChooser();
		fc.setTitle("Open Image");

		String userDirectoryString = "C:";
		File userDirectory = new File(userDirectoryString);
		if(!userDirectory.canRead()) {
			userDirectory = new File("C:");
		}
		fc.setInitialDirectory(userDirectory);
		this.fp = fc.showOpenDialog(stage);

		try {
			BufferedImage butfferedImage = ImageIO.read(fp);
			Image image = SwingFXUtils.toFXImage(butfferedImage, null);
			imgView.setImage(image);

		} catch (Exception e2) {
			// TODO: handle exception
		}


	}


	//them path ảnh vào album và csdl
	public void saveImage() throws IOException {
		Path imgDir = Paths.get("C:\\Users\\vxlam\\Downloads\\VuXuanLam_671605\\VuXuanLam_671605\\671605_VuXuanLam\\imageSave");

		if (!Files.exists(imgDir)) {
			// Tạo thư mục img nếu không tồn tại
			Files.createDirectories(imgDir);
		}

		// Đổi tên ảnh theo tên tệp gốc
		String newFileName = fp.getName();
		Path targetPath = imgDir.resolve(newFileName);
		if (Files.exists(targetPath)) {
			System.out.println("File already exists. Cannot overwrite.");
		} else {
			Files.copy(fp.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

			String sql ="UPDATE tblproduct SET img_path=? WHERE codeprd=?";
			try {
				Connection conncection = productDAO.connectionPRD();
				PreparedStatement statement = conncection.prepareStatement(sql);
				statement.setString(1, targetPath.toString()); // img_path
				statement.setString(2, codeTF.getText()); // codeprd (cần lấy từ trường codeTF)

				// Thực hiện truy vấn cập nhật
				int rowsUpdated = statement.executeUpdate();
				if (rowsUpdated > 0) {
					System.out.println("add");

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}



	//Su kien cho nut add
	public void addBtn() throws IOException {

		String codetF=codeTF.getText();
		String nametF=nameTF.getText();
		String memorytF=memoryTF.getText();
		String requiretF=requireTF.getText();
		String pricetF=priceTF.getText();
		String img_path =null;
		Product product = null;
		product = new Product(codetF,nametF,memorytF,requiretF,pricetF,img_path);
		if(pd.checkCode(codetF) != null) {
			ppLabel.setText("Product does exits!");

		}else {
			pd.addProduct(product);
			//Neu mà chọn ảnh thì thêm ảnh, không chọn ảnh thì chỉ thêm sản phẩm bình thường.
			if (fp != null) {
				img_path="C:\\Users\\vxlam\\Downloads\\VuXuanLam_671605\\VuXuanLam_671605\\671605_VuXuanLam\\imageSave\\"+fp.getName();
				// Nếu đã chọn ảnh, thì lưu ảnh
				saveImage();
			}else {
				System.out.println("ADDBTN:!!!Can't find image");
			}
			tableView.getItems().add(product);
			ppLabel.setText("Add product successfully!");
		}


	}



	//Su kien cho nut DELETE
	public void deleteBtn() throws ClassNotFoundException, SQLException {

		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
		Product prd = tableView.getItems().get(selectedIndex);
		if(selectedIndex >=0) {
			String imgPath = prd.getImg_path();
			Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
			confirmationAlert.setTitle("Confirm Deletion");
			confirmationAlert.setHeaderText(null);
			confirmationAlert.setContentText("Are you sure you want to delete this product?");


			ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);
			if (result == ButtonType.OK) {
				boolean deleteR = pd.Delete(prd.getCodeprd());
				if(deleteR) {
					if (imgPath != null && !imgPath.isEmpty()){
						try {
							Path pathToDelete = Paths.get(imgPath);
							Files.delete(pathToDelete);
							System.out.println("Image deleted successfully from album.");

						} catch (IOException e) {
							ppLabel.setText("Error deleting image from album.");
						}
					}else {
						System.out.println("DELETE:!!!!Can't find image from album");
					}
					tableView.getItems().remove(selectedIndex);
					ppLabel.setText("DELETE sucessfull!");
					showAlert(Alert.AlertType.INFORMATION, "Success", "Product deleted successfully.");
				}else {
					ppLabel.setText("DELETE fail!");

					showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete product.");
				}
			}
		}

	}



	//Su kien cho nut sua
	public void repairBtn() throws IOException {
		//lay ve so dong o table view
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

		if(selectedIndex>=0) {
			Product product = tableView.getItems().get(selectedIndex);
			String code = codeTF.getText();
			String name = nameTF.getText();
			String memory = memoryTF.getText();
			String require = requireTF.getText();
			String price = priceTF.getText();
			String imgPath = product.getImg_path();

			if (imgPath != null && !imgPath.isEmpty()){
				try {
					Path pathToDelete = Paths.get(imgPath);
					Files.delete(pathToDelete);
					System.out.println("Image deleted successfully from album.");
				} catch (IOException e) {
					ppLabel.setText("Error deleting image from album.");
				}
			}else {
				System.out.println("DELETE:!!!!Can't find image from album");
			}

			product.setCodeprd(code);
			product.setNameprd(name);
			product.setMemoryprd(memory);
			product.setRequireprd(require);
			product.setPriceprd(price);
			boolean updates = pd.updates(product);


			if (fp != null) {
				// Nếu đã chọn ảnh, thì lưu ảnh
				saveImage();
				product.setImg_path(fp.getPath());
			}else {
				System.out.println("REPAIR:!!!!Can't find image");
			}
			if(updates) {
				tableView.getItems().set(selectedIndex, product);
				ppLabel.setText("Repair sucessfull!");

			}else {
				ppLabel.setText("Repair fail!");
			}

		}else {
			ppLabel.setText("Choose product to repair");
		}

	}



	//Ham an  vao hang cua tableView de xem thong tin cua tableView
	public void onClicktoShow() {
		// Lấy chỉ mục của mục được chọn
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

		// Kiểm tra nếu có mục được chọn
		if (selectedIndex >= 0) {
			// Lấy mục được chọn
			Product selectedProduct = tableView.getSelectionModel().getSelectedItem();

			// Cập nhật các trường TextField dựa trên mục được chọn
			if (selectedProduct != null) {
				codeTF.setText(selectedProduct.getCodeprd());
				nameTF.setText(selectedProduct.getNameprd());
				memoryTF.setText(selectedProduct.getMemoryprd());
				requireTF.setText(selectedProduct.getRequireprd());
				priceTF.setText(selectedProduct.getPriceprd());
				String imagePath = selectedProduct.getImg_path();
				if (imagePath != null && !imagePath.isEmpty()) {
					try {
						// Tạo một đối tượng Image từ đường dẫn hình ảnh
						Image imgToSet = new Image(new File(imagePath).toURI().toString());
						imgView.setImage(imgToSet);
					} catch (Exception e) {
						// Xử lý nếu có lỗi xảy ra khi tạo Image từ đường dẫn
						e.printStackTrace();
					}
				}else {
					System.out.println("Can't find image");
				}

			}
		} else {
			// Không có mục nào được chọn, xử lý trường hợp này theo yêu cầu
		}

	}



	//Phan quyen role cho ADMIN va USER
	private void setupRole() {
		// Kiểm tra vai trò của tài khoản
		Integer role = accountLogin.getRole();

		// Ẩn hoặc hiện các nút dựa trên vai trò
		if (role == 0) {
			// Admin có quyền truy cập tất cả các nút
			addbtnn.setVisible(true);
			deleteBtnn.setVisible(true);
			repairBtnn.setVisible(true);
			changeImgg.setVisible(true);
		} else {
			// Vai trò khác không có quyền truy cập các nút
			addbtnn.setVisible(false);
			deleteBtnn.setVisible(false);
			repairBtnn.setVisible(false);
			changeImgg.setVisible(false);
		}
	}

	private void showAlert(AlertType alertType, String title, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}


}
