
package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
public class csvwriter {
	
	
	

	public void writeArrayToCsv(String[] arr, String csvFilename) throws IOException {
	    FileWriter fileWriter = new FileWriter(csvFilename, true); 
	    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

	    
	    for (int i = 0; i < arr.length; i++) {
	        bufferedWriter.write(arr[i]);

	        
	        if (i != arr.length - 1) {
	            bufferedWriter.write(",");
	        }
	    }

	    bufferedWriter.newLine();

	    bufferedWriter.close();
	    fileWriter.close();
	}

	
	
	// updatecsv:-  Updates the value present at that particular row and column index in datafile.
	
	public void updatecsv(int row,int col,String newValue) throws IOException {
		String filePath = "src/files/usercreds.csv";

        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line;
        int rowIndex = 0;
        while ((line = reader.readLine()) != null) {
            if (rowIndex == row) {
            	
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

