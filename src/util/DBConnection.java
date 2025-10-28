package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        String url = ConfigManager.getProperty("db.url");
        String user = ConfigManager.getProperty("db.username");
        String pass = ConfigManager.getProperty("db.password");
        return DriverManager.getConnection(url, user, pass);
    }
}
