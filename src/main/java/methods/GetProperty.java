package methods;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GetProperty {
    public static String getProperty(String arg) {

        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/environment/test.properties");
            property.load(fis);
            return property.getProperty(arg);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
