package org.automationframework.Libraries;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TableDataDeleter {
    WebDriver driver;
    TableDataValidator tableDataValidator;
    ExcelReport excelReport;
    List<String> errMsgList = new ArrayList<>();
    String successMsg = "Deleted Successfully";
    public TableDataDeleter(WebDriver driver) {
        this.driver = driver;
        tableDataValidator = new TableDataValidator(driver);
        excelReport = new ExcelReport();
     }
    @FindBy(xpath = "(//span[normalize-space()='Yes'])")
    WebElement yesButtonEle;
    @FindBy(xpath = "(//span[normalize-space()='No'])")
    WebElement noButtonEle;
    public void deleteTableData(HashMap<String, String> deleteTableData, HashMap<String, String> inputValidationData, List<WebElement> tableHeadList, List<WebElement> tableRowList, WebElement nextPageButton) throws InterruptedException {
        String ConfirmationStatus = deleteTableData.get("ConfirmationStatus");
        if(ConfirmationStatus.equalsIgnoreCase("Yes")){
            yesButtonEle.click();
            WebElement successMshEle = driver.findElement(By.xpath("//div[@class='ant-message-notice-content']//span[@role='img']/following-sibling::span"));
            if(!successMshEle.getText().equalsIgnoreCase(successMsg)){
                errMsgList.add("Data Deletion Error: "+successMshEle.getText());
            }
        }else{
            noButtonEle.click();
            tableDataValidator.validateTableData(inputValidationData, tableHeadList, tableRowList, nextPageButton);
        }
        excelReport.addErrorMessages(errMsgList, deleteTableData.get("ExcelReportName"));
    }
}
