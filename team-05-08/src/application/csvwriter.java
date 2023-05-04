
package application;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.io.*;
public class csvwriter {
	
	
	
	public void deleteRowWithIdMinusOne(String filename) throws IOException {
        File inputFile = new File(filename);

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        List<String> lines = new ArrayList<>();
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
        	String[] fields = currentLine.split(",");
            if (!(fields[11].equals(-1))) {
                lines.add(currentLine);
            }
        }
        reader.close();

        BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));
        for (String line : lines) {
            writer.write(line + System.lineSeparator());
        }
        writer.close();
    }
	
	public void saveStringToFile(String path, String text) {
		try {
			System.out.println("____________________________________________________________");
			System.out.println(path);
			System.out.println(text);
			System.out.println("____________________________________________________________");
	        File file = new File(path);
	        file.createNewFile();
	        FileWriter writer = new FileWriter(file);
	        writer.write(text);
	        writer.close();
	        System.out.println("File saved successfully.");
	    } catch (IOException e) {
	    	System.out.println(e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	public void deleteRowWithId(String filename, String id) throws IOException {
        File inputFile = new File(filename);

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        List<String> lines = new ArrayList<>();
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
        	String[] fields = currentLine.split(",");
            if (!(fields[11].equals(id))) {
                lines.add(currentLine);
            }
        }
        reader.close();

        BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));
        for (String line : lines) {
            writer.write(line + System.lineSeparator());
        }
        writer.close();
    }
	public void writeArrayToCsv(String[] values, String csvFilePath) throws IOException {
		FileWriter fileWriter = new FileWriter(csvFilePath, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        
        for (int i = 0; i < values.length; i++) {
            bufferedWriter.write(values[i]);
            
            if (i != values.length - 1) {
                bufferedWriter.write(",");
            }
        }
        
        bufferedWriter.newLine();
        bufferedWriter.close();
        fileWriter.close();
	}

	
	
	// updatecsv:-  Updates the value present at that particular row and column index in datafile.
	
	public void updatecsv(String filePath,int row,int col,String newValue) throws IOException {

        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line;
        int rowIndex = 0;
        System.out.println("here");
        while ((line = reader.readLine()) != null) {
            if (rowIndex == row) {
            	System.out.println(line);
                String[] fields = line.split(",");
                
                if (col <= fields.length) {
                	
                    fields[col] = newValue;
                    line = String.join(",", fields);
                }
            }
            sb.append(line).append("\n");
            rowIndex++;
        }
        reader.close();

        // Write the updated content back to the same CSV file
        FileWriter writer = new FileWriter(file);
        writer.write(sb.toString());
        writer.close();
        
    }
	
	
	
}

