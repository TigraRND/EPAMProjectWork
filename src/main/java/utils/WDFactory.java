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

//    Для логирования нужно передать код операции и тип WebDriver
//    код успешной операции 1, когда WebDriver создан
//    код не успешной операции -1, когда WebDriver не создан
    public static WebDriver getDriver(WDType type, MutableCapabilities options) {
        switch (type) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                logging(1,type);
                return new ChromeDriver((ChromeOptions) options);
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                logging(1,type);
                return new FirefoxDriver((FirefoxOptions) options);
            case OPERA:
                WebDriverManager.operadriver().setup();
                logging(1,type);
                return new OperaDriver((OperaOptions) options);
            case IE:
                WebDriverManager.iedriver();
                logging(1,type);
                return new InternetExplorerDriver((InternetExplorerOptions) options);
        }
        logging(-1,type);
        return null;
    }

    public static WebDriver getDriver(WDType type, String arguments) {
        arguments = arguments.toLowerCase();
        switch (type) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                logging(1,type);
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments(arguments);
                return new ChromeDriver(chromeOptions);
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                logging(1,type);
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments(arguments);
                return new FirefoxDriver(firefoxOptions);
            case OPERA:
                WebDriverManager.operadriver().setup();
                logging(1,type);
                OperaOptions operaOptions = new OperaOptions();
                operaOptions.addArguments(arguments);
                return new OperaDriver(operaOptions);
            case IE:
                WebDriverManager.iedriver().setup();
                logging(1,type);
                InternetExplorerOptions ieOptions = new InternetExplorerOptions();
                ieOptions.addCommandSwitches(arguments);
                return new InternetExplorerDriver(ieOptions);
        }
        logging(-1,type);
        return null;
    }

    public static WebDriver getDriver(WDType type) {
        switch (type) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                logging(1,type);
                return new ChromeDriver();
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                logging(1,type);
                return new FirefoxDriver();
            case OPERA:
                WebDriverManager.operadriver().setup();
                logging(1,type);
                return new OperaDriver();
            case IE:
                WebDriverManager.iedriver();
                logging(1,type);
                return new InternetExplorerDriver();
        }
        logging(-1,type);
        return null;
    }

    public static WebDriver getDriver(String type) {
        if (type == null)
            return getDriver(WDType.CHROME);
        try {
            return getDriver(WDType.valueOf(type));
        } catch (IllegalArgumentException ex) {
            return getDriver(WDType.CHROME);
        }
    }

    private static void logging(int code, WDType type){
        Logger logger = LogManager.getLogger(WDFactory.class);
        switch (code){
            case 1:
                logger.info("WebDriver {} поднят", type);
            case -1:
                logger.error("Ошибка при создании WebDriver. Не указан тип.");
        }
    }
}