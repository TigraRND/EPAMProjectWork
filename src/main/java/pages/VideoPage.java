package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.CommonElements;

public class VideoPage extends CommonElements {
    private static final String URL = "https://events.epam.com/video?f%5B0%5D%5Bmedia%5D%5B%5D=Video";

    @FindBy(css = "div[href='#collapseMoreFilters']")
    private WebElement moreFiltersBtn;

    @FindBy(xpath = "//span[@class='evnt-filter-text'][text()='Location']")
    private WebElement filterLocation;

    private final String locationValue = "//label[@data-value='%s']";
    private final String talkNamesLocator = "div.evnt-talk-name span";


    public VideoPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver,this);
    }

    public VideoPage clickMoreFilters(){
        moreFiltersBtn.click();
        return this;
    }
}
