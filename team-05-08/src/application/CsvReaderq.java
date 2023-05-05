package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvReaderq {

    public static void main(String[] args) {

        String csvFile = "src/files/StudentInfo.csv";
        String line;
        int rowCount = 0;
      
        //reads how many rows are there in csvFile and prints out the number
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
            	System.out.println(line);
            	System.out.println("Number of rows: " + rowCount);
                rowCount++;
            }

            System.out.println("Number of rows: " + rowCount);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


