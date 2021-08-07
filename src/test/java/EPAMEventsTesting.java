import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.EventsPage;
import utils.Argumentator;
import utils.Helpers;
import utils.TestsData;
import utils.WDFactory;

import java.util.Date;

public class EPAMEventsTesting {
    protected static WebDriver driver;
    private final Logger logger = LogManager.getLogger(EPAMEventsTesting.class);
    private final TestsData cfg = ConfigFactory.create(TestsData.class);
    private final Argumentator arg = new Argumentator();
    private final SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void startUp() {
        driver = WDFactory.getDriver(arg.getBrowser());
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void shutDown() {
        if (driver != null) {
            driver.quit();
            logger.info("WebDriver закрыт");
        }
    }

    @Test(testName = "Проверка счетчика предстоящих событий")
    public void checkUpcomingEventsCounter() {
        EventsPage eventsPage = new EventsPage(driver);
        eventsPage.goToPage();
        Assert.assertEquals(eventsPage.getRealCountOfPlates(), eventsPage.getCountOfUpcomingEvents());
    }



//    @Test(testName = "Проверка дат предстоящих событий")
    public void checkUpcomingEventsData() {
//        EventsPage eventsPage = new EventsPage(driver);
//        eventsPage
//                .goToPage()
//                .turnUpcomingEvents();
//        Parser parser = new Parser();
//        List<DateGroup> groups = parser.parse("1 Feb - 25 Aug 2025");
//        for(DateGroup group:groups) {
//            List <Date> dates = group.getDates();
//            logger.debug(dates.get(dates.size() - 1));
//        }
        Date today = Helpers.stringToDateParser("today");
        Date eventDate = Helpers.stringToDateParser("1 Feb - 25 Aug 2025");
        boolean check = false;
        if (eventDate.after(today))
            check = true;
        Assert.assertTrue(check, "Дата события не позже текущей");
    }
}
