package application;

import javafx.beans.property.SimpleIntegerProperty;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import javafx.collections.transformation.SortedList;

import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AfterLogin implements Initializable{
	
	// adding all of FXML variables to use them in this controller
	@FXML
	private Button logout;
	@FXML
	private Button generateNew;
	@FXML
	private TableView<Student> tableview;
	@FXML
	private TextField searchBox;
	
	// to show a list on the table
	@FXML private TableColumn<Student, String> id;
	@FXML private TableColumn<Student, String> firstName;
	@FXML private TableColumn<Student, String> lastName;
	@FXML private TableColumn<Student, String> year;
	
	// log out function that takes an user back to the log in page
	public void userLogout(ActionEvent event) throws IOException{
		Main m = new Main();
		m.changeScene("Login.fxml");
	}
	
	//Take an user to the generating a new letter page
	public void userGenerate(ActionEvent event) throws IOException{
		Main m = new Main();
		m.changeScene("GenerateNew.fxml");
	}	
	
	// deleting a row when you click the 'delete' button and also deletes data in db
	public void deleteRowFromTable(ActionEvent event) throws Exception{	
		try {
			if(tableview.getSelectionModel().getSelectedItem() != null){
				Student selectedItem = tableview.getSelectionModel().getSelectedItem();
				
				// assign this variable to the ID number of the clicked row
				Integer idNum = selectedItem.getID();
				
				// need to create .deleteRow method?
				csvwriter s = new csvwriter();
				s.deleteRowWithId("src/files/StudentInfo.csv",idNum.toString());
			}
		}
		catch (NullPointerException ex) {
		    System.out.println(ex.getMessage());
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		finally {
			Main m = new Main();
			m.changeScene("AfterLogin.fxml");
		}	
	}
	
	// edit function will help an user to modify an exisiting letter of recommentdation
	public void editRowFromTable(ActionEvent event) throws IOException{
		
		try {
			if(tableview.getSelectionModel().getSelectedItem() != null){
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("GenerateNew.fxml"));
				Parent tableViewStudent = loader.load();
				
				Scene tableViewScene = new Scene(tableViewStudent);
				
				GenerateNew controller = loader.getController();
				controller.initData(tableview.getSelectionModel().getSelectedItem());
				
				Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
				
				window.setScene(tableViewScene);
				window.show();
				
			
			}
		}
		
		catch (Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	// observable list to store data
	private final ObservableList<Student> dataList = FXCollections.observableArrayList();
		
	// Creating a Student class to save data for tableview list
	public class Student{
		private final SimpleIntegerProperty id;
		private final SimpleStringProperty firstName;
		private final SimpleStringProperty lastName;
		private final SimpleIntegerProperty year;
		
		Student(Integer id, String firstName, String lastName, Integer year){
			this.id = new SimpleIntegerProperty(id);
			this.firstName = new SimpleStringProperty(firstName);
			this.lastName = new SimpleStringProperty(lastName);
			this.year = new SimpleIntegerProperty(year);
		}
		
		// ID getter
		public int getID() {
			return id.get();
		}
		
		// ID setter
		public void setid(int id) {
			this.id.set(id);
		}
		
		//FirstName getter
		public String getFirstName() {
			return firstName.get();
		}
		
		//FirstName setter
		public void setFirstName(String firstName) {
			this.firstName.set(firstName);
		}
		
		//LastName getter
		public String getLastName() {
			return lastName.get();
		}
		
		//LastName setter
		public void setLastName(String lastName) {
			this.lastName.set(lastName);
		}
		
		//Year getter
		public int getYear() {
			return year.get();
		}
		
		//Year getter
		public void setYear(int year) {
			this.year.set(year);
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		id.setCellValueFactory(new PropertyValueFactory<>("ID"));
		firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		year.setCellValueFactory(new PropertyValueFactory<>("year"));
		
		try {
		    // Create a new file reader and pass the CSV file path
		    FileReader fileReader = new FileReader("src/files/StudentInfo.csv");
		    BufferedReader bufferedReader = new BufferedReader(fileReader);

		    // Read the header row
		    String header = bufferedReader.readLine();

		    // Read each line and create a new Student object
		    String line;
		    while ((line = bufferedReader.readLine()) != null) {
		        String[] fields = line.split(",");
		        System.out.println(line);
		        int id = Integer.parseInt(fields[11]);
		        String firstName = fields[0];
		        String lastName = fields[1];
		        System.out.println(fields[6]);
		        int year = Integer.parseInt(fields[6]);

		        // Create a new Student object and add it to the list
		        Student student = new Student(id, firstName, lastName, year);
		        dataList.add(student);
		    }

		    // Close the file reader
		    bufferedReader.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		//Being able to search wanted data on table view and display them based on Last Name, FirstName, and year
		FilteredList<Student> filteredData = new FilteredList<>(dataList, b -> true);
		SortedList<Student> sortedData = new SortedList<>(filteredData);
		
		sortedData.comparatorProperty().bind(tableview.comparatorProperty());
		
		//This is how to display information in the tableview with ID, first name, last name, and year
		tableview.setItems(sortedData);
		for (Student student : tableview.getItems()) {
		    System.out.println("ID: " + student.getID() + ", First Name: " + student.getFirstName() +
		                       ", Last Name: " + student.getLastName() + ", Year: " + student.getYear());
		}
		
		//Implementing the searchBox and reading what an user types and will show the result
		searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
		    filteredData.setPredicate(student -> {
		        if (newValue == null || newValue.isEmpty()) {
		            return true;
		        }

		        String lowerCaseFilter = newValue.toLowerCase();

		        if (student.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
		            return true;
		        }

		        else if (student.getLastName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
		            return true;
		        }

		        else if (String.valueOf(student.getYear()).indexOf(lowerCaseFilter) != -1) {
		            return true;
		        }

		        else {
		            return false;
		        }
		    });
		});
			
			
	}
	
	
}


