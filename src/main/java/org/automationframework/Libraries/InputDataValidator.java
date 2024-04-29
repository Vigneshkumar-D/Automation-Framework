package org.automationframework.Libraries;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
public class InputDataValidator {
    WebDriver driver;
    ExcelReport excelReport;
    String textPattern = "^[a-zA-Z0-9]+(?:\\s+[a-zA-Z0-9]+)*$";
    String textStartsWithPattern = "^[a-zA-Z][a-zA-Z0-9 ]*$";
    String mobileNumberPattern = "^\\+\\d{1,3}-\\d{10}$";
    String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    String time24HrsPattern = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
    String time12HrsPattern = "((1[0-2]|0?[1-9]):([0-5][0-9])\\s?(AM|PM))";
    public List<String> inputErrList = new ArrayList<>();
    public InputDataValidator(WebDriver driver){
        this.driver = driver;
        excelReport = new ExcelReport();
        PageFactory.initElements(driver, this);
    }
    private void validateTextInput(String inputText, String labelText){
        if (inputText.isEmpty()) {
            inputErrList.add("Input Data Error: Input cannot be empty for "+ labelText+".");
        }if (inputText.trim().length() != inputText.length()) {
            inputErrList.add("Input Data Error: Input cannot have leading or trailing whitespace for "+ labelText+".");
        }if(!Pattern.matches(textPattern, inputText)){
            inputErrList.add("Input Data Error: Input must contain only alphanumeric characters for "+ labelText+".");
        }if(!Pattern.matches(textStartsWithPattern, inputText)){
            inputErrList.add("Input Data Error: Input must start with alphabetic characters for "+ labelText+".");
        }
    }
    private void validateMobileNumberInput(String inputText, String labelText){
        try {
            int numericValue = Integer.parseInt(inputText);
        } catch (NumberFormatException e) {
            inputErrList.add("Input Data Error: Input must be a numeric value for "+ labelText+".");
        }
        if (!Pattern.matches(mobileNumberPattern, inputText)) {
            inputErrList.add("Input Data Error: Mobile number must start with a country code and be 10 digits long after the country code.");
        }
    }
    private void validateEmailInput(String inputText, String labelText){
        if (!Pattern.matches(emailPattern, inputText)) {
            inputErrList.add("Input Data Error: Input must be in email format for "+labelText+".");
        }
    }
    private void validatePasswordInput(String inputText){
        if (inputText.length() <= 6 || inputText.length() >= 14) {
            inputErrList.add("Input Data Error: Password length must be between 8 and 20 characters.");
        }if (!inputText.matches(".*[A-Z].*")) {
            inputErrList.add("Input Data Error: Password must contain at least one uppercase letter.");
        }if (!inputText.matches(".*[a-z].*")) {
            inputErrList.add("Input Data Error: Password must contain at least one lowercase letter.");
        }if (!inputText.matches(".*\\d.*")) {
            inputErrList.add("Input Data Error: Password must contain at least one digit.");
        }if (!inputText.matches("^[a-zA-Z][a-zA-Z0-9!@#$%^&*()-+=]*$")) {
            inputErrList.add("Input Data Error: Password may contain the following special characters: !@#$%^&*()-+=");
        }if (inputText.matches(".*\\s.*")) {
            inputErrList.add("Input Data Error: Password cannot contain whitespace characters.");
        }
    }
    private void validateRadioInput(String inputText){
        if (inputText == null || inputText.isEmpty()) {
            inputErrList.add("Input Data Error: Please select an option for "+inputText+".");
        }
    }
    private void validateDateInput(String inputText){
        String[] dateFormats = {
                "yyyy-MM-dd", "dd-MM-yyyy", "MM-dd-yyyy",
                "yyyy/MM/dd", "dd/MM/yyyy", "MM/dd/yyyy",
                "yyyy-M-dd", "dd-M-yyyy", "MM-d-yyyy",
                "yyyy/M/dd", "dd/M/yyyy", "MM/d/yyyy",
                "yyyy-M-d", "d-M-yyyy", "MM-d-yyyy",
                "yyyy/M/d", "d/M/yyyy", "MM/d/yyyy"
        };
        boolean isValidDate = false;
        for (String dateFormat : dateFormats) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
                sdf.setLenient(false);
                Date date = sdf.parse(inputText);
                System.out.println(date);
                isValidDate = true;
                break;
            } catch (ParseException e) {
                System.out.println("Parsing failed for this format, try the next one");
            }
        }
        if(!isValidDate){
            inputErrList.add("Input Data Error: Invalid date format. Expected formats are " + String.join(", ", dateFormats));
        }
    }
    private void validateSearchInput(String inputText, String labelText) {
        if(inputText.isEmpty()){
            inputErrList.add("Input Data Error: Input cannot be empty for "+labelText+".");
        }if(!Pattern.matches(textPattern, inputText)){
            inputErrList.add("Input Data Error: Input must contain only alphanumeric characters for "+labelText+".");
        }
    }
    private void validateTextareaInput(String inputText, String labelText) {
        inputText = inputText.trim();
        if (inputText.isEmpty()) {
            inputErrList.add("Input Data Error: Input cannot be empty for "+ labelText+".");
        }if (inputText.trim().length() != inputText.length()) {
            inputErrList.add("Input Data Error: Input cannot have leading or trailing whitespace for "+ labelText+".");
        }
    }
    private void validateTimeInput(String inputText){
        if(!Pattern.matches(time24HrsPattern, inputText)){
            inputErrList.add("Input Data Error: "+inputText+" is not a valid time.");
        }else if(!Pattern.matches(time12HrsPattern, inputText)){
            inputErrList.add("Input Data Error: "+inputText+" is not a valid time.");
        }
    }
    private void validateCheckboxInput(WebElement inputEle, String labelText) {
        boolean isChecked = inputEle.isSelected();
        if (!isChecked) {
            inputErrList.add("Input Data Error: Checkbox must be checked for " + labelText + ".");
        }
    }
    private void validateFileInput(WebElement inputEle, String labelText) throws IOException {
        WebElement fileNameEle = driver.findElement(By.xpath("//span[@class='ant-upload-list-item-name']"));
        String filePath = System.getProperty("user.dir") + "//reports//";
        String fileName = fileNameEle.getText();
        File file = null;
        if(!fileName.endsWith(".xlsx") || !fileName.endsWith(".txt")){
            WebElement imageEle = driver.findElement(By.xpath("(//img[@alt='"+fileName+"'])"));
            String src = imageEle.getAttribute("src");
            if (src != null && src.startsWith("data:")) {
                try {
                    String base64Image = src.split(",")[1];
                    byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                    String fileExtension = src.substring(src.indexOf('/') + 1, src.indexOf(';'));
                    file = new File(filePath+fileName + "." + fileExtension);
                    try (OutputStream outputStream = new FileOutputStream(file)) {
                        outputStream.write(imageBytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                        inputErrList.add("Failed to save image file for " + labelText + ".");
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    inputErrList.add("Invalid base64 image data for " + labelText + ".");
                }
            } else {
                inputErrList.add("Input Data Error: No data URI found for " + labelText + ".");
            }
        }
        if (fileName.isEmpty()) {
            inputErrList.add("Input Data Error: No file selected for " + labelText + ".");
        }
        if (file == null || !file.exists()) {
            inputErrList.add("Input Data Error: File does not exist for " + labelText + ".");
            return;
        }
        String fileType = fileName.substring(fileName.lastIndexOf('.') + 1);
        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        long fileSizeInMB = fileSizeInKB / 1024;
        List<String> allowedFileTypes = Arrays.asList("xlsx", "txt", "jpeg", "jpg", "png", "gif", "webp");
        if (fileSizeInMB > 5) {
            inputErrList.add("Input Data Error: File size exceeds the maximum allowed size (5 MB) for " + labelText + ".");
        }if(!allowedFileTypes.contains(fileType)){
            inputErrList.add("Input Data Error: The uploaded file must be in allowed format (with the .xlsx , .txt , .JPEG, .JPG, .PNG, .GIF) for " + labelText + ".");
        }
        if (file.exists()){
            file.delete();
        }
    }
    public void validateInputData(HashMap<String, String> inputValidationData, List<WebElement> inputElementsList, List<WebElement> inputLabelEleList) throws IOException {
        for (WebElement inputEle: inputElementsList) {
            String type = inputEle.getAttribute("type");
            if(inputEle.getTagName().equalsIgnoreCase("textarea")){
                type = "textarea";
            }
            String id = inputEle.getAttribute("id");
            String inputText = inputEle.getAttribute("value");
            String labelText = null;
            for (WebElement labelEle: inputLabelEleList) {
                String forAttribute = labelEle.getAttribute("for");
                if(forAttribute.equalsIgnoreCase(id)){
                    labelText = labelEle.getText();
                }
            }
            WebElement parentElement = (WebElement) ((JavascriptExecutor) driver)
                    .executeScript("return arguments[0].parentNode;", inputEle);
            WebElement grandparentEle = (WebElement) ((JavascriptExecutor) driver)
                    .executeScript("return arguments[0].parentNode.parentNode;", inputEle);
            if ("span".equalsIgnoreCase(parentElement.getTagName()) && type.equalsIgnoreCase("search")) {
                WebElement secondSpanElement = grandparentEle.findElement(By.xpath(".//span[2]"));
                inputText = secondSpanElement.getText();
            }
            switch (Objects.requireNonNull(type)) {
                case "text" -> validateTextInput(inputText, labelText);
                case "number" -> validateMobileNumberInput(inputText, labelText);
                case "email" -> validateEmailInput(inputText, labelText);
                case "password" -> validatePasswordInput(inputText);
                case "radio" -> validateRadioInput(inputText);
                case "date" -> validateDateInput(inputText);
                case "textarea" -> validateTextareaInput(inputText, labelText);
                case "search" -> validateSearchInput(inputText, labelText);
                case "time" -> validateTimeInput(inputText);
                case "checkbox" -> validateCheckboxInput(inputEle, labelText);
                case "file" -> validateFileInput(inputEle, labelText);
                default -> System.out.println("Error: Unrecognized input type - " + type);
            };
        }
        excelReport.addErrorMessages(inputErrList, inputValidationData.get("ExcelReportName"));
    }
}
