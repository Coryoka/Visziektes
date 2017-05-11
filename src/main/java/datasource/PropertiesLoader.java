package datasource;

import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {

    private Properties properties;

    public PropertiesLoader(String filename) throws IOException {
        properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream(filename));
    }

    public String getProperty(String property) {
        return properties.getProperty(property);
    }
}
