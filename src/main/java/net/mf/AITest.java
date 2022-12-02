package net.mf;

import com.hp.lft.report.ReportException;
import com.hp.lft.report.Reporter;
import com.hp.lft.report.Status;
import com.hp.lft.sdk.ai.AiObject;
import com.hp.lft.sdk.ai.AiObjectDescription;
import com.hpe.leanft.selenium.By;
import com.hpe.leanft.selenium.Utils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.hp.lft.sdk.*;
import com.hp.lft.sdk.web.*; // added manually
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.hp.lft.verifications.*; // added by template
import unittesting.*;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;


public class AITest extends UnitTestClassBase {
    private static final String ADV_WEBSITE  = "http://nimbusserver.aos.com:8000/#/";
    private static final String ADV_LOGIN    = "Mercury"; //"insert login name here";
    private static final String ADV_PASSWORD = "Mercury"; //"insert password here";

    private ChromeDriver chromeDriver;
    private Browser browser;

    public AITest() {
        //Change this constructor to private if you supply your own public constructor
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        instance = new AITest();
        globalSetup(AITest.class);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        globalTearDown();
    }

    @Before
    public void setUp() throws Exception {
        WebDriverManager.chromedriver().forceCache();
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        // The following line used to cause a  “Failed to load extension…” error so I would comment it.
        // It turns out that was caused by having the UFT Agent installed by Policy instead of manually.
        // The fix was to remove the Chrome registry key folder under HKEY_LOCAL_MACHINE\SOFTWARE\Policies\Google
        // and manually install the UFT Agent in the browser that Selenium spawns
        options.addExtensions(new File("C:\\Program Files (x86)\\Micro Focus\\UFT One\\Installations\\Chrome\\V3\\Agent.crx"));
        chromeDriver = new ChromeDriver(options);
        chromeDriver.get(ADV_WEBSITE);
        chromeDriver.manage().window().maximize(); // Best to maximize for AI functions

        browser = BrowserFactory.attach(new BrowserDescription.Builder().type(
                BrowserType.CHROME).build());

    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(5000); // Sleep 5 seconds before closing up
        chromeDriver.close();
    }

    @Test
    public void test() throws GeneralLeanFtException, InterruptedException, ReportException, IOException {

        WebDriverWait wait = new WebDriverWait(chromeDriver, 10);
        // Increase the value below to 120000 to allow two minutes pause to install UFT Agent into
        // the Selenium-spawned browser and stop/rerun the test - just needed the first time
        Thread.sleep(3000); // Wait 3 secs to allow browser to settle

        //Login to Advantage by clicking the Profile icon (Selenium style)
        // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/header/nav/ul/li[3]/a/a")));
        // Utils.highlight(chromeDriver.findElement(By.xpath("/html/body/header/nav/ul/li[3]/a/a")), 3000);
        // chromeDriver.findElement(By.xpath("/html/body/header/nav/ul/li[3]/a/a")).click();

        // Login to Advantage using AI to find and click the Profile icon (AI and UFT-Dev Style)
        browser.describe(AiObject.class, new AiObjectDescription(com.hp.lft.sdk.ai.AiTypes.PROFILE)).click();

        Thread.sleep(3000); // Wait 3 secs to allow Profile window to settle

        // Click on Username and then type Mercury into field (AI and UFT-Dev Style)
        browser.describe(AiObject.class, new AiObjectDescription.Builder()
                .aiClass(com.hp.lft.sdk.ai.AiTypes.INPUT)
                .text("Username")
                .locator(new com.hp.lft.sdk.ai.Position(com.hp.lft.sdk.ai.Direction.FROM_TOP, 0)).build()).click();

        browser.describe(AiObject.class, new AiObjectDescription.Builder()
                .aiClass(com.hp.lft.sdk.ai.AiTypes.INPUT)
                .text("Username")
                .locator(new com.hp.lft.sdk.ai.Position(com.hp.lft.sdk.ai.Direction.FROM_LEFT, 0)).build()).sendKeys(ADV_LOGIN);

        // Thread.sleep(10000); // Pause if you want to see it working

        // Click on Username and then type ADV_LOGIN into field (Selenium-style code)
        // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[1]/div/input")));
        // Utils.highlight(chromeDriver.findElement(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[1]/div/input")), 1000);
        // chromeDriver.findElement(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[1]/div/input")).sendKeys(ADV_LOGIN);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[2]/div/input")));
        Utils.highlight(chromeDriver.findElement(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[2]/div/input")), 1000);
        chromeDriver.findElement(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[2]/div/input")).sendKeys(ADV_PASSWORD);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("SIGN IN")));
        Utils.highlight(chromeDriver.findElement(By.visibleText("SIGN IN")), 3000);
        chromeDriver.findElement(By.visibleText("SIGN IN")).click();
        Thread.sleep(2000);

        //Click on Tablets
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("TABLETS")));
        Utils.highlight(chromeDriver.findElement(By.visibleText("TABLETS")), 1000);
        RenderedImage img = Utils.getSnapshot(chromeDriver.findElement(By.visibleText("TABLETS")));
        Reporter.reportEvent("TABLETS","Found", Status.Passed, img);
        chromeDriver.findElement(By.visibleText("TABLETS")).click();

        //Click on specific tablet
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("HP Pro Tablet 608 G1")));
        Utils.highlight(chromeDriver.findElement(By.visibleText("HP Pro Tablet 608 G1")), 1000);
        chromeDriver.findElement(By.visibleText("HP Pro Tablet 608 G1")).click();
        Thread.sleep(3000);

        //Add Tablet to cart
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("ADD TO CART")));
        Utils.highlight(chromeDriver.findElement(By.visibleText(("ADD TO CART"))), 1000);
        chromeDriver.findElement(By.visibleText("ADD TO CART")).click();

        //Go to Checkout
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText(Pattern.compile("CHECKOUT \\(\\$*"))));
        Utils.highlight(chromeDriver.findElement(By.visibleText(Pattern.compile("CHECKOUT \\(\\$*"))), 1000);
        chromeDriver.findElement(By.visibleText(Pattern.compile("CHECKOUT \\(\\$*"))).click();

        //Checkout - Use XPath as visibleText was not working correctly on BlueShift
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"next_btn\"]")));
        Utils.highlight(chromeDriver.findElement(By.xpath("//*[@id=\"next_btn\"]")), 1000);
        chromeDriver.findElement(By.xpath("//*[@id=\"next_btn\"]")).click();

        String path ="//*[@id=\"paymentMethod\"]/div/div[2]/sec-form/sec-view[1]/div/input";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        Utils.highlight(chromeDriver.findElement(By.xpath(path)), 1000);
        chromeDriver.findElement(By.xpath(path)).clear();
        chromeDriver.findElement(By.xpath(path)).sendKeys(ADV_LOGIN);

        path = "//*[@id=\"paymentMethod\"]/div/div[2]/sec-form/sec-view[2]/div/input";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        Utils.highlight(chromeDriver.findElement(By.xpath(path)), 1000);
        chromeDriver.findElement(By.xpath(path)).clear();
        chromeDriver.findElement(By.xpath(path)).sendKeys(ADV_PASSWORD+"1");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("PAY NOW")));
        Utils.highlight(chromeDriver.findElement(By.visibleText("PAY NOW")), 1000);
        chromeDriver.findElement(By.visibleText("PAY NOW")).click();

        //Logout of Advantage
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("menuUser")));
        Utils.highlight(chromeDriver.findElement(By.id("menuUser")), 1000);
        chromeDriver.findElement(By.id("menuUser")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("Sign out")));
        Utils.highlight(chromeDriver.findElement(By.visibleText("Sign out")), 1000);
        chromeDriver.findElement(By.visibleText("Sign out")).click();

        //Added sleep here to give time to see the selection
        Thread.sleep(3000);

    }

}
