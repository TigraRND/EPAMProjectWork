package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EventsPage extends CommonElements {
    private final static String URL = "https://events.epam.com/events";

    private final By eventsPlatesLocator = By.xpath("//div[contains(@class,'event-card')]");
    private final By upcomingEventsCounterLocator = By.xpath("//span[contains(text(),'Upcoming')]/following-sibling::span[contains(@class,'counter')]");
    private final By pastEventsCounterLocator = By.xpath("//span[contains(text(),'Past')]/following-sibling::span[contains(@class,'counter')]");
    private final By upcomingEventsBtnLocator = By.xpath("//span[contains(text(),'Upcoming')]/parent::a");
    private final By pastEventsBtnLocator = By.xpath("//span[contains(text(),'Past')]/parent::a");

    public EventsPage(WebDriver driver){
        super(driver);
    }

    public EventsPage goToPage(){
        driver.get(URL);
        logger.info("Переход на страницу Events");
        return this;
    }

    public int getRealCountOfPlates(){
//        Явное ожидание появления элементов
        getElement(eventsPlatesLocator);
//        Получение количества найденых элементов
        int cardsCount = driver.findElements(eventsPlatesLocator).size();
        logger.info("Количество карточек событий на странице: " + cardsCount);
        return cardsCount;
    }

    public int getCountOfUpcomingEvents(){
        int counterValue = Integer.parseInt(getElement(upcomingEventsCounterLocator).getText());
        logger.info("Количество предстоящих событий по счетчику: " + counterValue);
        return counterValue;
    }

    public int getCountOfPastEvents(){
        int counterValue = Integer.parseInt(getElement(pastEventsCounterLocator).getText());
        logger.info("Количество прошедших событий по счетчику: " + counterValue);
        return counterValue;
    }

    public EventsPage turnUpcomingEvents(){
        getElement(upcomingEventsBtnLocator).click();
        logger.info("Переключение на вкладку предстоящие события");
        return this;
    }

    public EventsPage turnPastEvents(){
        getElement(pastEventsBtnLocator).click();
        logger.info("Переключение на вкладку прошедшие события");
        return this;
    }
}
