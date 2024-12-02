package QLBH;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class loginController  implements Initializable{
	@FXML
	private TextField usernameTF;

	@FXML 
	private PasswordField passwordTF;

	@FXML
	private TextField showTF;

	@FXML
	private Label errorLabel;


	@FXML
	private CheckBox checkBox; 

	@FXML
	private CheckBox showPass;

	@FXML
	private ImageView fadeImage;

	@FXML
	private ImageView logoImage;

	@FXML
	private Label animeLabel;

	@FXML
	private Label animeLabel1;

	private accountDAO account = new accountDAO();






	public void loginBtn(ActionEvent eventt) throws IOException {
		String username = usernameTF.getText();
		String password = passwordTF.getText();
		Integer role;
		//Kiem tra checkbox đã được tích vào hay chưa	
		if(checkBox.isSelected()) {
			//lưu tk mk vào pre
			Preferences prefs = Preferences.userNodeForPackage(loginController.class);
			prefs.put("username", username);
			prefs.put("password", password);
		}
		if(username.equals("admin")) {
			role = 0;
		}else {
			role = 1;
		}

		Account a = new Account(username,password,role);

		if(account.checkAccount(a)) {
			((Node)(eventt.getSource())).getScene().getWindow().hide();

			showHome(a);
		}
		else {
			errorLabel.setText("Incorrect username or password. Please try again!");
		}



	}

	public void cancelBtn() {
		usernameTF.setText("");
		passwordTF.setText("");

	}
	public void showHome(Account account) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomeScene.fxml"));

		Parent root = (Parent) fxmlLoader.load();

		HomeSceneController controller = fxmlLoader.getController();
		controller.setAccountLogin(account);

		Stage homeStage = new Stage();
		homeStage.setTitle("HOME");
		homeStage.setScene(new Scene(root));
		homeStage.show();
	} 


	public void signupBtn() {
		String username = usernameTF.getText();
		String password = passwordTF.getText();
		if(username !=null && !username.isEmpty() && password!=null && !password.isEmpty()) {
		if(account.checkAccount(username) != null) {
			errorLabel.setText("Account does exits!");

		}else {
			account.addAccount(new Account(username,password,1));
			errorLabel.setText("Successful account registration!");
		}}else {
			errorLabel.setText("Username or password can't empty");
		}
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		//Timeline cho errorLabel
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.5),e->{
			errorLabel.setText(" ");
		})); 
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		
		
		
		

		//animation
		FadeTransition fade = new  FadeTransition();
		fade.setNode(fadeImage);
		fade.setDuration(Duration.millis(4000));
		fade.setCycleCount(TranslateTransition.INDEFINITE);
		fade.setInterpolator(Interpolator.LINEAR);
		fade.setFromValue(0);
		fade.setToValue(1);
		fade.play();



		RotateTransition rotatet = new RotateTransition();
		rotatet.setNode(logoImage);
		rotatet.setDuration(Duration.millis(5000));
		rotatet.setCycleCount(TranslateTransition.INDEFINITE);
		rotatet.setInterpolator(Interpolator.LINEAR);
		rotatet.setByAngle(360);
		rotatet.setAxis(rotatet.getAxis());
		rotatet.play();

		ScaleTransition scale = new  ScaleTransition();
		scale.setNode(fadeImage);
		scale.setDuration(Duration.millis(4000));
		scale.setCycleCount(TranslateTransition.INDEFINITE);
		scale.setInterpolator(Interpolator.LINEAR);
		scale.setByX(0.5);
		scale.setByY(0.5);
		scale.setAutoReverse(true);
		scale.play();
		//animation




		// Tải thông tin đăng nhập đã lưu từ preferences
		Preferences prefs = Preferences.userNodeForPackage(loginController.class);
		String savedUsername = prefs.get("username", "");
		String savedPassword = prefs.get("password", "");

		/// Nếu có thông tin đăng nhập đã lưu, điền thông tin vào trường username và password
		if (!savedUsername.isEmpty() && !savedPassword.isEmpty()) {
			usernameTF.setText(savedUsername);
			passwordTF.setText(savedPassword);
			showTF.setText(savedPassword);
			checkBox.setSelected(true); // Chọn checkbox
		}else {
			checkBox.setSelected(false);
		}

		//check box show password
		showTF.setVisible(false);
		passwordTF.setVisible(true);

		showPass.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				// Khi checkbox được tích, chuyển mật khẩu từ PasswordField sang TextField
				showTF.setText(passwordTF.getText());
				showTF.setVisible(true);
				passwordTF.setVisible(false);
			} else {
				// Khi checkbox không được tích, chuyển mật khẩu từ TextField sang PasswordField
				passwordTF.setText(showTF.getText());
				passwordTF.setVisible(true);
				showTF.setVisible(false);
			}
		});

		// Đồng bộ hóa giá trị của cả hai trường mật khẩu
		passwordTF.textProperty().bindBidirectional(showTF.textProperty());

	}
	//set Time của errolLbal
	



}
