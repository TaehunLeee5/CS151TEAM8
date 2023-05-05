  package application;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.io.IOException; 

public class csvreader {
	
	//Method that returns the row of a wanted csv file or no value
	public static String searchcsv(String csvFilename, String value1, String value2, String value3) throws IOException {
	    BufferedReader csvReader = new BufferedReader(new FileReader(csvFilename));
	    String row;

	    
	    while ((row = csvReader.readLine()) != null) {
	        String[] data = row.split(",");

	        	System.out.println(row);
	        
	            if (data[0].equals(value1) && data[1].equals(value2) && data[2].equals(value3)) {
	                csvReader.close();
	                return row;
	        }
	    }

	    csvReader.close();
	    return "no value";
	}
	
	// once it finds a wanted csvfile name, it reads the data to retrieve
	public String[] searchcsvid(String csvFilename, String value1) throws IOException {
	    BufferedReader csvReader = new BufferedReader(new FileReader(csvFilename));
	    String row;
	    
	    while ((row = csvReader.readLine()) != null) {
	        String[] data = row.split(",");
	        	System.out.println(Arrays.toString(data));
	            if (data[11].equals(value1)) {
	            	
	                csvReader.close();
	                return data;
	        }
	    }

	    csvReader.close();
	    return new String[0];
	}
	
	//Finds the row that matches id number
	public int findrow(String filePath,int i) {
		BufferedReader reader = null;
		try {
			
			String line = "";
			int count = 0;
			reader = new BufferedReader(new FileReader(filePath));
			while((line = reader.readLine()) != null) {
			    String[] fields = line.split(",");
			    if(fields[11].equalsIgnoreCase("-1")) {
			    	System.out.println(count);
			    	return count;
			    } else {
			    	count = count + 1; 
			    }
			}
		} 
		catch (Exception ex) {
				   ex.printStackTrace();
		} 
		finally {
			try {
			    reader.close();
			   } catch (Exception e) {
			    e.printStackTrace();
			   }
			  }
		
		return -1;
		
	}
	
	// readcsvfile:- read the initial data file and send values for different attributes like gender,courses,etc.
	public static String[] readcsvfile(String filePath,String colName) {
		BufferedReader reader = null;
		try {
			
			String line = "";
			reader = new BufferedReader(new FileReader(filePath));
			while((line = reader.readLine()) != null) {
			    String[] fields = line.split(",");
			    if(fields[0].equalsIgnoreCase(colName)) {
			    	return Arrays.copyOfRange(fields, 1, fields.length);
			    }
			}
		} 
		catch (Exception ex) {
				   ex.printStackTrace();
		} 
		finally {
			try {
			    reader.close();
			   } catch (Exception e) {
			    e.printStackTrace();
			   }
			  }
		
		return null;
		
	}
	
	
	// getPassword:- fecthes the current password from datafile.
	public String getPassword(int i) {
		BufferedReader reader = null;
		try {
			String filePath = "src/files/usercreds.csv";
			
			String line = "";
			reader = new BufferedReader(new FileReader(filePath));
			reader.readLine();
			line = reader.readLine();
			String[] fields = line.split(",");			
			String h = fields[i];
			return fields[i];
		} 
		catch (Exception ex) {
				   ex.printStackTrace();
		} 
		finally {
			try {
			    reader.close();
			   } catch (Exception e) {
			    e.printStackTrace();
			   }
			  }
		
		return null;
		
	}
	
//	public static void main(String[] args) {
//		int g = findrow("src/files/StudentInfo.csv",-1);
//	}
}
