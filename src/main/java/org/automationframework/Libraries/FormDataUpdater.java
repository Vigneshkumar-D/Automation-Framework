package org.automationframework.Libraries;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class FormDataUpdater extends AbstractComponent{
    WebDriver driver;
    InputDataValidator inputDataValidator;
    public FormDataUpdater(WebDriver driver) {
        super(driver);
        this.driver = driver;
        inputDataValidator = new InputDataValidator(driver);
    }

    public void updateFormData(List<WebElement> formInputEleList, HashMap<String, String> updatedData, HashMap<String, String> inputValidationData, List<WebElement> labelTextEleList) throws InterruptedException, IOException {
        Thread.sleep(2000);
        for (WebElement inputElement : formInputEleList) {
            for (Map.Entry<String, String> entry : updatedData.entrySet()) {
                String fieldName = entry.getKey();
                String value = entry.getValue();
                WebElement labelElement = findLabelElementByFieldName(fieldName, labelTextEleList);
                if (labelElement != null && inputElement.getAttribute("id").equals(labelElement.getAttribute("for"))) {
                    if (inputElement != null && !value.isEmpty() && !value.equalsIgnoreCase("ExistingUserName")) {
                        if (inputElement.getAttribute("type").equals("search")) {
                            WebElement spanElement = inputElement.findElement(By.xpath("./ancestor::div[contains(@class, 'ant-select-selector')]"));
                            spanElement.click();
                            inputElement.sendKeys(value);
                            String xpath = "//div[@title='" + value + "']";
                            if(value.equalsIgnoreCase("BFAL")){
                                xpath = "//span[@title='" + value + "']";
                            }
                            WebElement desiredOption = driver.findElement(By.xpath(xpath));
                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", desiredOption);
                            desiredOption.click();
                        } else {
                            clearExistingData(inputElement);
                            inputElement.sendKeys(value);
                        }
                    }
                    break;
                }
            }
        }
        inputDataValidator.validateInputData(updatedData, formInputEleList, labelTextEleList);
    }
    public WebElement findLabelElementByFieldName(String fieldName, List<WebElement> labelTextEleList) {
        for (WebElement labelElement : labelTextEleList) {
            String labelText = labelElement.getText().replaceAll("\\s+", "");
            if (labelText.equals(fieldName)) {
                return labelElement;
            }
        }
        return null;
    }
    public void clearExistingData(WebElement ele){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForWebElementToAppear(ele);
        ele.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        wait.until(ExpectedConditions.textToBePresentInElementValue(ele, ""));
    }
}

