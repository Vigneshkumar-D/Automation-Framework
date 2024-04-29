package org.automationframework.Libraries;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ExcelReport {
    String filePath = System.getProperty("user.dir") + "//reports//ExcelReport.xlsx";
    public void addErrorMessages(List<String> errMessageList, String reportName) {
        try (Workbook workbook = openOrCreateWorkbook(filePath)) {
            Sheet sheet = workbook.getSheet(reportName);
            int lastRowNum = 0;
            if (sheet == null) {
                sheet = workbook.createSheet(reportName);
            } else {
                lastRowNum = Math.max(sheet.getLastRowNum(), -1);
                lastRowNum++;
            }
            for (int i = 0; i < errMessageList.size(); i++) {
                Row row = sheet.createRow(lastRowNum + i);
                String errMsg = errMessageList.get(i);
                Cell cell = row.createCell(0);
                cell.setCellValue(errMsg);
            }
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                System.out.println("Excel file created successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Workbook openOrCreateWorkbook(String filePath) throws IOException {
        if (Files.exists(Paths.get(filePath))) {
            FileInputStream fileIn = new FileInputStream(filePath);
            return new XSSFWorkbook(fileIn);
        } else {
            return new XSSFWorkbook();
        }
    }
    public void deleteExcelReport(){
        File file = new File(filePath);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                System.out.println("Excel file deleted successfully.");
            } else {
                System.out.println("Failed to delete the Excel file.");
            }
        } else {
            System.out.println("The Excel file does not exist.");
        }
    }

    public void addErrorMessages(List<String> errMessageList, String reportName, String filePath){
        this.filePath = filePath;
        addErrorMessages(errMessageList, reportName);
    }
}


