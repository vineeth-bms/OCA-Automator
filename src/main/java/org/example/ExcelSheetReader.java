package org.example;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelSheetReader {

    public static List<String> getSheetNames(String excelFilePath) {
        List<String> sheetNames = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            int numberOfSheets = workbook.getNumberOfSheets();

            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                String sheetName = sheet.getSheetName();
                if( sheetName.contains("Config") || sheetName.contains("config"))

                {sheetNames.add(sheetName);}
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sheetNames;
    }
}
