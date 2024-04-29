package org.automationframework.TestDataCleanup;

import org.testng.annotations.AfterSuite;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DatabaseCleanup {
    private Connection connection;
    public void connectToDatabase() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://192.168.0.189:5432/bfal";
        String user = "postgres";
        String password = "max2max123456!";
        connection = DriverManager.getConnection(url, user, password);
    }
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
    public void cleanTestData() {
        String[] deleteQueries = {
                "DELETE FROM user_role WHERE role_name IN ('SuperUser Role', 'Manager Role', 'Technician Role');",
        };
        try {
            for (String deleteQuery : deleteQueries) {
                try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @AfterSuite
    public void tearDownAfterAllTests() throws Exception {
        connectToDatabase();
        cleanTestData();
        Thread.sleep(2000);
        closeConnection();
//        driver.close();
    }
}

