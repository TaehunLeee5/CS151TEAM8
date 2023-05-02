
package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.io.*;
public class csvwriter {
	
	
	

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
	
//	public static void main(String[] args) throws IOException{
//		String[] a = {"Nikunj", "Rana", "05-09-2023", "Male", "", "Master of Science", "", "Spring", "CS151:/ CS166:/ CS154:/ CS160:/ CS256:/ CS146:/ CS152:/", "submitted well-written assignments/ participated in all of my class activities/ worked hard/ was very well prepared for every exam and assignment/ picked up new skills quickly/ was able to excel academically at the top of my class/", "very passionate/ very enthusiastic/ punctual/ attentive/ polite/", "-1"};
//		writeArrayToCsv(a,"src/files/StudentInfo.csv");
//	}
	
}

