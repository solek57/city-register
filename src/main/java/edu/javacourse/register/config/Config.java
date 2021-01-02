package edu.javacourse.register.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    public static final String DB_URL = "db.url";
    public static final String DB_LOGIN = "db.login";
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_LIMIT = "db.limit";
    public static final String CR_URL = "cr.url";

    private static Properties properties = new Properties();

    public static synchronized String getProperty(String propertyName) {
        if (properties.isEmpty()) {
            try (InputStream inputStream = Config.class.getClassLoader()
                    .getResourceAsStream("config.properties")) {
                properties.load(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return properties.getProperty(propertyName);
    }
}
