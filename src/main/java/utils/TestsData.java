package utils;

import org.aeonbits.owner.Config;

@Config.Sources({"file:src/main/resources/TestsData.properties"})
public interface TestsData extends Config {
    String testData();
}
