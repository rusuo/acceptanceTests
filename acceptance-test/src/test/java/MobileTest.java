import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import cucumber.api.java.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MobileTest {
    WebDriver driver = null;

    @Before
    public void setup() throws MalformedURLException {
        File appDir = new File("/Users/oanarusu/Downloads");
        File app = new File(appDir, "Online-Translator.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("device","Android");
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(CapabilityType.VERSION, "4.2.2");
        capabilities.setCapability(CapabilityType.PLATFORM, "MAC");
        // Here we mention the app's package name, to find the package name we  have to convert .apk file into java class files
        capabilities.setCapability("app-package","com.myApp.activity");
        //Here we mention the activity name, which is invoked initially as app's first page.
        capabilities.setCapability("app-activity","LoginActivity");
//capabilities.setCapability("app-wait-activity","LoginActivity,NewAccountActivity");
        capabilities.setCapability("app", app.getAbsolutePath());

        driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);

    }

    @Test
    public void loginTest() throws Exception {
        driver.findElement(By.xpath("//EditText[@text='Email Address']")).sendKeys("tester@gmail.com");
        driver.findElement(By.xpath("//LinearLayout/EditText[2]")).sendKeys("Testerpwd");
        driver.findElement(By.xpath("//CheckBox")).click();
        driver.findElement(By.xpath("//Button[@text='Login']")).click();

        WebDriverWait wait = new WebDriverWait(driver,80);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//Button[@text='Logout']")));
        driver.findElement(By.xpath("//Button[@text='Logout']")).click();

    }

    @After
    public void tearDown() {
        driver.quit();
    }

}