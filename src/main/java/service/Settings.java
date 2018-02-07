package service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Class singleton gets properties from file.
 */
public class Settings {

    private final static Settings INSTANCE = new Settings();

    private final Properties properties = new Properties();

    private Settings() {
        try {
            // get properties
            FileInputStream input = new FileInputStream(this.getClass().getClassLoader().getResource("data.properties").getFile());
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Settings getInstance() {
        return INSTANCE;
    }

    /**
     * get property "jdbc.url"
     * @return string
     */
    public String getUrl() {
        return properties.getProperty("jdbc.url");
    }

    /**
     * get property "jdbc.driver"
     * @return string
     */
    public String getDriver() {
        return properties.getProperty("jdbc.driver");
    }

    /**
     * get property "jdbc.username"
     * @return string
     */
    public String getName() {
        return properties.getProperty("jdbc.username");
    }

    /**
     * get property "jdbc.password"
     * @return string
     */
    public String getPassword() {
        return properties.getProperty("jdbc.password");
    }
}
