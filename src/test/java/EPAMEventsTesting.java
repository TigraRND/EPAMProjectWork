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
import pages.elements.PastEventCard;
import pages.elements.UpcomingEventCard;
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
            String stars = "";
            for(int i = 0; i < 141; i++)
                stars += "*";
            logger.info("WebDriver закрыт\n" + stars);
        }
    }

    @Test(testName = "Проверка счетчика предстоящих событий", enabled = true)
    public void checkUpcomingEventsCounter() {
        EventsPage eventsPage = new EventsPage(driver);
        eventsPage
                .goToPage()
                .collectUpcomingCards();
        int realNumOfCards = eventsPage.upcomingEventCards.size();
        logger.info("Количество карточек предстоящих событий на странице: " + realNumOfCards);
        int counterValue = eventsPage.getCountOfUpcomingEvents();
        logger.info("Сравнение значений");
        Assert.assertEquals(realNumOfCards, counterValue);
    }

    @Test(testName = "Проверка карточек прошедших событий", enabled = true)
    public void checkPastCards(){
        EventsPage eventsPage = new EventsPage(driver);
        eventsPage
                .goToPage()
                .turnPastEvents()
                .collectPastCards();
        for(PastEventCard card:eventsPage.pastCards){
            int cardNum = eventsPage.pastCards.indexOf(card) + 1;
            logger.info("Проверка что значение карточки " + cardNum + " не равно null");
            softAssert.assertNotNull(card.getName());
            softAssert.assertNotNull(card.getLang());
            softAssert.assertNotNull(card.getDate());
            softAssert.assertNotNull(card.getRegInfo());
            softAssert.assertNotNull(card.getSpeakers());
        }
        softAssert.assertAll();
    }

    @Test(testName = "Проверка дат предстоящих событий", enabled = true)
    public void checkUpcomingEventsData() {
        EventsPage eventsPage = new EventsPage(driver);
        eventsPage
                .goToPage()
                .turnUpcomingEvents()
                .collectUpcomingCards();
        Date today = Helpers.parseStringToDate("today");
        logger.info("Сравнение дат предстоящих событий с текуще датой: " + today);
        for(UpcomingEventCard card:eventsPage.upcomingEventCards){
            logger.info(String.format("Сравнение даты %s события №%s с текущей",card.getDate(), eventsPage.upcomingEventCards.indexOf(card)));
            boolean check = false;
            if (card.getDate().after(today))
                check = true;
            softAssert.assertTrue(check, "Дата события не позже текущей");
        }
        softAssert.assertAll();
    }
}
