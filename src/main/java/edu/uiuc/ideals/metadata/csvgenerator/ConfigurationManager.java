package edu.uiuc.ideals.metadata.csvgenerator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by srobbins on 1/16/17.
 */
public class ConfigurationManager {
    //private Properties properties;

    public static Properties getProperties() throws IOException {
        Properties someProperties = new Properties();
        InputStream in = ConfigurationManager.class.getClassLoader().getResourceAsStream("settings.properties");
        if (in==null) System.out.println("can't find settings");
        someProperties.load(in);
        in.close();
        return someProperties;
    }
}
