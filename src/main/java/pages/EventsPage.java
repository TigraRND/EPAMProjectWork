package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.elements.EventSpeaker;
import pages.elements.PastEventCard;
import utils.Helpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventsPage extends CommonElements {
    private final static String URL = "https://events.epam.com/events";

    public List<PastEventCard> pastCards = new ArrayList<PastEventCard>();

    @FindBy(xpath = "//div[contains(@class,'event-card')]")
    private List<WebElement> plates;

    @FindBy(xpath = "//span[contains(text(),'Upcoming')]/following-sibling::span[contains(@class,'counter')]")
    private WebElement upcomingEventsCounter;

    @FindBy(xpath = "//span[contains(text(),'Past')]/following-sibling::span[contains(@class,'counter')]")
    private WebElement pastEventsCounter;

    @FindBy(xpath = "//span[contains(text(),'Upcoming')]/parent::a")
    private WebElement upcomingEventsBtn;

    @FindBy(xpath = "//span[contains(text(),'Past')]/parent::a")
    private WebElement pastEventsBtn;

//    Локаторы карточек мероприятий
    private final By eventNameLocator = By.cssSelector("div.evnt-event-name span");
    private final By eventLanguageLocator = By.cssSelector("p.language > span");
    private final By eventDatesLocator = By.cssSelector("div.evnt-event-dates-table span:first-child");
    private final By eventRegInfoLocator = By.cssSelector("div.evnt-event-dates-table span:nth-child(2)");
    private final String speakersLocator = "(//div[contains(@class,'event-card')])[%d]/descendant::div[@class='evnt-speaker']";

    public EventsPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    public EventsPage goToPage(){
        driver.get(URL);
        waitForLoading();
        logger.info("Переход на страницу Events");
        return this;
    }

    public int getRealCountOfPlates(){
        waitForLoading();
//        Получение количества найденых элементов
        int cardsCount = plates.size();
        logger.info("Количество карточек событий на странице: " + cardsCount);
        return cardsCount;
    }

    public int getCountOfUpcomingEvents(){
        int counterValue = Integer.parseInt(upcomingEventsCounter.getText());
        logger.info("Количество предстоящих событий по счетчику: " + counterValue);
        return counterValue;
    }

    public int getCountOfPastEvents(){
        int counterValue = Integer.parseInt(pastEventsCounter.getText());
        logger.info("Количество прошедших событий по счетчику: " + counterValue);
        return counterValue;
    }

    public EventsPage turnUpcomingEvents(){
        waitForLoading();
        upcomingEventsBtn.click();
        logger.info("Переключение на вкладку предстоящие события");
        return this;
    }

    public EventsPage turnPastEvents(){
        pastEventsBtn.click();
        waitForLoading();
        logger.info("Переключение на вкладку прошедшие события");
        return this;
    }

    public EventsPage collectPastCards(){
//        Явное ожидание для гарантии закрузки всех карточек
        waitForLoading();
        logger.info("Старт процедуры сбора информации с карточек прошедших событий");
//        Собираем атрибуты карточек в списки
        List<WebElement> names = driver.findElements(eventNameLocator);
        List<WebElement> langs = driver.findElements(eventLanguageLocator);
        List<WebElement> dates = driver.findElements(eventDatesLocator);
        List<WebElement> regs = driver.findElements(eventRegInfoLocator);

//        Создаем список объектов карточек
        for(int i = 0; i < names.size(); i++){
            PastEventCard card = new PastEventCard();
            card.setName(names.get(i).getText());
            card.setLang(langs.get(i).getText());
//            Переводим строку в дату
            Date date = Helpers.parseStringToDate(dates.get(i).getText());
            card.setDate(date);
            card.setRegInfo(regs.get(i).getText());
            pastCards.add(card);
        }

//        Добавляем спикеров
        for(PastEventCard card:pastCards){
//            Строим локатор для выбора спикеров конкретного события
            int num = pastCards.indexOf(card) + 1;
            String locator = String.format(speakersLocator,num);
//            Собираем спикеров в список Web элементов
            List<WebElement> webSpeakers = driver.findElements(By.xpath(locator));
//            Создаем объекты спикеров и добавляем их к объекту карточки события
            for (WebElement speakerElem:webSpeakers){
                String name = speakerElem.getAttribute("data-name");
                String job = speakerElem.getAttribute("data-job-title");
                EventSpeaker speakerObj = new EventSpeaker(name,job);
                card.addSpeaker(speakerObj);
            }
        }
        logger.info("Процедура сбора информации с карточек прошедших событий завершена");
//        for(PastEventCard card:pastCards)
//            logger.debug(card.toString());

        return this;
    }

    public EventsPage collectUpcomingCards(){
//        TODO реализовать код сбора информации с карточек предстоящих событий
        return this;
    }
}