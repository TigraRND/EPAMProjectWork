package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

public class WDFactory {

    public static WebDriver getDriver(WDType type, MutableCapabilities options){
        Logger logger = LogManager.getLogger(WDFactory.class);
        switch (type){
            case CHROME:
                WebDriverManager.chromedriver().setup();
                logger.info("WebDriver " + type +" поднят");
                return new ChromeDriver((ChromeOptions) options);
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                logger.info("WebDriver " + type +" поднят");
                return new FirefoxDriver((FirefoxOptions)options);
            case OPERA:
                WebDriverManager.operadriver().setup();
                logger.info("WebDriver " + type +" поднят");
                return new OperaDriver((OperaOptions) options);
            case IE:
                WebDriverManager.iedriver();
                logger.info("WebDriver " + type +" поднят");
                return new InternetExplorerDriver((InternetExplorerOptions) options);
        }
        logger.error("Ошибка при создании WebDriver. Не указан тип.");
        return null;
    }

    public static WebDriver getDriver(WDType type, String arguments){
        Logger logger = LogManager.getLogger(WDFactory.class);
        arguments = arguments.toLowerCase();
        switch (type){
            case CHROME:
                WebDriverManager.chromedriver().setup();
                logger.info("WebDriver " + type +" поднят");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments(arguments);
                return new ChromeDriver(chromeOptions);
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                logger.info("WebDriver " + type +" поднят");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments(arguments);
                return new FirefoxDriver(firefoxOptions);
            case OPERA:
                WebDriverManager.operadriver().setup();
                logger.info("WebDriver " + type +" поднят");
                OperaOptions operaOptions = new OperaOptions();
                operaOptions.addArguments(arguments);
                return new OperaDriver(operaOptions);
            case IE:
                WebDriverManager.iedriver().setup();
                logger.info("WebDriver " + type +" поднят");
                InternetExplorerOptions ieOptions = new InternetExplorerOptions();
                ieOptions.addCommandSwitches(arguments);
                return new InternetExplorerDriver(ieOptions);
        }
        logger.error("Ошибка при создании WebDriver. Не указан тип.");
        return null;
    }

    public static WebDriver getDriver(WDType type){
        Logger logger = LogManager.getLogger(WDFactory.class);
        switch (type){
            case CHROME:
                WebDriverManager.chromedriver().setup();
                logger.info("WebDriver " + type +" поднят");
                return new ChromeDriver();
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                logger.info("WebDriver " + type +" поднят");
                return new FirefoxDriver();
            case OPERA:
                WebDriverManager.operadriver().setup();
                logger.info("WebDriver " + type +" поднят");
                return new OperaDriver();
            case IE:
                WebDriverManager.iedriver();
                logger.info("WebDriver " + type +" поднят");
                return new InternetExplorerDriver();
        }
        logger.error("Ошибка при создании WebDriver. Не указан тип.");
        return null;
    }

    public static WebDriver getDriver(String type){
        if(type == null)
            return getDriver(WDType.CHROME);
        try{
            return getDriver(WDType.valueOf(type));
        }catch(IllegalArgumentException ex){
            return getDriver(WDType.CHROME);
        }
    }
}