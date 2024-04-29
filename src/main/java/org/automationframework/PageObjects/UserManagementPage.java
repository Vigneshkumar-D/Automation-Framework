package org.automationframework.PageObjects;

import org.automationframework.Libraries.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class UserManagementPage extends AbstractComponent {

    WebDriver driver;
    ExcelReport excelReport;
    InputFieldsValidator inputFieldsValidator;
    InputDataValidator inputDataValidator;
    TableDataValidator tableDataValidator;
    FormDataUpdater formDataUpdater;
    TableDataDeleter tableDataDeleter;
    public UserManagementPage(WebDriver driver) {
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

    @FindBy(xpath = "(//span[normalize-space()='Management Console'])")
    WebElement managementConsoleEle;
    @FindBy(xpath = "(//a[normalize-space()='User Management'])")
    WebElement userManagementEle;
    @FindBy(xpath = "(//span[normalize-space()='Add'])")
    WebElement addButton;
    @FindBy(xpath = "(//input[@id='userName'])")
    WebElement userNameEle;
    @FindBy(xpath = "//input[@id='roleId']")
    WebElement roleNameEle;
    @FindBy(xpath = "//input[@id='email']")
    WebElement emailEle;
    @FindBy(xpath = "//input[@id='contactNumber']")
    WebElement mobileNumberEle;
    @FindBy(xpath = "//input[@id='password']")
    WebElement passwordEle;
    @FindBy(xpath = "//input[@id='ahid']")
    WebElement userSiteEle;
    WebElement userSite;
    WebElement statusEle;
    @FindBy(xpath = "(//button[@type='submit'])[1]")
    WebElement saveButtonEle;
    @FindBy(xpath = "//form[@class='ant-form ant-form-horizontal ant-form-small css-dkbvqv form-horizontal']//input")
    List<WebElement> formInputEleList;
    @FindBy(xpath = "//label[@class='ant-form-item-required ant-form-item-no-colon']")
    List<WebElement> mandatoryInputEleList;
    @FindBy(xpath = "//label[contains(@class, 'ant-form-item-no-colon')]")
    List<WebElement> labelTextEleList;
    @FindBy(xpath = "//div[@class='ant-form-item-explain-error']")
    List<WebElement> errMsgEleList;
    public void createUser(String userName, String role, String email, String mobileNum, String password, String site, String status, HashMap<String, String> inputValidationData) throws Exception {
        Thread.sleep(1000);
        waitForWebElementToAppear(managementConsoleEle);
        managementConsoleEle.click();
        waitForWebElementToAppear(userManagementEle);
        userManagementEle.click();
        addButton.click();
        userNameEle.sendKeys("");
        roleNameEle.sendKeys("");
        emailEle.sendKeys("");
        mobileNumberEle.sendKeys("");
        passwordEle.sendKeys("");
        userSiteEle.sendKeys("");
        saveButtonEle.click();
        inputFieldsValidator.validateInputFields(inputValidationData, formInputEleList, mandatoryInputEleList, labelTextEleList, errMsgEleList);
        userNameEle.sendKeys(userName);
        roleNameEle.click();
        WebElement desiredOption =driver.findElement(By.xpath("//div[contains(text(),'" + role + "')]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(desiredOption).perform();
        desiredOption.click();
//        try {
//            WebElement desiredOption = null;
//            do {
//                Actions actions = new Actions(driver);
//                actions.sendKeys(Keys.ARROW_DOWN).build().perform();
//                desiredOption =driver.findElement(By.xpath("//div[contains(text(),'" + role + "')]"));
////                waitForWebElementToAppear(desiredOption);
//            } while (!desiredOption.isDisplayed());
//            desiredOption.click();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        emailEle.sendKeys(email);
        mobileNumberEle.sendKeys(mobileNum);
        passwordEle.sendKeys(password);
        userSiteEle.click();
        userSite = driver.findElement(By.xpath("(//span[@title='"+ site+"'])[1]"));
        userSite.click();
        statusEle = driver.findElement(By.xpath("(//span[normalize-space()='"+ status +"'])"));
        Thread.sleep(1000);
        System.out.println(statusEle.getText());
        statusEle.click();
        inputDataValidator.validateInputData(inputValidationData, formInputEleList, labelTextEleList);
        Thread.sleep(2000);
        saveButtonEle.click();
    }
    @FindBy(xpath = "//input[@placeholder='Search...']")
    WebElement searchEle;
    @FindBy(xpath = "(//span[@aria-label='edit'])")
    WebElement editElement;
    @FindBy(xpath = "(//span[normalize-space()='Update'])")
    WebElement updateButtonEle;
    public void updateUser(HashMap<String, String> UpdatedData,  HashMap<String, String> inputValidationData) throws InterruptedException, IOException {
        Thread.sleep(2000);
        waitForWebElementToAppear(managementConsoleEle);
        managementConsoleEle.click();
        Thread.sleep(1000);
        waitForWebElementToAppear(userManagementEle);
        userManagementEle.click();
        Thread.sleep(1000);
        waitForWebElementToAppear(searchEle);
        searchEle.sendKeys(UpdatedData.get("ExistingUserName"));
        Thread.sleep(2000);
        editElement.click();
        formDataUpdater.updateFormData(formInputEleList, UpdatedData,inputValidationData, labelTextEleList);
        updateButtonEle.click();
    }

    @FindBy(xpath = "//thead//th")
    List<WebElement> tableHeadList;
    @FindBy(xpath = "//tr[@data-testId='row']")
    List<WebElement> tableRowList;
    @FindBy(xpath = "//li[@title='Next Page']//button[@type='button']")
    WebElement nextPageButton;
    public void validateUserTableData(HashMap<String, String> inputValidationData) throws InterruptedException {
        Thread.sleep(2000);
        tableDataValidator.validateTableData(inputValidationData,  tableHeadList, tableRowList, nextPageButton);
    }

    @FindBy(xpath = "//button[@data-testid='delete-button']")
    WebElement deleteButtonEle;
    public void deleteRowFromTable(HashMap<String, String> deleteTableData, HashMap<String, String> inputValidationData) throws InterruptedException {
        Thread.sleep(2000);
        waitForWebElementToAppear(managementConsoleEle);
        managementConsoleEle.click();
        Thread.sleep(1000);
        waitForWebElementToAppear(userManagementEle);
        userManagementEle.click();
        Thread.sleep(1000);
        waitForWebElementToAppear(searchEle);
        searchEle.sendKeys(deleteTableData.get("UserName"));
        Thread.sleep(2000);
        deleteButtonEle.click();
        tableDataDeleter.deleteTableData(deleteTableData, inputValidationData,  tableHeadList, tableRowList, nextPageButton);
    }

    @FindBy(xpath = "(//a[normalize-space()='Entity'])")
    WebElement entityEle;
    @FindBy(xpath = "//input[@id=\"ahname\"]")
    WebElement entityNameEle;
    @FindBy(xpath = "//input[@id=\"ahparentId\"]")
    WebElement underEle;
    public void createEntity() throws InterruptedException {
        Thread.sleep(1000);
        waitForWebElementToAppear(managementConsoleEle);
        managementConsoleEle.click();
        waitForWebElementToAppear(entityEle);
        addButton.click();
        entityNameEle.sendKeys("Demo");
        underEle.click();
        Thread.sleep(1000);
//
//        WebElement desiredOption = null;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(100));
//        while (desiredOption==null) {
//                    Actions actions = new Actions(driver);
//                    actions.sendKeys(Keys.ARROW_DOWN).build().perform();
//                   try{
//                       desiredOption = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'test1')]")));
//                   }catch (TimeoutException | NoSuchElementException e){
//                       continue;
//                   }
//                    System.out.println(desiredOption);
//        }
//        desiredOption.click();

        try {
            WebElement desiredOption = null;
            do {
                Actions actions = new Actions(driver);
                actions.sendKeys(Keys.ARROW_DOWN).build().perform();
                try{
                       desiredOption = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'test1')]")));
                   }catch (TimeoutException | NoSuchElementException e){
                       continue;
                   }
            } while (!Objects.requireNonNull(desiredOption).isDisplayed());
            desiredOption.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
