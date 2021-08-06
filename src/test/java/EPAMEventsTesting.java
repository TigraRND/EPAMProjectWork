import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import utils.TestsData;

public class EPAMEventsTesting {
    protected static WebDriver driver;
    private final Logger logger = LogManager.getLogger(EPAMEventsTesting.class);
    private final TestsData cfg = ConfigFactory.create(TestsData.class);

    @Test
    public void someTest(){
        logger.info(cfg.testData());
    }
}
