
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
	
	
	
	public static void deleteRowWithIdMinusOne(String filename) throws IOException {
        File inputFile = new File(filename);

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        List<String> lines = new ArrayList<>();
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            if (!currentLine.contains("-1")) {
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
	
	public static void main(String[] args) throws IOException{
		deleteRowWithIdMinusOne("src/files/StudentInfo.csv");
	}
	
}

