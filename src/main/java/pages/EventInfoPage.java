package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class EventInfoPage extends CommonElements {

    @FindBy(xpath = "//div[contains(@class,'evnt-talk-details location')]/span")
    private WebElement locationInfo;

    @FindBy(xpath = "//div[contains(@class,'evnt-talk-details language')]/span")
    private WebElement languageInfo;

    @FindBy(xpath = "//div[contains(@class,'evnt-topics')]/descendant::label")
    private List<WebElement> categories;

    public EventInfoPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    public String getLocationInfo() {
        return locationInfo.getText();
    }

    public String getLanguageInfo() {
        return languageInfo.getText();
    }

    public List<WebElement> getCategories() {
        return categories;
    }
}
