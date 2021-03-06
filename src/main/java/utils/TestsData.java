package utils;

import org.aeonbits.owner.Config;

@Config.Sources({"file:src/test/resources/TestsData.properties"})
public interface TestsData extends Config {
    String location();
    String category();
    String language();
}