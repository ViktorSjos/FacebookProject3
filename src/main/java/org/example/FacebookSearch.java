package org.example;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FacebookSearch {

    private Logger logger = LoggerFactory.getLogger(FacebookSearch.class);
    private String WebDriverLocation = null;
    private String JSONloc = null;

    public FacebookSearch(String locWebDriver, String locJSON){
        WebDriverLocation = locWebDriver;
        JSONloc = locJSON;
    }
    public void method() throws InterruptedException, AWTException {

        // Set the path to the chromedriver executable
        System.setProperty("webdriver.chrome.driver", WebDriverLocation);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");

        // Create a new instance of the Chrome driver with the configured options
        WebDriver driver = new ChromeDriver(options);

        // Maximize the window
        driver.manage().window().maximize();

        // Navigate to Facebook
        driver.get("https://www.facebook.com");

        // Accept cookies
        TimeUnit.SECONDS.sleep(2);
        try {
            WebElement acceptButton = (new WebDriverWait(driver, Duration.ofSeconds(2)))
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-cookiebanner='accept_button']")));
            acceptButton.click();
        } catch (Exception e) {
            logger.error("Cookies could not be accepted");
            return;
        }

        logger.info("The program has accepted cookies");

        String email = null;
        String password = null;

        try {
            File jsonFile = new File(JSONloc);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonFile);

            email = jsonNode.get("facebookCredentials").get("email").asText();
            password = jsonNode.get("facebookCredentials").get("password").asText();

        } catch (Exception e) {
            logger.error("No JSON file found");
            e.printStackTrace();
        }



        try {
            WebElement usernameField = driver.findElement(By.id("email"));
            WebElement passwordField = driver.findElement(By.id("pass"));
            WebElement loginButton = driver.findElement(By.name("login"));

            usernameField.sendKeys(email);
            passwordField.sendKeys(password);
            loginButton.click();

        } catch (Exception e) {
            logger.error("Login failed");
        }

        logger.info("you have logged in");

        TimeUnit.SECONDS.sleep(7);

        try {
            WebElement searchBox = driver.findElement(By.cssSelector("input[placeholder='Search Facebook']"));
            searchBox.sendKeys("Harry Potter");
            TimeUnit.SECONDS.sleep(1);
            searchBox.sendKeys(Keys.ENTER);

        } catch (Exception e) {
            logger.error("Search failed");
        }

        logger.info("The test is finished");

    }
}