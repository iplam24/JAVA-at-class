package QLBH;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class QLBHmain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//Lay file fxml login lam layout chinh
			Parent root =
			FXMLLoader.load(getClass().getResource("login.fxml"));
			//them root vao scene
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			String css = this.getClass().getResource("application.css").toExternalForm();
			scene.getStylesheets().add(css);
			//them scene vao stage
			primaryStage.setScene(scene);
			primaryStage.setTitle("Login Screen");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
