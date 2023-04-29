package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;

public class AfterLogin {
	
	@FXML
	private Button logout;
	@FXML
	private Button generateNew;
	
	public void userLogout(ActionEvent event) throws IOException{
		Main m = new Main();
		m.changeScene("Login.fxml");
	}
	
	public void userGenerate(ActionEvent event) throws IOException{
		Main m = new Main();
		m.changeScene("GenerateNew.fxml");
	}	

}
