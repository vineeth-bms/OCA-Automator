package org.example;

import java.util.List;

public class TestClass {

    public static void main(String args[]) {

        String filePath = "C:\\Users\\rrao\\Documents\\Test Configs\\SP_MVB_DL385 Gen11OEM LL_Snickerdoodle OEM_v1.0 (1).xlsx";
        String sheetName = "Config2";
        String partnumber = "Part Number";
        String quantity = "QTY";

        System.out.println("Hello");

        List<String> partnumbers = ExcelUtils.getColumnValues(filePath, sheetName, partnumber);
        List<String> quantities = ExcelUtils.getColumnValues(filePath, sheetName, quantity);
        System.out.println( partnumber +"    "+ quantity);

// Process the column values as needed
        for (int i = 0; i < quantities.size(); i++) {
            System.out.println(  partnumbers.get(i) + "     " +quantities.get(i));
        }
    }
}
