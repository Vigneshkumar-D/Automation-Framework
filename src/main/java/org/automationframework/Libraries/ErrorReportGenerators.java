package org.automationframework.Libraries;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ErrorReportGenerators {
    private static final int INITIAL_COLUMN_INDEX = 1;
    static String filePath = System.getProperty("user.dir") + "//reports//report.xlsx";
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

            int rowNum = sheet.getLastRowNum() + 1;

            for (int i = 0; i < errMessageList.size(); i++) {
                Row row = sheet.createRow(rowNum + i);

                Cell cell1 = row.createCell(0);
                cell1.setCellValue(i + 1); // Set S.No

                Cell cell2 = row.createCell(1);
                cell2.setCellValue(reportName); // Set Report Name

                Cell cell3 = row.createCell(2);
                cell3.setCellValue(errMessageList.get(i)); // Set Error Message
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

    private static Map<String, Integer> getColumnMap(Sheet sheet) {
        Map<String, Integer> columnMap = new HashMap<>();
        Row headerRow = sheet.getRow(0);
        for (int i = INITIAL_COLUMN_INDEX; i <= headerRow.getLastCellNum(); i += 3) {
            Cell cell = headerRow.getCell(i);
            if (cell != null) {
                columnMap.put(cell.getStringCellValue(), i);
            }
        }
        return columnMap;
    }

    private static int findNextAvailableRow(Sheet sheet) {
        int lastRowNum = sheet.getLastRowNum();
        return lastRowNum + 1;
    }

    private static int getReportStartColumn(String reportName, Map<String, Integer> columnMap) {
        if (columnMap.containsKey(reportName)) {
            return columnMap.get(reportName);
        } else {
            int lastColumnIndex = columnMap.isEmpty() ? INITIAL_COLUMN_INDEX : columnMap.values().stream().mapToInt(Integer::intValue).max().getAsInt();
            int newColumnIndex = lastColumnIndex + 3;
            columnMap.put(reportName, newColumnIndex);
            return newColumnIndex;
        }
    }
    public static void main(String[] args) {
        // Sample error messages and report names
        List<String> errMessageList1 = Arrays.asList("Error 1", "Error 2", "Error 3");
        List<String> errMessageList2 = Arrays.asList("Error A", "Error B", "Error C");

        // Testing addErrorMessages method
        addErrorMessages(errMessageList1, "Role Report");
        addErrorMessages(errMessageList2, "User Report");
    }
}
