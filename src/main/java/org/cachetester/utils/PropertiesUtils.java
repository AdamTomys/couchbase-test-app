package org.cachetester.utils;

import org.cachetester.exceptions.CacheTestingException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

    private static final Properties properties = new Properties();

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void initApplicationProperties() {
        InputStream propertiesStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(propertiesStream);
        } catch (IOException e) {
            throw new CacheTestingException(e.getMessage(), e.getCause(), false, true);
        }
    }
}
