package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

// reset class that enables to reset password for an user
public class ResetPassword {
	@FXML
	private Button resetButton;
	@FXML
	private Button cancelButton;
	@FXML
	private PasswordField newPassword;
	@FXML
	private PasswordField oldPassword;
	@FXML
	private PasswordField renewPassword;
	@FXML
	private Label notmatched;
	
	
	// userResetpassword:- Updates the new password in datafile after verifying with current password.
	public void userResetpassword(ActionEvent event) throws IOException{
		Main m = new Main();
		csvreader currpassword = new csvreader();
		String PassValue = currpassword.getPassword(0);
		String LoginVal = currpassword.getPassword(1);
		String LatestPass = currpassword.getPassword(2);
		csvwriter resetpw = new csvwriter();
		
		//check if the old passwords match and updates with a new password
		if(LoginVal.equals("FALSE") && oldPassword.getText().toString().equals(PassValue) && newPassword.getText().toString().equals(renewPassword.getText().toString())) {
			String nn = newPassword.getText().toString();
			resetpw.updatecsv("src/files/usercreds.csv",1,2, nn);
			resetpw.updatecsv("src/files/usercreds.csv",1,1, "FALSE");
			m.changeScene("Login.fxml");
			
		}
		else if(LoginVal.equals("FALSE") && oldPassword.getText().toString().equals(LatestPass) && newPassword.getText().toString().equals(renewPassword.getText().toString())){
			
			String nn = newPassword.getText().toString();
			resetpw.updatecsv("src/files/usercreds.csv",1,2, nn);
			m.changeScene("Login.fxml");
		}
		else {
			notmatched.setText("Enter correct password or new password does not match.");

		}
		
	}
	
	// Cancel functions that directs an user back to login page
	public void userCancel(ActionEvent event) throws IOException{
		Main m = new Main();
		m.changeScene("Login.fxml");
	}
}
