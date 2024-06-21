package initialization;

import org.junit.jupiter.api.BeforeAll;

import static methods.GetProperty.getProperty;


public class Base {
    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", getProperty("driver_path"));
    }

}
