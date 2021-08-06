package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class CommonElements {
    protected static WebDriver driver;
    final Logger logger = LogManager.getLogger(CommonElements.class);

    @FindBy(css = "a.nav-link[href='/calendar']")
    private WebElement calendarBtn;

    @FindBy(css = "a.nav-link[href='/events']")
    private WebElement eventsBtn;

    @FindBy(css = "a.nav-link[href^='/video']")
    private WebElement videoBtn;

    public CommonElements(WebDriver driver){
        CommonElements.driver = driver;
    }

    public EventsPage clickEventsLink(){
        eventsBtn.click();
        logger.info("Переход на страницу Events");
        return new EventsPage(driver);
    }
}
