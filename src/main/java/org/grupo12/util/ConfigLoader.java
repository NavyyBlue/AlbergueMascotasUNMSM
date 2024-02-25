package org.grupo12.util;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final String CONFIG_FILE = "config.properties";
    private static final String CONFIG_FILE_DEV = "config_dev.properties";
    private static final String CONFIG_FILE_PROD = "config_prod.properties";
    private static final Properties properties = new Properties();

    static {
        loadProperties(CONFIG_FILE);

        String configFile = determineConfigFile();
        loadProperties(configFile);
    }

    private static void loadProperties(String configFile) {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(configFile)) {
            if (input != null) {
                properties.load(input);
            } else {
                System.out.println("Sorry, unable to find " + configFile);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    private static String determineConfigFile() {
        String env = System.getenv("APP_ENV");
        return "prod".equalsIgnoreCase(env) ? CONFIG_FILE_PROD : CONFIG_FILE_DEV;
    }

    public static String getWebSocketUrl(HttpServletRequest request) {
        ServletContext context = request.getServletContext();
        return "ws://" + request.getServerName() + ":" + request.getServerPort() + context.getContextPath();
    }
}
