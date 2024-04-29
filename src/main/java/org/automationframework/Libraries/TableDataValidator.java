package org.automationframework.Libraries;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.*;

public class TableDataValidator {
    WebDriver driver;
    List<String> tableDataErrList = new ArrayList<>();
    ExcelReport excelReport;
    public TableDataValidator(WebDriver driver){
        this.driver = driver;
        excelReport = new ExcelReport();
    }

    public void validateTableData(HashMap<String, String> inputValidationData, List<WebElement> tableHeadList, List<WebElement> tableRowList, WebElement nextPageButton) throws InterruptedException {
        Thread.sleep(2000);
        boolean hasNextPage = true;
        boolean foundText = false;
        while (hasNextPage) {
            if(tableRowList!=null){
                for (WebElement row : tableRowList) {
                    List<WebElement> cells = row.findElements(By.tagName("td"));
                    for (int i = 0; i < cells.size(); i++) {
                        String tableHead = tableHeadList.get(i).getText();
                        tableHead = tableHead.replaceAll("\\s+", "");
                        if (inputValidationData.containsKey(tableHead)) {
                            String expectedValue = inputValidationData.get(tableHead);
                            String actualValue = cells.get(i).getText();
                            if (actualValue.equals(expectedValue) && !actualValue.isEmpty()) {
                                foundText = true;
                            } else {
                                foundText = false;
                                break;
                            }
                        }
                    }
                    if (foundText) {
                        break;
                    }
                }
            }
            if(nextPageButton!=null){
                try {
                    if (nextPageButton.isEnabled()) {
                        nextPageButton.click();
                    } else {
                        hasNextPage = false;
                    }
                } catch (NoSuchElementException e) {
                    hasNextPage = false;
                }
            }
        }
        String errMsg = "The table dose not contains the expected data ";
        StringBuilder inputData= new StringBuilder();
        if(!foundText){
            for (Map.Entry<String, String> data: inputValidationData.entrySet()) {
                if(data.toString().startsWith("ExcelReportName")){
                    continue;
                }
                String s =  data + ", ";
                inputData.append(s);
            }
            tableDataErrList.add(errMsg+inputData);
        }
        excelReport.addErrorMessages(tableDataErrList, inputValidationData.get("ExcelReportName"));
    }
}
