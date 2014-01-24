package pivotal.university.acceptance.test.config;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Properties;

public class Messages {
    private static Properties messages;
        static {
        // Read properties file.
        messages = new Properties();
        try {
            ClassPathResource userMessages = new ClassPathResource("userMessages.properties");
            messages.load(userMessages.getInputStream());
            messages.putAll(System.getProperties());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getMessage(String messageKey) {
        if(messages.containsKey(messageKey)) { return messages.getProperty(messageKey); }
        else { throw new RuntimeException("Message with key '" + messageKey + "' not defined in properties file"); }
    }
}
