package org.automationframework.Libraries;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ErrorReportGenerator {

    private static final int INITIAL_COLUMN_INDEX = 0; // Starting column index for report names
    static String filePath = System.getProperty("user.dir") + "//reports//report.xlsx";
    public static  void addErrorMessages(List<String> errMessageList, String reportName) {

        try (Workbook workbook = openOrCreateWorkbook(filePath)) {
            Sheet sheet = workbook.getSheet("ErrorReports");
            if (sheet == null) {
                sheet = workbook.createSheet("ErrorReports");
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("S.No");
                headerRow.createCell(1).setCellValue("Report Name");
                headerRow.createCell(2).setCellValue("Error Message");
            }

            // Find the last used column index
            int lastColumnIndex = sheet.getRow(0).getLastCellNum();

            // Find the next available starting column index for this report
            int startingColumnIndex = lastColumnIndex + 1;

            // Create header row for this report
            Row headerRow = sheet.getRow(0);
            Cell reportNameHeaderCell = headerRow.createCell(startingColumnIndex);
            reportNameHeaderCell.setCellValue("Report Name");
            Cell errorMessageHeaderCell = headerRow.createCell(startingColumnIndex + 1);
            errorMessageHeaderCell.setCellValue("Error Message");

            int rowNum = sheet.getLastRowNum() + 1;

            for (int i = 0; i < errMessageList.size(); i++) {
                Row row = sheet.createRow(rowNum + i);

                Cell cell1 = row.createCell(0);
                cell1.setCellValue(i + 1); // Set S.No

                Cell cell2 = row.createCell(startingColumnIndex);
                cell2.setCellValue(reportName); // Set Report Name

                Cell cell3 = row.createCell(startingColumnIndex + 1);
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
        return new XSSFWorkbook();
    }

    public static void main(String[] args) {
        // Sample error messages and report names
        List<String> errMessageList1 = Arrays.asList("Error 1", "Error 2", "Error 3");
        List<String> errMessageList2 = Arrays.asList("Error A", "Error B", "Error C");
        List<String> errMessageList3 = Arrays.asList("Error !", "Error @", "Error #");

        // Testing addErrorMessages method
        addErrorMessages(errMessageList1, "Role Report");
        addErrorMessages(errMessageList2, "User Report");
        addErrorMessages(errMessageList3, "Asset Report");
    }
}
