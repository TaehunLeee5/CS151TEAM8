package application;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import java.time.LocalDate;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Region;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class SavedReco implements Initializable {

	String[] userVal;
	 @FXML
	    private TextArea textname;
	@FXML
    public void SaveButton(ActionEvent event) throws IOException {
		csvreader newob = new csvreader();
		csvwriter newval = new csvwriter();
		
		int row = newob.findrow("src/files/StudentInfo.csv",-1);
		String newid = newob.getPassword(9);
		System.out.println(newid);
		System.out.println(row);
		String text = textname.getText();
		String textval = "\"" + text + "\"";
		String replacedString = textval.replaceAll("\n", "\\n");
		String filename = "src/outputfiles/" + userVal[0] + "_" + userVal[1] + "_" + newid + ".txt";
		System.out.println(filename);
		newval.saveStringToFile(filename, text);
		//newval.updatecsv("src/files/StudentInfo.csv",row, 12, replacedString);
		newval.updatecsv("src/files/StudentInfo.csv",row,11,newid);
		newval.updatecsv("src/files/usercreds.csv",1, 9, Integer.toString(Integer.parseInt(newid)+1));
		Main m = new Main();
		m.changeScene("AfterLogin.fxml");
    }
	public String getString(String arr) {
		
	    String[] values = arr.split("/");

	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < values.length - 1; i++) {
	        sb.append(values[i]).append(", ");
	    }
	    sb.append("and ").append(values[values.length - 1]);

	    String result = sb.toString();
	    return result;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb){
		try {
			
            csvreader newid = new csvreader();
            String genderUsersmall = "";
            String genderUserCap = "";
            userVal = newid.searchcsvid("src/files/StudentInfo.csv", "-1");
            String profName = newid.getPassword(3);
            String profTitle = newid.getPassword(4);
            String profSchool = newid.getPassword(5);
            String profDepartment = newid.getPassword(6);
            String profEmail = newid.getPassword(7);
            String profNumber = newid.getPassword(8);
            if(userVal[3].equals("Male")) {
            	 genderUsersmall = "he";
            	 genderUserCap = "He";
            } else {
            	 genderUsersmall = "she";
            	 genderUserCap = "She";
            }
            
            String textToSet = "Letter of Recommendation\n\n" + "For: " + userVal[0] + " " + userVal[1] + "\n\n" + "To: Graduate Admissions Committee\n"
            + "Date: " + userVal[2] + "\n\n\n\n" + "I am writing this letter to recommend my former student " + userVal[0] + " " + userVal[1] + " who is"
            + " applying for the " + userVal[5] + " in your school. " + "I met " + userVal[0] + " in " + userVal[7] + " ," + userVal[6] + " when he enrolled"
            + " in my" + " need best course here " + "course.\n\n" + userVal[0] + " earned " + "best grade " + "from this tough course, and this shows how "
            + "knowledgable and hardworking " + genderUsersmall + " is.\n\n" + userVal[0] + " " + getString(userVal[9].substring(0, userVal[9].lastIndexOf("/")))
            + ". " + genderUserCap + " was always " + getString(userVal[9].substring(0, userVal[9].lastIndexOf("/"))) + ".\n\n" + "Furthermore, I noticed from "
            + "the term project result, " + genderUsersmall + " developed leadership, time management, and problem-solving skills. " + genderUsersmall + 
            "worked effectively with the team members and delegated tasks appropriately. They were able to deliver a successful project in a timely fashion.\n\n" +
            "I believe that " + userVal[0] + " has the capacity to excel at higher education program and this is my pleasure to highly recommend him.\n\n" + 
            "Please do not hesitate to contact me with further questions.\n\n\n" + "Very Respectfully,\n\n" + profName + "\n\n" + profTitle + "\n"
            + profSchool + "\n" + profDepartment + "\n" + profEmail + "\n" + profNumber;
            System.out.println(textToSet);
            // Set the text in the TextArea
            textname.setText(textToSet);
            //textname. setEditable(false);
            //Code for updating the textarea column for rowid = -1
		} catch (IOException e) {
            e.printStackTrace();
        }
	}
}