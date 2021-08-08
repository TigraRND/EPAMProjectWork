package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class CommonElements {
    protected static WebDriver driver;
    final Logger logger = LogManager.getLogger(CommonElements.class);
    private final By loader = By.xpath("//div[contains(@class,'loader')]");

    @FindBy(css = "a.nav-link[href='/calendar']")
    private WebElement calendarBtn;

    @FindBy(css = "a.nav-link[href='/events']")
    private WebElement eventsBtn;

    @FindBy(css = "a.nav-link[href^='/video']")
    private WebElement videoBtn;

    public CommonElements(WebDriver driver){
        CommonElements.driver = driver;
        PageFactory.initElements(this.driver,this);
    }

//    Поиск элемента через явное ожидание
    public WebElement getElement(By locator){
        return new WebDriverWait(driver,3)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

//    Ожидание загрузки страницы
    public CommonElements waitForLoading(){
        new WebDriverWait(driver,10)
                .until(ExpectedConditions.invisibilityOfElementLocated(loader));
        return this;
    }

    public EventsPage clickEventsBtn(){
        eventsBtn.click();
        waitForLoading();
        logger.info("Переход на страницу Events");
        return new EventsPage(driver);
    }
}