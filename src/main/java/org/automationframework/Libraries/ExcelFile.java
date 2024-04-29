package org.automationframework.Libraries;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
public class ExcelFile {
    static String filePath = System.getProperty("user.dir") + "//reports//report.xlsx";
//    public static void addErrorMessages(List<String> errMessageList, String reportName) {
//        try (Workbook workbook = openOrCreateWorkbook(filePath)) {
//            Sheet sheet = workbook.getSheet("ErrorReports");
//            if (sheet == null) {
//                sheet = workbook.createSheet("ErrorReports");
//                Row headerRow = sheet.createRow(0);
//                headerRow.createCell(0).setCellValue("S.No");
//                headerRow.createCell(1).setCellValue(reportName);
//                headerRow.createCell(2).setCellValue("Error Message");
//            } else if (findColumnIndex(sheet.getRow(0), reportName) == -1) {
//                Row headerRow = sheet.getRow(0);
//                int lastColumnIndex = headerRow.getLastCellNum();
//                headerRow.createCell(lastColumnIndex).setCellValue(reportName);
//                headerRow.createCell(lastColumnIndex + 1).setCellValue("Error Message");
//            }
//            int rowNum = findNextAvailableRow(sheet);
//            for (int i = 0; i < errMessageList.size(); i++) {
//                Row row = sheet.getRow(rowNum);
//                if (row == null) {
//                    row = sheet.createRow(rowNum);
//                }
//                Cell cell1 = row.createCell(0);
//                cell1.setCellValue(rowNum + 1); // Set S.No
//
//                Cell cell2 = row.createCell(1);
//                cell2.setCellValue(reportName); // Set Report Name
//
//                Cell cell3 = row.createCell(findColumnIndex(sheet.getRow(0), reportName));
//                cell3.setCellValue(errMessageList.get(i)); // Set Error Message
//
//                rowNum++;
//            }
//
//            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
//                workbook.write(fileOut);
//                System.out.println("Excel file updated successfully!");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    private static int findNextAvailableRow(Sheet sheet) {
//        int lastRowNum = sheet.getLastRowNum();
//        return lastRowNum + 1;
//    }
//
//    private static int findColumnIndex(Row headerRow, String columnName) {
//        if (headerRow == null) {
//            return -1;
//        }
//
//        int lastCellNum = headerRow.getLastCellNum();
//        for (int i = 0; i < lastCellNum; i++) {
//            Cell cell = headerRow.getCell(i);
//            if (cell != null && cell.getStringCellValue().equals(columnName)) {
//                return i;
//            }
//        }
//        return -1;
//    }
//    private static Workbook openOrCreateWorkbook(String filePath) throws IOException {
//        if (Files.exists(Paths.get(filePath))) {
//            FileInputStream fileIn = new FileInputStream(filePath);
//            return new XSSFWorkbook(fileIn);
//        } else {
//            return new XSSFWorkbook();
//        }
//    }
public static void addErrorMessages(List<String> errMessageList, String reportName) {
    try (Workbook workbook = openOrCreateWorkbook(filePath)) {
        Sheet sheet = workbook.getSheet("ErrorReports");
        if (sheet == null) {
            sheet = workbook.createSheet("ErrorReports");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("S.No");
            headerRow.createCell(1).setCellValue("Report Name");
            headerRow.createCell(2).setCellValue("Error Message");
        }

        int rowNum = findNextAvailableRow(sheet);

        for (int i = 0; i < errMessageList.size(); i++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }

            Cell cell1 = row.createCell(0);
            cell1.setCellValue(rowNum + 1); // Set S.No

            Cell cell2 = row.createCell(1);
            cell2.setCellValue(reportName); // Set Report Name

            Cell cell3 = row.createCell(2);
            cell3.setCellValue(errMessageList.get(i)); // Set Error Message

            rowNum++;
        }

        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            System.out.println("Excel file updated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    private static Workbook openOrCreateWorkbook(String filePath) throws IOException {
        if (Files.exists(Paths.get(filePath))) {
            FileInputStream fileIn = new FileInputStream(filePath);
            return new XSSFWorkbook(fileIn);
        } else {
            return new XSSFWorkbook();
        }
    }

    private static int findNextAvailableRow(Sheet sheet) {
        int lastRowNum = sheet.getLastRowNum();
        return lastRowNum + 1;
    }
    public static void main(String[] args) {
        // Sample error messages and report names
//        List<String> errMessageList1 = Arrays.asList("Error 1", "Error 2", "Error 3");
//        List<String> errMessageList2 = Arrays.asList("Error A", "Error B", "Error C");
//
//        // Testing addErrorMessages method
//        addErrorMessages(errMessageList1, "Role Report");
//        addErrorMessages(errMessageList2, "User Report");

    }

}
