package org.automationframework.Libraries;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInterfaceValidator {
    List<String> displayLayoutErrLost = new ArrayList<>();
    Map<String, Map<String, String>> resolutions = new HashMap<>();
    ExcelReport excelReport = new ExcelReport();
    public void testCustomScreenResponsiveness(WebDriver driver, HashMap<String, HashMap<String, String>> loginDisplayLayoutData){
        int screenHeight = Integer.parseInt(loginDisplayLayoutData.get("ScreenInfo").get("Height"));
        int screenWidth = Integer.parseInt(loginDisplayLayoutData.get("ScreenInfo").get("Width"));
        driver.manage().window().setSize(new Dimension(screenWidth, screenHeight));
        try{
            for (String xpath: loginDisplayLayoutData.get("ElementXpath").values()) {
                WebElement ele = driver.findElement(By.xpath(xpath));
                if (!ele.isDisplayed()) {
                    displayLayoutErrLost.add("User Interface Error: Element with xpath- "+xpath+" is not displayed for screen size " + screenWidth + "x" + screenHeight);
                }
            }
        }catch (Exception e){
            displayLayoutErrLost.add("User Interface Error: Menu is not displayed for screen size " + screenWidth + "x" + screenHeight);
        }
        excelReport.addErrorMessages(displayLayoutErrLost, loginDisplayLayoutData.get("ScreenInfo").get("ReportName"));
        displayLayoutErrLost.clear();
    }

    public void testScreenResponsiveness(WebDriver driver, HashMap<String, HashMap<String, String>> loginDisplayLayoutData) throws InterruptedException {
        getResolutions();
        for (Map.Entry<String, Map<String, String>> entry : resolutions.entrySet()) {
            Map<String, String> deviceResolutions = entry.getValue();
            int screenHeight = Integer.parseInt(deviceResolutions.get("Height"));
            int screenWidth = Integer.parseInt(deviceResolutions.get("Width"));
            driver.manage().window().setSize(new Dimension(screenWidth, screenHeight));
            Thread.sleep(3000);
            try{
                for (String xpath: loginDisplayLayoutData.get("ElementXpath").values()) {
                    WebElement ele = driver.findElement(By.xpath(xpath));
                    if (!ele.isDisplayed()) {
                        displayLayoutErrLost.add("User Interface Error: Element with xpath- "+xpath+" is not displayed for screen size " + screenWidth + "x" + screenHeight);
                    }
                }
            }catch (Exception e){
                displayLayoutErrLost.add("User Interface Error: Menu is not displayed for screen size " + screenWidth + "x" + screenHeight);
            }
        }
        excelReport.addErrorMessages(displayLayoutErrLost, loginDisplayLayoutData.get("ScreenInfo").get("ReportName"));
        displayLayoutErrLost.clear();
    }
    private void getResolutions(){
        Map<String, String> extraSmallResolutions = new HashMap<>();

        // Tiny (XS)
        extraSmallResolutions.put("Height", "240");
        extraSmallResolutions.put("Width", "320");
        resolutions.put("Extra Small (XS)", extraSmallResolutions);

        // Small (S)
        Map<String, String> smallResolutions = new HashMap<>();
        smallResolutions.put("Height", "480");
        smallResolutions.put("Width", "720");
        resolutions.put("Small (S)", smallResolutions);

        // Medium (M)
        Map<String, String> mediumResolutions = new HashMap<>();
        mediumResolutions.put("Height", "768");
        mediumResolutions.put("Width", "1366");
        resolutions.put("Medium (M)", mediumResolutions);

        // Large (L)
        Map<String, String> largeResolutions = new HashMap<>();
        largeResolutions.put("Height", "1080");
        largeResolutions.put("Width", "1920");
        resolutions.put("Large (L)", largeResolutions);

        // Extra Large (XL)
        Map<String, String> extraLargeResolutions = new HashMap<>();
        extraLargeResolutions.put("Height", "1440");
        extraLargeResolutions.put("Width", "2560");
        resolutions.put("Extra Large (XL)", extraLargeResolutions);
    }
}

