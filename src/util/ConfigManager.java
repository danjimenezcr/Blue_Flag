package util;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("main/resources/db.properties")) {
            if (input == null) throw new RuntimeException("Properties file not found!");
            properties.load(input);
        } catch  (Exception e) {
            System.out.println("Error loading properties file!");
            e.printStackTrace();
        }
    }
    public static String getProperty (String key) {
        return properties.getProperty(key);
    }
}
