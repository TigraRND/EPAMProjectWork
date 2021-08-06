package pages;

import org.openqa.selenium.WebDriver;

public class EventsPage extends CommonElements {
    private final static String URL = "https://events.epam.com/events";

    public EventsPage(WebDriver driver){
        super(driver);
    }

    public EventsPage goToPage(){
        driver.get(URL);
        logger.info("Переход на страницу Events");
        return this;
    }
}
