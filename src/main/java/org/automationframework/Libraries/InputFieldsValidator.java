package org.automationframework.Libraries;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputFieldsValidator {
    WebDriver driver;
    List<String> errorList = new ArrayList<>();
    ExcelReport excelReport = new ExcelReport();
    List<WebElement> inputElementsList;
    List<WebElement> mandatoryEleList;
    List<WebElement> inputLabelEleList;
    List<WebElement> errMessageList;
    public InputFieldsValidator(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void validateInputFields(HashMap<String, String> inputValidationData, List<WebElement> inputElementsList, List<WebElement> mandatoryEleList, List<WebElement> inputLabelEleList,  List<WebElement> errMessageList) throws Exception {
        this.inputElementsList = inputElementsList;
        this.mandatoryEleList = mandatoryEleList;
        this.inputLabelEleList = inputLabelEleList;
        this.errMessageList = errMessageList;
        validateNumberOfInputFields(inputValidationData.get("NumberOfInputFields"));
        validateNumberOfMandatoryInputFields(inputValidationData.get("MandatoryInputFields"));
        validateNumberNonMandatoryOfInputFields(inputValidationData.get("NonMandatoryInputFields"));
        validateInputElementTypes(inputValidationData);
        List<String> mandatoryFieldNames = getMandatoryFieldNames(inputValidationData);
        validateMandatoryElements(mandatoryFieldNames);
        validateErrorMessage(inputValidationData);
        excelReport.addErrorMessages(errorList, inputValidationData.get("ExcelReportName"));
    }

    private void validateInputElementTypes(HashMap<String, String> inputValidationData) {
        for (WebElement labelEle : inputLabelEleList) {
            String labelText = labelEle.getText().replaceAll("\\s+", "");
            String labelId = labelEle.getAttribute("for");
            String expectedInputType = inputValidationData.get(labelText);
            if (expectedInputType != null) {
                WebElement inputElement = driver.findElement(By.id(labelId));
                if(inputElement.getAttribute("id").equalsIgnoreCase("active")){
                    inputElement = driver.findElement(By.xpath("//div[@id='" + labelId +"']//input"));
                }
                String actualInputType = inputElement.getAttribute("type");
                if (!expectedInputType.equalsIgnoreCase(actualInputType)) {
                    errorList.add("Input Type Error: Mismatch in input element type for element with Label: " + labelText);
                }
            } else {
                System.out.println("No validation data found for label text: " + labelText);
            }
        }
    }

    private void validateNumberOfInputFields(String numberOfInputFields) {
        if(inputElementsList.size() != Integer.parseInt(numberOfInputFields)) {
            errorList.add("Input Count Error:Actual number of input fields are not equals to expected number of input fields. Expected: "+ numberOfInputFields + ", Actual: "+inputElementsList.size());
        }
    }

    private void validateNumberOfMandatoryInputFields(String mandatoryInputFields) {
        if(mandatoryEleList.size() != Integer.parseInt(mandatoryInputFields)) {
            errorList.add("Mandatory Input Count Error: Actual number of mandatory input fields are not equals to expected number of mandatory input fields. Expected: "+ mandatoryInputFields + ", Actual: "+mandatoryEleList.size());
        }
    }

    private void validateNumberNonMandatoryOfInputFields(String nonMandatoryInputFields){
        if((inputElementsList.size() - mandatoryEleList.size())  != Integer.parseInt(nonMandatoryInputFields)) {
            errorList.add("Non-Mandatory Input Count Error: Actual number of non-mandatory input fields are not equals to expected number of non-mandatory input fields. Expected: "+ nonMandatoryInputFields + ", Actual: "+(inputElementsList.size()-mandatoryEleList.size()));
        }
    }

    private List<String> getMandatoryFieldNames(HashMap<String, String> inputValidationData) {
        return inputValidationData.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("MandatoryField"))
                .map(Map.Entry::getValue)
                .toList();
    }

    private void validateMandatoryElements(List<String> mandatoryFieldNames){
        for (WebElement mandatoryEle : mandatoryEleList) {
            String mandatoryFieldText = mandatoryEle.getText().trim();
            boolean isMandatory = mandatoryFieldNames.contains(mandatoryFieldText);
            if (!isMandatory) {
                errorList.add("Mandatory Input Error: The element is not a valid mandatory element. Actual text: '" + mandatoryFieldText + "'.");
            }
        }
    }

    public void validateErrorMessage(HashMap<String, String> inputValidationData) {
        List<String> expectedErrorMessageList = inputValidationData.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("ErrorMessage"))
                .map(Map.Entry::getValue)
                .toList();
        for (WebElement errMessageEle: errMessageList) {
            if(errMessageEle.getText().isBlank()){
                continue;
            }
            if(!expectedErrorMessageList.contains(errMessageEle.getText())){
                errorList.add("Error Message: The element with id " +errMessageEle.getAttribute("id") + "doesn't contains expected error message. Actual: "+ errMessageEle.getText());
            }
        }
    }
}

