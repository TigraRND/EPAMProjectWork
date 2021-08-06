package utils;

import java.util.Properties;

public class Argumentator {
    private final Properties properties;

    public Argumentator(){
        properties = System.getProperties();
    }

    public String getBrowser() {
        String browser = properties.getProperty("browser");
        if (browser != null)
            browser = browser.toUpperCase();
        return browser;
    }
}