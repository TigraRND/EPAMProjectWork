import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.EventInfoPage;
import pages.EventsPage;
import pages.VideoPage;
import pages.elements.PastEventCard;
import pages.elements.UpcomingEventCard;
import utils.Argumentator;
import utils.Helpers;
import utils.TestsData;
import utils.WDFactory;

import java.util.Date;
import java.util.List;

public class EPAMEventsTesting {
    protected static WebDriver driver;
    private final Logger logger = LogManager.getLogger(EPAMEventsTesting.class);
    private final TestsData cfg = ConfigFactory.create(TestsData.class);
    private final Argumentator arg = new Argumentator();
    private final SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    @Step("Запуск и настройка WebDriver")
    public void settUp() {
        driver = WDFactory.getDriver(arg.getBrowser());
        driver.manage().window().maximize();
    }

    @AfterMethod
    @Step("Закрытие WebDriver")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            String stars = "";
            for (int i = 0; i < 141; i++)
                stars += "*";
            logger.info("WebDriver закрыт\n" + stars);
        }
    }

    @Test(testName = "Просмотр предстоящих мероприятий", enabled = true)
    @Feature("Счетчик событий")
    @Description(value = "Тест проверяет что значение счетчика предстоящих событий соответствует реальному " +
            "количеству отображаемых карточек на экране")
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

    @Test(testName = "Просмотр карточек мероприятий", enabled = true)
    @Feature("Атрибуты карточки")
    @Description(value = "Тест проверяет что в прошедших событиях отображаются карточки у которых " +
            "не пустые атрибуты: язык, название, дата, регистрация и спикеры")
    public void checkPastCards() {
        EventsPage eventsPage = new EventsPage(driver);
        eventsPage
                .goToPage()
                .turnPastEvents()
                .collectPastCards();
        for (PastEventCard card : eventsPage.pastCards) {
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

    @Test(testName = "Валидация дат предстоящих мероприятий", enabled = true)
    @Feature("Отображение карточек предстоящих событий")
    @Description("Тест проверяет что у предстоящих событий дата проведения больше чем у текущей " +
            "даты минус один день")
    public void checkUpcomingEventsData() {
        EventsPage eventsPage = new EventsPage(driver);
        eventsPage
                .goToPage()
                .turnUpcomingEvents()
                .collectUpcomingCards();
        Date yesterday = Helpers.parseStringToDate("yesterday");
        logger.info("Сравнение дат предстоящих событий с текуще датой: " + yesterday);
        for (UpcomingEventCard card : eventsPage.upcomingEventCards) {
            logger.info(String.format("Сравнение даты %s события %s с текущей", card.getDate(), card.getName()));
            boolean check = false;
            if (card.getDate().after(yesterday))
                check = true;
            softAssert.assertTrue(check, "Дата события не позже текущей");
        }
        softAssert.assertAll();
    }

    @Test(testName = "Просмотр прошедших мероприятий в Канаде", enabled = true)
    @Feature("Фильтрация")
    @Description("Тест фильтрует прошедшие события по локации Канада, затем проверяет значение счетчика событий " +
            "и что даты событий меньше текущей")
    public void checkEventInCanada() {
        EventsPage eventsPage = new EventsPage(driver);
        eventsPage
                .goToPage()
                .turnPastEvents()
                .filtration(cfg.location(), "Canada");
        eventsPage.collectPastCards();

//        Проверка счетчика
        int realNumOfCards = eventsPage.pastCards.size();
        logger.info("Количество карточек прошедших событий на странице: " + realNumOfCards);
        int counterValue = eventsPage.getCountOfPastEvents();
        logger.info("Сравнение значений");
        softAssert.assertEquals(realNumOfCards, counterValue);

//        Проверка даты
        Date today = Helpers.parseStringToDate("today");
        logger.info("Сравнение дат предстоящих событий с текуще датой: " + today);
        for (PastEventCard card : eventsPage.pastCards) {
            logger.info(String.format("Сравнение даты %s события %s с текущей", card.getDate(), card.getName()));
            boolean check = false;
            if (card.getDate().before(today))
                check = true;
            softAssert.assertTrue(check, "Дата события не позже текущей");
        }
        softAssert.assertAll();
    }

    @Test(testName = "Фильтрация докладов по категориям", enabled = true)
    @Feature("Фильтрация")
    @Description("Тест использует несколько фильтров на странице Talks Library после чего выбирает произвольную " +
            "карточку среди отфильтрованных, заходит в нее и проверяет что ее атрибуты соответствуют критериям " +
            "фильтрации")
    public void checkFilteringOfTalks() {
        String targetCategory = "Testing";
        String targetLocation = "Belarus";
        String targetLanguage = "ENGLISH";
        VideoPage videoPage = new VideoPage(driver);
        videoPage
                .goToPage()
                .clickMoreFilters()
                .filtration(cfg.category(), targetCategory)
                .filtration(cfg.location(), targetLocation)
                .filtration(cfg.language(), targetLanguage);

        int randomCardIndex = Helpers.randomNumInRange(1, videoPage.getNumOfCards());
        EventInfoPage eventInfoPage = videoPage.clickTalkCardChrome(randomCardIndex);
//        EventInfoPage eventInfoPage = videoPage.clickTalkCardFirefox();
        logger.info("Переход к карточке №" + randomCardIndex);

        logger.info("Проверка наличия параметра запроса " + targetLocation);
        logger.debug("Получено значение: " + eventInfoPage.getLocationInfo());
        boolean checkLocation = Helpers.checkSubstringInString(eventInfoPage.getLocationInfo(), targetLocation);
        softAssert.assertTrue(checkLocation);

        logger.info("Проверка наличия параметра запроса " + targetLanguage);
        logger.debug("Получено значение: " + eventInfoPage.getLanguageInfo());
        boolean checkLanguage = Helpers.checkSubstringInString(eventInfoPage.getLanguageInfo(), targetLanguage);
        softAssert.assertTrue(checkLanguage);

        logger.info("Проверка наличия параметра запроса " + targetCategory);
        boolean checkCategory = false;
        for (WebElement tag : eventInfoPage.getCategories()) {
            logger.debug("Получено значение: " + tag.getText());
            if (Helpers.checkSubstringInString(tag.getText(), targetCategory)) {
                checkCategory = true;
                break;
            }
        }
        softAssert.assertTrue(checkCategory);

        softAssert.assertAll();
    }

    @Test(testName = "Поиск докладов по ключевому слову", enabled = true)
    @Feature("Поиск по ключевому слову")
    @Description("Тест использует поиск по ключевому слову QA на странице Talks Library, затем собирает названия " +
            "найденных карточек и проверяет что в названиях присутствует ключевое слово поиска")
    public void checkSearching() {
        String searchingCriteria = "QA";
        VideoPage videoPage = new VideoPage(driver);
        videoPage
                .goToPage()
                .searching(searchingCriteria);

        List<WebElement> allNames = videoPage.collectTalksNames();
        logger.info("Собранные имена со страницы:");
        for (WebElement talk : allNames) {
            String actual = talk.getText();
            logger.info(actual);
            boolean check = Helpers.checkSubstringInString(actual, searchingCriteria);
            softAssert.assertTrue(check);
        }
        logger.info("Сравнение полученных значений");
        softAssert.assertAll();
    }
}