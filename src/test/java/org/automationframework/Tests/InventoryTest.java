package org.automationframework.Tests;

import org.automationframework.Data.JSONDataReader;
import org.automationframework.PageObjects.ChecklistPage;
import org.automationframework.PageObjects.InventoryPage;
import org.automationframework.PageObjects.UserManagementPage;
import org.automationframework.TestComponents.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class InventoryTest {
    public static WebDriver driver;
    BaseTest baseTest;
    JSONDataReader jsonDataReader;
    List<HashMap<String,String>> userCreationData;
    List<HashMap<String,String>> updateUserData;
    UserManagementPage userManagementPage;
    List<HashMap<String,String>> deletionData;
    InventoryPage inventoryPage;
    ChecklistPage checklistPage;
    @BeforeTest
    public void setUp() throws IOException, SQLException, ClassNotFoundException {
        baseTest = new BaseTest();
        driver = LoginTest.driver;
        userManagementPage = new UserManagementPage(driver);
        jsonDataReader = new JSONDataReader();
        inventoryPage = new InventoryPage(driver);
        checklistPage = new ChecklistPage(driver);
        userCreationData  = jsonDataReader.getJsonDataListOfObjects("UserCreationData");
        updateUserData = jsonDataReader.getJsonDataListOfObjects("UpdateUserData");
        deletionData = jsonDataReader.getJsonDataListOfObjects("DeleteUserData");
    }
    @Test(priority = 1)
    public void testCreateInventoryPart() throws InterruptedException, IOException {
        inventoryPage.createInventoryParts();
    }
    @Test(priority = 2)
    public void testCreateChecklist() throws IOException, InterruptedException {
        checklistPage.addChecklist();
    }
}
