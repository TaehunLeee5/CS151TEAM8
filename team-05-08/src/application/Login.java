package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;


public class Login {
	public Login() {
		
	}
	
	@FXML
	private Button login;
	
	@FXML
	private Label wrongLogin;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private Button resetPassword;
	
	// Function triggered after Login button is clicked.
	public void userLogin(ActionEvent event) throws IOException{
		checkLogin();
	}  
	
	// CheckLogin:- Checks for first time login and matches password with default/current password.
	//              If default password user is prompted to resetPassword scene otherwise shown HomePage.
	private void checkLogin() throws IOException{
		Main m = new Main();
		String PassValue;
		csvreader checkfirstlogin = new csvreader();
		String check = checkfirstlogin.getPassword(1);
		csvreader currpassword = new csvreader();
		if(check.toString().equals("TRUE")) {
			PassValue = currpassword.getPassword(0);
		} else {
			PassValue = currpassword.getPassword(2);
		}
		//System.out.println(PassValue);
		if(password.getText().toString().equals(PassValue)) {
			wrongLogin.setText("Success!");
			if(check.toString().equals("TRUE")) {
				m.changeScene("ResetPassword.fxml");
				
			}
			else {
				m.changeScene("AfterLogin.fxml");

			}
			
		}
		
		else if(password.getText().isEmpty()) {
			wrongLogin.setText("Please enter your password.");
		}
		
		else {
			wrongLogin.setText("Wrong password");
		}
	}
	
	public void userResetpassword(ActionEvent event) throws IOException{
		resetPassword();
	}
	
	private void resetPassword() throws IOException{
		Main m = new Main();
		m.changeScene("ResetPassword.fxml");
	}
	
	
}
