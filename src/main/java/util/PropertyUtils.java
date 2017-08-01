package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {
    private static final String PROPERTIES_FILE = "config.properties";

    public static String readProperty(String propertyName) {
        Properties prop = new Properties();
        String propertyValue;
        try (InputStream input = new FileInputStream(PROPERTIES_FILE)) {
            prop.load(input);
            propertyValue = prop.getProperty(propertyName);
        } catch (IOException e) {
            return null;
        }
        return propertyValue;
    }
}
