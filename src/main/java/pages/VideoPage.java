package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class VideoPage extends CommonElements {
    private static final String URL = "https://events.epam.com/video?f%5B0%5D%5Bmedia%5D%5B%5D=Video";

    private final String talksNames = "div.evnt-talk-name span";


    public VideoPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver,this);
    }

    public VideoPage goToPage(){
        driver.get(URL);
        waitForLoading();
        logger.info("Переход на страницу Video");
        return this;
    }

    public List<WebElement> collectTalksNames(){
        logger.info("Сбор названий Talks");
        List<WebElement> listOfNames = driver.findElements(By.cssSelector(talksNames));
        return listOfNames;
    }
}