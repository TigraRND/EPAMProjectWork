package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends CommonElements{
    private final static String URL = "https://events.epam.com";

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    public MainPage goToPage(){
        driver.get(URL);
        waitForLoading();
        logger.info("Переход на главную страницу");
        return this;
    }
}
