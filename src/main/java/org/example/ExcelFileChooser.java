package org.example;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExcelFileChooser extends JFrame {
    private List<String> selectedFilePaths;
    public static boolean isSubmitClicked;

    public ExcelFileChooser() {
        selectedFilePaths = new ArrayList<>();
        final String[] recentPath = {null};
        JButton selectFilesButton = new JButton("Select Files");
        Dimension buttonSize = new Dimension(180, 30);
        selectFilesButton.setPreferredSize(buttonSize);
        selectFilesButton.setBackground(new Color(59, 191, 129));
        selectFilesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx", "xls");
                fileChooser.setFileFilter(filter);
                if(recentPath[0] !=null)
                {fileChooser.setCurrentDirectory(new File(recentPath[0]));}
//                int option = fileChooser.showOpenDialog(ExcelFileChooser.this);
//                if (option == JFileChooser.APPROVE_OPTION) {
//                    File[] selectedFiles = fileChooser.getSelectedFiles();
//                    for (File file : selectedFiles) {
//                        selectedFilePaths.add(file.getAbsolutePath());
//                    }
                int option = fileChooser.showOpenDialog(ExcelFileChooser.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File[] selectedFiles = fileChooser.getSelectedFiles();
                    for (File file : selectedFiles) {
                        String filePath = file.getAbsolutePath();
                        recentPath[0] = filePath;
                        if (file.exists() && selectedFilePaths.contains(filePath)) {
                            int result = JOptionPane.showConfirmDialog(ExcelFileChooser.this, "File '" + filePath + "' already exists.", "File Already Exists", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                            if (result == JOptionPane.YES_OPTION) {

                            }
                        } else if (!selectedFilePaths.contains(filePath)) {
                            selectedFilePaths.add(filePath);

                        }
                        recentPath[0] = filePath;
                    }
                    refreshFileList();
                }
            }
        });

        JButton getPathsButton = new JButton("Submit");
        Dimension buttonSize1 = new Dimension(180, 30);
        getPathsButton.setPreferredSize(buttonSize1);
        getPathsButton.setBackground(new Color(59, 191, 129));
        getPathsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (String path : selectedFilePaths) {
                    System.out.println(path);
                }
                isSubmitClicked = true; // Set the flag to indicate submit button is clicked
                dispose(); // Close the GUI
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(selectFilesButton);
        buttonPanel.add(getPathsButton);

        JTextArea fileListTextArea = new JTextArea(10, 30);
        fileListTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(fileListTextArea);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Excel File Chooser");
        pack();
        setLocationRelativeTo(null);
    }

    private void refreshFileList() {
        StringBuilder fileList = new StringBuilder();
        for (String path : selectedFilePaths) {
            fileList.append("   ").append(ExcelUtils.getFileName(path)).append("\n");
        }
        JTextArea fileListTextArea = (JTextArea) ((JScrollPane) getContentPane().getComponent(1)).getViewport().getView();
        fileListTextArea.setText(fileList.toString());
        fileListTextArea.setFont(new Font(Font.SERIF, Font.BOLD, 13));
    }

    public List<String> getSelectedFilePaths() {
        return selectedFilePaths;
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                ExcelFileChooser fileChooser = new ExcelFileChooser();
//                fileChooser.setVisible(true);
//            }
//        });

//    }
}
