package pivotal.university.acceptance.test.config;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.core.io.ClassPathResource;
import pivotal.university.acceptance.test.testData.User;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * User: tris
 * Date: 27/03/13
 */
public class Settings {
    public static final String applicationUrl;
    public static final String browser;

    public static final int browserTimeout = 10;

    public static final DateTimeFormatter dateFormat;
    public static final DateTimeFormatter timeFormat;

    public static final String adminUsername;
    public static final String adminPassword;

    static {
        // Read properties file.
        Properties properties = new Properties();
        try {
            ClassPathResource res = new ClassPathResource("environment.properties");
            properties.load(res.getInputStream());
            properties.putAll(System.getProperties());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        applicationUrl = properties.getProperty("applicationUrl");
        browser = properties.getProperty("browser");

        dateFormat = DateTimeFormat.forPattern("dd/MM/yyyy");
        timeFormat = DateTimeFormat.forPattern("HH:mm");

        adminUsername = properties.getProperty("adminUsername");
        adminPassword = properties.getProperty("adminPassword");
    }



}
