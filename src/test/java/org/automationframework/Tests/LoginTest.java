package org.automationframework.Tests;

import org.automationframework.PageObjects.LandingPage;
import org.automationframework.TestComponents.BaseTest;
import org.automationframework.TestComponents.CustomListeners;
import org.automationframework.Data.JSONDataReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

@Listeners(CustomListeners.class)
public class LoginTest {
    public static WebDriver driver;
    BaseTest baseTest;
    JSONDataReader jsonDataReader;
    List<HashMap<String,String>> loginData;
    List<HashMap<String,String>> urlData;
    HashMap<String, HashMap<String, String>> loginDisplayLayoutData;
//    HashMap<String, HashMap<String, String>> dashboardDisplayLayoutData;
    @BeforeTest
    public void setUp() throws IOException, SQLException, ClassNotFoundException {
        baseTest = new BaseTest();
        driver = baseTest.initializeDriver();
        jsonDataReader = new JSONDataReader();
        loginData = jsonDataReader.getJsonDataListOfObjects("LoginData//LoginData");
        urlData = jsonDataReader.getJsonDataListOfObjects("LoginData//URLData");
        loginDisplayLayoutData = jsonDataReader.getJsonDataObjectsOfObjects("LoginDisplayLayoutData");
//        dashboardDisplayLayoutData = jsonDataReader.getJsonDataObjectsOfObjects("DashboardDisplayLayoutData");
    }

    @Test(priority = 1)
    public void testLogin() throws InterruptedException, IOException, SQLException, ClassNotFoundException {
        String userName = loginData.get(0).get("username");
        String password = loginData.get(0).get("password");
        String loginUrl = urlData.get(0).get("loginUrl");
        String dashboardUrl = urlData.get(0).get("dashboardUrl");
        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo(loginUrl);
        landingPage.loginApplication(userName, password);
        Thread.sleep(1000);
        landingPage.loginMainApplication(userName, password, loginDisplayLayoutData);
        WebDriverWait wait = new WebDriverWait(baseTest.driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe(dashboardUrl));
        String actualUrlAfterLogin = baseTest.driver.getCurrentUrl();
        Assert.assertEquals(actualUrlAfterLogin, dashboardUrl);
//        landingPage.dashBoardPage(dashboardDisplayLayoutData);

    }
}
