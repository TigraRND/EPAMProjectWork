package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class VideoPage extends CommonElements {
    private static final String URL = "https://events.epam.com/video?f%5B0%5D%5Bmedia%5D%5B%5D=Video";

    @FindBy(css = "div.evnt-talk-name span")
    private List<WebElement> talksNames;

    @FindBy(xpath = "//div[@class='evnt-talk-card']")
    private WebElement talkCardLinkFirefox;

    private String talkCardLinkSelectorChrome = "(//div[@class='evnt-talk-card']/a)[%d]";

    public VideoPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    public VideoPage goToPage() {
        driver.get(URL);
        waitForLoading();
        acceptCookie();
        logger.info("Переход на страницу Video");
        return this;
    }

    public List<WebElement> collectTalksNames() {
        logger.info("Сбор названий Talks");
        return talksNames;
    }

    public int getNumOfCards() {
        return talksNames.size();
    }

    public EventInfoPage clickTalkCardChrome(int index) {
        By finalLocator = By.xpath(String.format(talkCardLinkSelectorChrome, index));
        driver.findElement(finalLocator).click();
        waitForLoading();
        return new EventInfoPage(driver);
    }

    public EventInfoPage clickTalkCardFirefox() {
        talkCardLinkFirefox.click();
        waitForLoading();
        return new EventInfoPage(driver);
    }
}