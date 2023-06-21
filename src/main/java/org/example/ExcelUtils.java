package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {

    public static String getFileName(String filePath){
        File file = new File(filePath);
        String fileName = file.getName();
        return fileName;
    }




    public static String getTitle(String filepath, String sheetname) {

        String cValue = "";
        try (FileInputStream fileInputStream = new FileInputStream(filepath)) {
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheet(sheetname);

            int columnIdx = 1;
            int rowIndex = 2;
            Row row = sheet.getRow(rowIndex);
            Cell cell = row.getCell(columnIdx);
            cValue = getCellValueAsString(cell);

            workbook.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return cValue;
    }

    public static List<String> getColumnValues(String filePath, String sheetName, String columnName) {
        List<String> columnValues = new ArrayList<>();


        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {

            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheet(sheetName);

            int columnIdx = getColumnIndex(sheet, columnName);


            if (columnIdx != -1) {
                int rowCount = sheet.getPhysicalNumberOfRows();

                for (int rowIndex = 8; rowIndex < rowCount; rowIndex++) {
                    Row row = sheet.getRow(rowIndex);

                    if (row != null) {
                        Cell cell = row.getCell(columnIdx);
                        Cell char_field = row.getCell(2);

                        if (cell != null ) {

                            if(getCellValueAsString(char_field).equals("PRI"))
                                {

                                    String cellValue = getCellValueAsString(cell);

                                    columnValues.add(cellValue);
                                }
                        }

                    }
                }
            }

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return columnValues;
    }

    private static int getColumnIndex(Sheet sheet, String columnName) {
        Row headerRow = sheet.getRow(7);

        int lastCellNum = headerRow.getLastCellNum();

        for (int columnIndex = 0; columnIndex < lastCellNum; columnIndex++) {
            Cell headerCell = headerRow.getCell(columnIndex);
            if (headerCell != null && columnName.equals(getCellValueAsString(headerCell))) {
                return columnIndex;
            }
        }
        return -1; // Column not found
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        CellType cellType = cell.getCellType();
        switch (cellType) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
