package org.automationframework.PageObjects;

import org.automationframework.Libraries.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryPage extends AbstractComponent {
    WebDriver driver;
    ExcelReport excelReport;
    InputFieldsValidator inputFieldsValidator;
    InputDataValidator inputDataValidator;
    TableDataValidator tableDataValidator;
    FormDataUpdater formDataUpdater;
    TableDataDeleter tableDataDeleter;
    public InventoryPage(WebDriver driver) {
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


    @FindBy(xpath = "(//span[normalize-space()='Inventory'])")
    WebElement InventoryButtonEle;
    @FindBy(xpath = "(//a[normalize-space()='Inventory Parts'])")
    WebElement inventoryPartsButtonEle;
    @FindBy(xpath = "(//span[normalize-space()='Add'])")
    WebElement addButtonEle;
    @FindBy(xpath = "(//input[@id='sparePartName'])")
    WebElement sparePartNameEle;
    @FindBy(xpath = "(//input[@id='sparePartNumber'])")
    WebElement sparePartNumberEle;
    @FindBy(xpath = "(//input[@id='image'])")
    WebElement imageEle;
    @FindBy(xpath = "(//textarea[@id='description'])")
    WebElement descriptionEle;
    @FindBy(xpath = "(//input[@id='sparePartTypeId'])")
    WebElement sparePartTypeIdEle;
    @FindBy(xpath = "(//span[contains(@title,'Milling machine')])")
    WebElement millingMachineEle;
    @FindBy(xpath = "(//input[@id='assetFamilyId'])")
    WebElement assetFamilyIdEle;
    @FindBy(xpath = "(//span[@title='SpareFamily'])")
    WebElement spareFamilyEle;
    @FindBy(xpath = "(//span[normalize-space()='Active'])")
    WebElement statusEle;
    @FindBy(xpath = "(//span[normalize-space()='Save'])")
    WebElement saveButtonEle;
    @FindBy(xpath = "(//form[@class='ant-form ant-form-horizontal ant-form-small css-dkbvqv form-horizontal'])//input")
    List<WebElement> inputElementsList;
    @FindBy(xpath = "//label[contains(@class, 'ant-form-item-no-colon')]")
    List<WebElement> inputLabelEleList;
    @FindBy(xpath = "(//form[@class='ant-form ant-form-horizontal ant-form-small css-dkbvqv form-horizontal'])//textarea")
    WebElement textArea;
    List<WebElement> additionalElements = new ArrayList<>();
    public void createInventoryParts() throws InterruptedException, IOException {
        HashMap<String, String> inputValidationData = new HashMap<>();
        inputValidationData.put("ExcelReportName", "Inventory Report");
        Thread.sleep(2000);
        InventoryButtonEle.click();
        waitForWebElementToAppear(inventoryPartsButtonEle);
        inventoryPartsButtonEle.click();
        waitForWebElementToAppear(addButtonEle);
        addButtonEle.click();
        sparePartNameEle.sendKeys("Demo");
        sparePartNumberEle.sendKeys("12");
        imageEle.sendKeys("C:\\Users\\vinay\\Downloads\\45455.jpg");
        descriptionEle.sendKeys("Demo Des");
        sparePartTypeIdEle.click();
        Thread.sleep(1000);
        millingMachineEle.click();
        Thread.sleep(1000);
        assetFamilyIdEle.click();
        Thread.sleep(1000);
        spareFamilyEle.click();
        statusEle.click();
        Thread.sleep(3000);
        additionalElements.add(textArea);
        List<WebElement> combinedList = new ArrayList<>(inputElementsList);
        combinedList.addAll(additionalElements);
        inputDataValidator.validateInputData(inputValidationData, combinedList, inputLabelEleList);
        saveButtonEle.click();
    }

}
