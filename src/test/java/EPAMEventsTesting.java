import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.EventsPage;
import utils.Argumentator;
import utils.TestsData;
import utils.WDFactory;
import utils.WDType;

import java.util.concurrent.TimeUnit;

public class EPAMEventsTesting {
    protected static WebDriver driver;
    private final Logger logger = LogManager.getLogger(EPAMEventsTesting.class);
    private final TestsData cfg = ConfigFactory.create(TestsData.class);
    private final Argumentator arg = new Argumentator();

    @BeforeMethod
    public void startUp(){
        driver = WDFactory.getDriver(arg.getBrowser());
        driver.manage().window().maximize();
//        Настройка не явного ожидания с таймаутом 3 секунды
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

//    @AfterMethod
    public void shutDown() {
        if (driver != null)
            driver.quit();
        logger.info("WebDriver закрыт");
    }

    @Test
    public void someTest(){
        EventsPage eventsPage = new EventsPage(driver);
        eventsPage.goToPage();
    }
}
