package org.automationframework.Tests;

import org.automationframework.Data.JSONDataReader;
import org.automationframework.PageObjects.UserManagementPage;
import org.automationframework.TestComponents.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class UserManagementTest {
    public static WebDriver driver;
    BaseTest baseTest;
    JSONDataReader jsonDataReader;
    List<HashMap<String,String>> userCreationData;
    List<HashMap<String,String>> updateUserData;
    UserManagementPage userManagementPage;
    List<HashMap<String,String>> deletionData;
    @BeforeTest
    public void setUp() throws IOException, SQLException, ClassNotFoundException {
        baseTest = new BaseTest();
        driver = LoginTest.driver;
        userManagementPage = new UserManagementPage(driver);
        jsonDataReader = new JSONDataReader();
        userCreationData  = jsonDataReader.getJsonDataListOfObjects("UserCreationData");
        updateUserData = jsonDataReader.getJsonDataListOfObjects("UpdateUserData");
        deletionData = jsonDataReader.getJsonDataListOfObjects("DeleteTableData");
    }
    @Test(priority = 1)
    public void testCreateUser() throws Exception {
        String userName = userCreationData.get(0).get("UserName");
        String role = userCreationData.get(0).get("Role");
        String email = userCreationData.get(0).get("Email");
        String mobileNo = userCreationData.get(0).get("MobileNo");
        String password = userCreationData.get(0).get("Password");
        String site = userCreationData.get(0).get("Site");
        String status = userCreationData.get(0).get("Status");
        HashMap<String, String> inputValidationData = userCreationData.get(1);
        userManagementPage.createUser(userName,role, email, mobileNo, password, site, status, inputValidationData);
    }
    @Test(priority = 2)
    public void testUpdateUser() throws Exception {
        HashMap<String, String> updatedData = updateUserData.get(0);
        HashMap<String, String> inputValidationData = updateUserData.get(1);
        userManagementPage.updateUser(updatedData, inputValidationData);
        userManagementPage.validateUserTableData(inputValidationData);
    }
    @Test(priority = 3)
    public void testCreateEntity() throws InterruptedException {
        userManagementPage.createEntity();
    }
    @Test(priority = 4)
    public void  testDeleteTableData() throws InterruptedException {
        HashMap<String, String> deleteTableData = deletionData.get(0);
        HashMap<String, String> inputValidationData = deletionData.get(1);
        userManagementPage.deleteRowFromTable(deleteTableData, inputValidationData);
    }
}
