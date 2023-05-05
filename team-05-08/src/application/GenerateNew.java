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

    public String getListview(ListView<String> val) {
    	val.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	ObservableList<String> topics;
    	String list= "";
    	topics = val.getSelectionModel().getSelectedItems();
    	if(val.equals(listviewcourse)) {
    		topics = val.getItems();    	}
    	
    	//String[] grades = new String[] { CS151grade.getText(), cs146grade.getText(), cs152grade.getText(), cs154grade.getText(), cs160grade.getText(), cs256grade.getText(), CS166grade.getText() };
    	int i = 0;
    	for (String m : topics)
    	{	
    		System.out.println(m);
    		list += m + "/ ";
//    		if(val == courseTaken){
//    			
//    			list += m + ":" + "/ ";
//    			i++;
//    		}else {
//    			
//    		}
    	    
    	} 

    	int pos= list.lastIndexOf("");

    	String selection= list.substring(0, pos);
    	return selection;
    }
    public void userCancel1(ActionEvent event) throws IOException{
		Main m = new Main();
		m.changeScene("AfterLogin.fxml");
	}
    
    
    public void initData(Student student) throws IOException{
    	Student selectedStudent = student;
    	
    	firstName.setText(selectedStudent.getFirstName());
    	lastName.setText(selectedStudent.getLastName());
    	csvreader idinfo = new csvreader();
    	int idv = selectedStudent.getID();
    	String stridv = String.valueOf(idv);
    	String[] userinf = idinfo.searchcsvid("src/files/StudentInfo.csv", stridv );
    	// will need to connect to db
    	
    	datePicker.setText(userinf[2].toString());
    	gender.setText(userinf[3]);
    	schoolName.setText(userinf[4]);
    	programApplying.setText(userinf[5]);
    	firstSemester.setText(userinf[7]);
    	firstYear.setText(userinf[6]);
    	courseTaken.setText(userinf[8]);
    	//letterGrade.setText(userinf[3]);
    	personalCharacteristics.setText(userinf[10]);
    	academicCharacteristics.setText(userinf[9]);
    }
    
    
    public void userGenerateNew(ActionEvent event) throws IOException{
        try {
	    	if (firstName.getText() != null || lastName.getText() != null || datePicker.getValue() != null 
	        		|| gender.getValue() != null ||schoolName.getText() != null || programApplying.getValue() != null || 
	        		firstYear.getText() != null || firstSemester.getValue() != null ) {
	            csvwriter newStudentInfo = new csvwriter();
	            //newStudentInfo.deleteRowWithIdMinusOne("src/files/StudentInfo.csv");
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
	            // Create an instance of Controller B and pass the input array as a parameter
	
	
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
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("GenerateNew.fxml"));
//        loader.setController(this);
//        saveButton.setOnAction(e -> handleSaveButton());
//
//    }
    
    
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
         //gender.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);
         
         String[] personalCharacteristicsList = csvreader.readcsvfile("src/files/initial_data_.csv","personal characteristics");
         ObservableList<String> list_personalCharacter = FXCollections.observableArrayList(personalCharacteristicsList);
         personalCharacteristics.getItems().addAll(list_personalCharacter);
         personalCharacteristics.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
         //personalCharacteristics.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);
         
         
         String[] courseTakenList = csvreader.readcsvfile("src/files/initial_data_.csv","courses");
         ObservableList<String> list_course = FXCollections.observableArrayList(courseTakenList);
         courseTaken.setItems(list_course);
         
         firstName.setText("Nikunj");
         lastName.setText("Rana");
         gender.setValue("Male");
         programApplying.setValue("Master of Science");
         firstSemester.setValue("Spring");
         academicCharacteristics.getSelectionModel().selectAll();
         personalCharacteristics.getSelectionModel().selectAll();
         //courseTaken.getSelectionModel().selectAll();  
             
         
    }  
    
    
    
}