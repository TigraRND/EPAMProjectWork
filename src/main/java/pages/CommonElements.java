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
    private final By loader = By.xpath("//div[contains(@class,'evnt-loader')]");

    @FindBy(css = "a.nav-link[href='/calendar']")
    private WebElement calendarBtn;

    @FindBy(css = "a.nav-link[href='/events']")
    private WebElement eventsBtn;

    @FindBy(css = "a.nav-link[href^='/video']")
    private WebElement videoBtn;

    @FindBy(css = "div[href='#collapseMoreFilters']")
    private WebElement moreFiltersBtn;

    @FindBy(css = "input[placeholder='Search by Talk Name']")
    private WebElement searchField;

    private final String filterName = "//span[@class='evnt-filter-text'][text()='%s']";
    private final String filterValue = "//label[@data-value='%s']";

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

    public VideoPage clickVideoBtn(){
        videoBtn.click();
        waitForLoading();
        logger.info("Переход на страницу с видео");
        return new VideoPage(driver);
    }

    public CommonElements filtration(String name, String value){
        logger.info("Фильтрация по " + filterName + " со значением " + filterValue);
//        Построение локатора для списка фильтра
        String locatorName = String.format(filterName,name);
//        Клик для раскрытия списка фильтров
        driver.findElement(By.xpath(locatorName)).click();
//        Построение локатора для значения фильтра
        String locatorValue = String.format(filterValue,value);
//        Клик на элемент списка
        getElement(By.xpath(locatorValue)).click();
//        Ожидаем результатов фильтрации
        waitForLoading();
//        Клик для закрытия списка фильтров
        driver.findElement(By.xpath(locatorName)).click();
        return this;
    }

    public CommonElements clickMoreFilters(){
        moreFiltersBtn.click();
        logger.info("Нажимаем кнопку More filters");
        return this;
    }

    public CommonElements searching(String criteria){
        searchField.sendKeys(criteria);
//        Использован Thread.sleep так как в java скрипте
//        так же используется задержка после ввода прежде
//        чем начнется фильтрация
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Поиск по ключевому слову " + criteria);
        return this;
    }
}