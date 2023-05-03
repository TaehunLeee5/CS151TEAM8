package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import javafx.collections.transformation.SortedList;

import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
public class AfterLogin implements Initializable{
	
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
	
	
	public void userLogout(ActionEvent event) throws IOException{
		Main m = new Main();
		m.changeScene("Login.fxml");
	}
	
	public void userGenerate(ActionEvent event) throws IOException{
		Main m = new Main();
		m.changeScene("GenerateNew.fxml");
	}	
	
	// deleting a row when you click the 'delete' button
	public void deleteRowFromTable(ActionEvent event) throws IOException{
		tableview.getItems().removeAll(tableview.getSelectionModel().getSelectedItem());	
	
	}
	
	// will need to work on the edit function
	public void editRowFromTable(ActionEvent event) throws IOException{
		//tableview.getitem().removeall(tableview.SelectionModel().getSelectedItem());	
	}
	
	
	
	
	
	// below is what I've implemented for the search function
	
	// observable list to store data
	private final ObservableList<Student> dataList = FXCollections.observableArrayList();
		
	
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
		
		public int getID() {
			return id.get();
		}
		public void setid(int id) {
			this.id.set(id);
		}
		
		public String getFirstName() {
			return firstName.get();
		}
		public void setFirstName(String firstName) {
			this.firstName.set(firstName);
		}
		
		public String getLastName() {
			return lastName.get();
		}
		public void setLastName(String lastName) {
			this.lastName.set(lastName);
		}
		
		public int getYear() {
			return year.get();
		}
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
		
		Student student1 = new Student(1, "John", "Doe",2023);
		Student student2 = new Student(2, "James", "Smith",2022);
		Student student3 = new Student(3, "Phil", "Lee",2021);
		
		dataList.addAll(student1, student2, student3);
		
		FilteredList<Student> filterdData = new FilteredList<>(dataList, b -> true);
		
		FilteredList<Student> filteredData = new FilteredList<>(dataList, b -> true);
		SortedList<Student> sortedData = new SortedList<>(filteredData);
		
		sortedData.comparatorProperty().bind(tableview.comparatorProperty());
		
		tableview.setItems(sortedData);
		for (Student student : tableview.getItems()) {
		    System.out.println("ID: " + student.getID() + ", First Name: " + student.getFirstName() +
		                       ", Last Name: " + student.getLastName() + ", Year: " + student.getYear());
		}
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
