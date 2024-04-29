package org.automationframework.Data;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.automationframework.TestComponents.BaseTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class DataProvider {
    public ArrayList<String> getTestData(String testCase) throws IOException {
        FileInputStream fis=new FileInputStream("path/to/your/excel-file");
        XSSFWorkbook workbook= new XSSFWorkbook(fis);
        ArrayList<String> userCredentials = new ArrayList<>();

        int sheets = workbook.getNumberOfSheets();
        for (int i=0; i<sheets; i++){
            if(workbook.getSheetName(i).equalsIgnoreCase("excel sheet name")){
                XSSFSheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rows = sheet.iterator();
                Row firstRow = rows.next();
                Iterator<Cell> ce = firstRow.cellIterator();
                int k=0;
                int column=0;
                while (ce.hasNext()){
                    Cell value = ce.next();
                    if(value.getStringCellValue().equalsIgnoreCase("cell name")){
                        column=k;
                    }
                    k++;
                }
                while(rows.hasNext()){
                    Row r = rows.next();
                    if(r.getCell(column).getStringCellValue().equalsIgnoreCase(testCase)){
                        Iterator<Cell> cv = r.cellIterator();
                        while ((cv.hasNext())){
                            Cell nextCell = cv.next();
                            if (nextCell.getCellType() == CellType.STRING) {
                                userCredentials.add(nextCell.getStringCellValue());
                            } else if (nextCell.getCellType() == CellType.NUMERIC) {
                                int numericData = (int) nextCell.getNumericCellValue();
                                String numericAsString = String.valueOf(numericData);
                                userCredentials.add(numericAsString);
                            }
                        }
                    }
                }
            }
        }
        return userCredentials;
    }

    @org.testng.annotations.DataProvider(name = "data provider name1")
    public Object[][] getUserData() throws IOException {
        FileInputStream fis = new FileInputStream("path/to/your/excel-file");
        XSSFWorkbook workbook= new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(1);
        int rowCount = sheet.getPhysicalNumberOfRows();
        XSSFRow row = sheet.getRow(0);
        int columnCount = row.getLastCellNum();
        Object[][] data = new Object[rowCount-1][columnCount];
        for(int i=0; i<rowCount-1; i++){
            row = sheet.getRow(i+1);
            for(int j=0; j<columnCount; j++){
                XSSFCell cell = row.getCell(j);
                data[i][j] = BaseTest.formatter.formatCellValue(cell);
            }
        }
        return data;
    }

    @org.testng.annotations.DataProvider(name = "data provider name2")
    public Object[][] getUserGroupData() throws IOException {
        FileInputStream fis = new FileInputStream("path/to/your/excel-file");
        XSSFWorkbook workbook= new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(3);
        int rowCount = sheet.getPhysicalNumberOfRows();
        XSSFRow row = sheet.getRow(0);
        int columnCount = row.getLastCellNum();
        Object[][] data = new Object[rowCount-1][columnCount];
        for(int i=0; i<rowCount-1; i++){
            row = sheet.getRow(i+1);
            for(int j=0; j<columnCount; j++){
                XSSFCell cell = row.getCell(j);
                data[i][j] = BaseTest.formatter.formatCellValue(cell);
            }
        }
        return data;
    }

    @org.testng.annotations.DataProvider(name = "data provider name3")
    public Object[][] getSchedulerData() throws IOException {
        FileInputStream fis = new FileInputStream("path/to/your/excel-file");
        XSSFWorkbook workbook= new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(3);
        int rowCount = sheet.getPhysicalNumberOfRows();
        XSSFRow row = sheet.getRow(0);
        int columnCount = row.getLastCellNum();
        Object[][] data = new Object[rowCount-1][columnCount];
        for(int i=0; i<rowCount-1; i++){
            row = sheet.getRow(i+1);
            for(int j=0; j<columnCount; j++){
                XSSFCell cell = row.getCell(j);
                data[i][j] = BaseTest.formatter.formatCellValue(cell);
            }
        }
        return data;
    }

    @org.testng.annotations.DataProvider(name = "data provider name4")
    public Object[][] getResolutionWorkOrderData() throws IOException {
        FileInputStream fis = new FileInputStream("path/to/your/excel-file");
        XSSFWorkbook workbook= new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(4);
        int rowCount = sheet.getPhysicalNumberOfRows();
        XSSFRow row = sheet.getRow(0);
        int columnCount = row.getLastCellNum();
        Object[][] data = new Object[rowCount-1][columnCount];
        for(int i=0; i<rowCount-1; i++){
            row = sheet.getRow(i+1);
            for(int j=0; j<columnCount; j++){
                XSSFCell cell = row.getCell(j);
                data[i][j] = BaseTest.formatter.formatCellValue(cell);
            }
        }
        return data;
    }
}
