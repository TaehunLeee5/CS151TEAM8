package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import java.io.BufferedReader;
import java.io.FileReader;
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
    private Button edit;
    @FXML
    private Button delete;
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
		System.out.println("here");
		m.changeScene("GenerateNew.fxml");
	}	
	
	public void deleteRowFromTable(ActionEvent event) throws Exception{
        try {
            if(tableview.getSelectionModel().getSelectedItem() != null){
                Student selectedItem = tableview.getSelectionModel().getSelectedItem();

                // assign this variable to the ID number of the clicked row
                Integer idNum = selectedItem.getID();
                csvwriter s = new csvwriter();
                // need to create .deleteRow method?
                s.deleteRowWithId("src/files/StudentInfo.csv",idNum.toString());
            }
        }
        catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        finally {
            Main m = new Main();
            m.changeScene("AfterLogin.fxml");
        }
    }
	

    // will need to work on the edit function
    public void editRowFromTable(ActionEvent event) throws IOException{
        //tableview.getitem().removeall(tableview.SelectionModel().getSelectedItem());
        // Check if a row is selected
        if (tableview.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(AlertType.ERROR, "No row selected.");
            alert.showAndWait();
            return;
        }

        // Get the selected row and assign it to "selectedData" object with all of its data
        Student selectedData = tableview.getSelectionModel().getSelectedItem();

        // and then bring up the generate a new letter page with pre-filled data to be able to edit
        //need to Main constructor taking selecetData as an argument
//        Main m = new Main(selectedData);
//        m.changeScene("GenerateNew.fxml");


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
