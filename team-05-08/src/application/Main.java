package application;
	
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

// Main class that sets the size of the window of this project and setting stage
public class Main extends Application {
	
	private static Stage stg;
	
	@Override
	//Start Function takes a stage as an arugment and displays the window	
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
	// methods that takes a string to open the specific fxml file
	public void changeScene(String fxml) throws IOException{
		Parent pane = FXMLLoader.load(getClass().getResource(fxml));
		stg.getScene().setRoot(pane);
	}

	// function that runs the entire file for this project
 // ################################     MAIN FUNCTION    ########################################
	public static void main(String[] args) {
		launch(args);
	}
}
