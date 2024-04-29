package org.automationframework.PageObjects;

import org.automationframework.Libraries.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ChecklistPage extends AbstractComponent{
    WebDriver driver;
    ExcelReport excelReport;
    InputFieldsValidator inputFieldsValidator;
    InputDataValidator inputDataValidator;
    TableDataValidator tableDataValidator;
    FormDataUpdater formDataUpdater;
    TableDataDeleter tableDataDeleter;
    public ChecklistPage(WebDriver driver) {
        super(driver);
        this.driver=driver;
        inputFieldsValidator = new InputFieldsValidator(driver);
        excelReport = new ExcelReport();
        inputDataValidator = new InputDataValidator(driver);
        tableDataValidator = new TableDataValidator(driver);
        formDataUpdater = new FormDataUpdater(driver);
        tableDataDeleter = new TableDataDeleter(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "(//span[normalize-space()='Preventive Maintenance'])")
    WebElement preventiveMaintenanceEle;
    @FindBy(xpath = "(//span[normalize-space()='Configuration'])[1]")
    WebElement configurationELe;
    @FindBy(xpath = "(//a[normalize-space()='Checklist'])")
    WebElement checklistEle;
    @FindBy(xpath = "(//span[normalize-space()='Upload'])")
    WebElement uploadEle;
    @FindBy(xpath = "(//form[@class='ant-form ant-form-horizontal css-dkbvqv'])//input")
    List<WebElement> inputEleList;
    @FindBy(xpath = "(//input[@type='file'])")
    WebElement inputEle;
    HashMap<String, String> inputValidationData = new HashMap<>();
    @FindBy(xpath ="(//label[normalize-space()='Select File'])")
    List<WebElement> inputLabelEleList;
    public void addChecklist() throws InterruptedException, IOException {
        Thread.sleep(2000);
        HashMap<String, String> inputValidationData = new HashMap<>();
        inputValidationData.put("ExcelReportName", "Checklist Report");
        preventiveMaintenanceEle.click();
        configurationELe.click();
        checklistEle.click();
        uploadEle.click();
        inputEle.sendKeys("D:/Automation Framework/Automation-Testing-Framework/reports/ExcelReport.xlsx");

        inputDataValidator.validateInputData(inputValidationData, inputEleList, inputLabelEleList);
    }
}
