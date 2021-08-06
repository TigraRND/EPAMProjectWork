import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.EventsPage;
import utils.Argumentator;
import utils.TestsData;
import utils.WDFactory;

public class EPAMEventsTesting {
    protected static WebDriver driver;
    private final Logger logger = LogManager.getLogger(EPAMEventsTesting.class);
    private final TestsData cfg = ConfigFactory.create(TestsData.class);
    private final Argumentator arg = new Argumentator();

    @BeforeMethod
    public void startUp(){
        driver = WDFactory.getDriver(arg.getBrowser());
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void shutDown() {
        if (driver != null)
            driver.quit();
        logger.info("WebDriver закрыт");
    }

    @Test(testName = "Проверка счетчика предстоящих событий")
    public void checkUpcomingEventsCounter() {
        EventsPage eventsPage = new EventsPage(driver);
        eventsPage.goToPage();
        Assert.assertEquals(eventsPage.getRealCountOfPlates(),eventsPage.getCountOfUpcomingEvents());
    }
}
