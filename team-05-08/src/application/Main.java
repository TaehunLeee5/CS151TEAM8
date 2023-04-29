package application;
	
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class Main extends Application {
	
	private static Stage stg;
	
	@Override
	//	Start Function	
	public void start(Stage primaryStage) {
		stg = primaryStage;
		primaryStage.setResizable(false);
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root,800,800);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("RECOMMENDER");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void changeScene(String fxml) throws IOException{
		Parent pane = FXMLLoader.load(getClass().getResource(fxml));
		stg.getScene().setRoot(pane);
	}
	
 // ################################     MAIN FUNCTION    ########################################
	public static void main(String[] args) {
		launch(args);
	}
}
