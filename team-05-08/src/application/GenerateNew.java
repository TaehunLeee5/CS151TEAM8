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
import java.time.LocalDate;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.AfterLogin.Student;
import javafx.collections.FXCollections;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GenerateNew implements Initializable {
//getting all of FXML variables to use them in this class
	@FXML
	private TextField CS151grade;
	
	@FXML
    private TextField cs146grade;
	
	
    @FXML
    private TextField cs152grade;

    @FXML
    private TextField cs154grade;

    @FXML
    private TextField cs160grade;

    @FXML
    private TextField cs256grade;
    
	@FXML
	private TextField CS166grade;

    @FXML
    private ListView<String> academicCharacteristics;

    @FXML
    private Button cancelButton1;

    @FXML
    private TextField courseLettergrade;

    @FXML
    private ComboBox<String> courseTaken;

    @FXML
    private TextField firstName;

    @FXML
    private ComboBox<String> firstSemester;

    @FXML
    private TextField firstYear;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private Button generateButton;

    @FXML
    private TextField lastName;
    @FXML
    private ListView<String> listviewcourse;
    @FXML
    private ListView<String> personalCharacteristics;

    @FXML
    private ComboBox<String> programApplying;

    @FXML
    private TextField schoolName;

    @FXML
    private Label warning;
    
    @FXML
    private Label firstSemesterWarning;
    
    @FXML
    private DatePicker datePicker;
    private List<String> coursegradelist = new ArrayList<>();

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField classField;

    @FXML
    private TextField gradeField;

    @FXML
    private Button saveButton;
    
    // making the add button functional in GenerteNew page to add courses taken with a professor
    @FXML
    void addcourse(ActionEvent event) {
        String course = courseTaken.getValue();
        String grade = gradeField.getText();

        // Add the course and grade to the ArrayList
        coursegradelist.add(course + "    :     " + grade);

        // Clear the text fields
        courseTaken.setValue(null);
        gradeField.clear();

        // Update the ListView
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(coursegradelist);
        listviewcourse.setItems(items);
    }

    //Getting input data and putting them in a ObeservableList(String) 
    public String getListview(ListView<String> val) {
    	val.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	ObservableList<String> topics;
    	String list= "";
    	topics = val.getSelectionModel().getSelectedItems();
    	if(val.equals(listviewcourse)) {
    		topics = val.getItems();    	}
    	
    	int i = 0;
    	for (String m : topics)
    	{	
    		System.out.println(m);
    		list += m + "/ ";    	    
    	} 

    	int pos= list.lastIndexOf("");

    	String selection= list.substring(0, pos);
    	return selection;
    }
	
    // Cancel button that will direct an user back to the afterlogin page where it shows a tableview
    public void userCancel1(ActionEvent event) throws IOException{
		Main m = new Main();
		m.changeScene("AfterLogin.fxml");
	}
    
    // When an user clicks on edit button with a selected row, it directs an user to GenerateNew page and show saved data and be able to edit
    public void initData(Student student) throws IOException{
    	Student selectedStudent = student;
    	
    	firstName.setText(selectedStudent.getFirstName());
    	lastName.setText(selectedStudent.getLastName());
    	csvreader idinfo = new csvreader();
    	int idv = selectedStudent.getID();
    	String stridv = String.valueOf(idv);
    	String[] userinf = idinfo.searchcsvid("src/files/StudentInfo.csv", stridv );
    	
	gender.setValue(userinf[3]);
    	schoolName.setText(userinf[4]);
    	programApplying.setValue(userinf[5]);
    	firstSemester.setValue(userinf[7]);
    	firstYear.setText(userinf[6]);
    	courseTaken.setValue(userinf[8]);
    	gradeField.setText(userinf[3]);  
    }
    
    //Compile button that will generate a draft version of a letter of recommendation
    public void userGenerateNew(ActionEvent event) throws IOException{
        try {
	    	if (firstName.getText() != null || lastName.getText() != null || datePicker.getValue() != null 
	        		|| gender.getValue() != null ||schoolName.getText() != null || programApplying.getValue() != null || 
	        		firstYear.getText() != null || firstSemester.getValue() != null ) {
	            csvwriter newStudentInfo = new csvwriter();
	            String[] inputValues = new String[12];
	            inputValues[0] = firstName.getText();
	            inputValues[1] = lastName.getText();
	            LocalDate date = datePicker.getValue();
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
	            inputValues[2] = date.format(formatter);
	            inputValues[3] = gender.getValue();
	            inputValues[4] = schoolName.getText();
	            inputValues[5] = programApplying.getValue();
	            inputValues[6] = firstYear.getText();
	            inputValues[7] = firstSemester.getValue();
	            inputValues[8] = getListview(listviewcourse);
	            inputValues[9] = getListview(academicCharacteristics);
	            inputValues[10] = getListview(personalCharacteristics);
	            inputValues[11] = "-1";
	
	            for (int i = 0; i < inputValues.length; i++) {
	                System.out.println(inputValues[i]);
	            }
	
	            newStudentInfo.writeArrayToCsv(inputValues, "src/files/StudentInfo.csv");
	            Main m = new Main();
	            m.changeScene("SavedReco.fxml");
	        }
	    
        }
	    catch(Exception ex){
	    	warning.setText("Please fill out all");
	    	System.out.println(ex.getMessage());
	    }
    }
    
	//initializing String lists for each section in the GenerateNew page
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	 
    	String[] list = csvreader.readcsvfile("src/files/initial_data_.csv","gender");
        ObservableList<String> list_gender = FXCollections.observableArrayList(list);
        gender.setItems(list_gender);
        
        String[] list_program = csvreader.readcsvfile("src/files/initial_data_.csv","programs names");
        ObservableList<String> list_check = FXCollections.observableArrayList(list_program);
        programApplying.setItems(list_check);
        
         String[] firstSemesterList = csvreader.readcsvfile("src/files/initial_data_.csv","semesters");
         ObservableList<String> list_firstsemester = FXCollections.observableArrayList(firstSemesterList);
         firstSemester.setItems(list_firstsemester);
           
         String[] academicCharacteristicsList = csvreader.readcsvfile("src/files/initial_data_.csv","academic characteristics");
         ObservableList<String> list_academic = FXCollections.observableArrayList(academicCharacteristicsList);
         academicCharacteristics.getItems().addAll(list_academic);
         academicCharacteristics.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
         
         String[] personalCharacteristicsList = csvreader.readcsvfile("src/files/initial_data_.csv","personal characteristics");
         ObservableList<String> list_personalCharacter = FXCollections.observableArrayList(personalCharacteristicsList);
         personalCharacteristics.getItems().addAll(list_personalCharacter);
         personalCharacteristics.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);         
         
         String[] courseTakenList = csvreader.readcsvfile("src/files/initial_data_.csv","courses");
         ObservableList<String> list_course = FXCollections.observableArrayList(courseTakenList);
         courseTaken.setItems(list_course);             
        
    }    
}
