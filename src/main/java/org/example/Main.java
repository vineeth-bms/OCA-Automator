package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class Main {

    static List<File> selectedFiles = new ArrayList<>();

    static List<String> sheetNames = new ArrayList<>();

    static String str = "";

    static String excelFilePath = "";

    public static DefaultTableModel playConfigs(String FilePath) {

        String exlFilePath = FilePath;
        DefaultTableModel model = new DefaultTableModel();


        System.out.println("------in playconfigs--------");


        Map<String, String> resultmap = new HashMap<>();


        sheetNames = ExcelSheetReader.getSheetNames(exlFilePath);

        if (!sheetNames.isEmpty()) {

            model.addColumn("File Name");
            model.addColumn("Config Title");
            model.addColumn("UCID");
            model.addColumn("Result");


            int i = 0;
            for (String sheetName : sheetNames) {
                System.out.println("Sheet name: " + sheetName);
                resultmap = OCA_SDriver.testConfig(exlFilePath, sheetName);
                model.addRow(new Object[]{ExcelUtils.getFileName(exlFilePath), ExcelUtils.getTitle(exlFilePath, sheetNames.get(i)),resultmap.get( resultmap.keySet().toArray()[0]),  resultmap.keySet().toArray()[0]});
                i++;
            }



        }

        return model;
    }


    public static void main(String[] args) {

        List<DefaultTableModel> models = new ArrayList<>();
        List<JTable> tables = new ArrayList<>();

        ExcelFileChooser fileChooser = new ExcelFileChooser();
        fileChooser.setVisible(true);

        // Wait for the user to select files...
        // Call getSelectedFilePaths() to retrieve the selected file paths
        List<String> selectedFiles = fileChooser.getSelectedFilePaths();
        int fno = 0;

        while (!ExcelFileChooser.isSubmitClicked) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Call Main.playConfigs() after the file chooser is closed
        if (!selectedFiles.isEmpty()) {
            OCA_SDriver.openDriver();
            for (String filePath : selectedFiles) {
                models.add(Main.playConfigs(filePath));

            }
            OCA_SDriver.closeDriver();
            // Create the table model


        }

        Workbook workbook = new XSSFWorkbook();
        CellStyle headerCellStyle = workbook.createCellStyle();
        org.apache.poi.ss.usermodel.Font headerF =  workbook.createFont();

        headerF.setBold(true);
        headerCellStyle.setFont(headerF);
        int i = 0;
        JFrame frame = new JFrame(ExcelUtils.getFileName(selectedFiles.get(i)));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        JPanel parentPanel = new JPanel();
        parentPanel.setLayout(new GridLayout(models.size(),1));

        // Add a WindowListener to handle the window closing event
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Perform any cleanup or finalization tasks here
                System.exit(0);


            }
        });

int tno = 0;
        for (DefaultTableModel model : models) {


           tables.add(new JTable(model)) ;

            Font tableFont = new Font(Font.SERIF, Font.BOLD, 16);
            tables.get(tno).setFont(tableFont);
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setPreferredSize(new Dimension(150, 80));
            cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            cellRenderer.setPreferredSize(new Dimension(150, 40));
            tables.get(tno).setRowHeight(50);
            tables.get(tno).setDefaultRenderer(Object.class, cellRenderer);

            JTableHeader header = tables.get(tno).getTableHeader();
            header.setPreferredSize(new Dimension(header.getWidth(), 60));
            Font headerFont = new Font(Font.SERIF, Font.BOLD, 18);
            header.setFont(headerFont);

            // Create a scroll pane for the table
            JScrollPane scrollPane = new JScrollPane(tables.get(tno));
            Dimension preferredSize = tables.get(tno).getPreferredSize();
            scrollPane.setPreferredSize(new Dimension(preferredSize.width, preferredSize.height));
            parentPanel.add(scrollPane);

            tno++;


        }

        //excel
        JButton saveButton = new JButton("Save to Excel");
        Dimension buttonSize = new Dimension(saveButton.getPreferredSize().width, 40);
        saveButton.setPreferredSize(buttonSize);
        saveButton.setBackground(new Color(59, 191, 129));
        //String finalExcelFilePath = excelFilePath;





        saveButton.addActionListener(e -> {
                    JFileChooser fChooser = new JFileChooser();
                    fChooser.setDialogTitle("Save Excel File");
                    fChooser.setCurrentDirectory(new File(selectedFiles.get(0)));
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx");


                    // Provide a recommended file name
                    String recommendedFileName = "result_of"  +  "files";
                    fChooser.setSelectedFile(new java.io.File(recommendedFileName));

                    int choice = fChooser.showSaveDialog(frame);
                    if (choice == JFileChooser.APPROVE_OPTION) {
                        String filePath = fChooser.getSelectedFile().getAbsolutePath();

                        if (!filePath .endsWith(".xlsx"))
                                filePath += ".xlsx";

                        int t = 0;
                        for (DefaultTableModel model : models) {
                            try {
                                Sheet sheet = workbook.createSheet(ExcelUtils.getFileName(selectedFiles.get(t++)));

                                // Write table headers to Excel
                                Row headerRow = sheet.createRow(0);
                                for (int col = 0; col < model.getColumnCount(); col++) {
                                    Cell cell = headerRow.createCell(col);

                                    cell.setCellValue(model.getColumnName(col));
                                    cell.setCellStyle(headerCellStyle);


                                }


                                // Write table data to Excel
                                Vector<Vector<Object>> data = model.getDataVector();
                                for (int row = 0; row < data.size(); row++) {
                                    Row excelRow = sheet.createRow(row + 1);
                                    Vector<Object> rowData = data.get(row);
                                    for (int col = 0; col < rowData.size(); col++) {
                                        Cell cell = excelRow.createCell(col);
                                        Object cellValue = rowData.get(col);
                                        if (cellValue != null) {
                                            cell.setCellValue(cellValue.toString());
                                            int contentLength = cellValue.toString().length();
                                            int currentWidth = sheet.getColumnWidth(col);
                                            if (contentLength > currentWidth / 256) {
                                                sheet.setColumnWidth(col, (contentLength + 1) * 256);
                                            }
                                        }
                                    }
                                }

                                // Save Excel file


                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                                workbook.write(fileOutputStream);
                                JOptionPane.showMessageDialog(frame, "Excel file saved successfully.");
                                System.out.println("Joption is " + JOptionPane.DEFAULT_OPTION);
                                if (JOptionPane.DEFAULT_OPTION == -1) {
                                    frame.dispose();
                                    System.exit(0);
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(frame, "The process cannot access the file because it is being used by another process");
                            }
                        }

                });

        // Add the "Save" button to the frame
        frame.add(saveButton, BorderLayout.SOUTH);
        frame.add(parentPanel, BorderLayout.CENTER);

        // Set the frame size and make it visible
        frame.setSize(1000, 800);
        frame.setVisible(true);

    }
}








