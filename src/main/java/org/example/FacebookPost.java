package org.example;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.time.Duration;
import org.slf4j.Logger;

public class FacebookPost {

    private Logger logger = LoggerFactory.getLogger(FacebookPost.class);
    private String WebDriverLocation = null;
    private String JSONloc = null;

    public FacebookPost(String locWebDriver, String locJSON){
        WebDriverLocation = locWebDriver;
        JSONloc = locJSON;
    }

    public void method() throws InterruptedException {
        ArrayList<String> listvid = new ArrayList<String>();
        String PostString = "The last post really";



            // Set the path to the chromedriver executable
        System.setProperty("webdriver.chrome.driver", WebDriverLocation);

            // Configure the WebDriver to not accept announcements and popups
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
            // Click on the "What's on your mind" element
            WebElement postButton = driver.findElement(By.xpath("//span[contains(text(), 'What')]"));
            postButton.click();

            TimeUnit.SECONDS.sleep(1);

            // Enter the text "hej" in the post textarea
            WebElement postTextArea = driver.findElement(By.xpath("//div[contains(@class, 'notranslate')][@role='textbox']"));
            postTextArea.sendKeys(PostString);

            TimeUnit.SECONDS.sleep(1);



            // Click on the "Post" button to make the post
            WebElement postButton2 = driver.findElement(By.xpath("//span[text()='Post']"));
            postButton2.click();
            TimeUnit.SECONDS.sleep(1);

        } catch (Exception e) {
            logger.error("Could not make a post");
        }


        logger.info("Post has been sent");



        TimeUnit.SECONDS.sleep(1);
        driver.get("https://www.facebook.com/profile.php");
        TimeUnit.SECONDS.sleep(3);
        WebElement post = driver.findElement(By.id("facebook"));
        TimeUnit.SECONDS.sleep(1);
        String latestPostText = post.getText();
        TimeUnit.SECONDS.sleep(1);

        //Comparing the last post to make sure it is the same as the one intended to be sent

        //Selecting the last post from the profile page

        String lines[] = latestPostText.split("\\r?\\n");
        int tot = 0;
        int place = 0;
        for (int i=0;i<lines.length;i++){
            if(lines[i].contains("Shared")){
                listvid.add(lines[i+1]);
            }
        }


        boolean equal = listvid.get(0).equals(PostString);

        //Comparison

        if (equal) {
            logger.info("The strings are equal, both strings are: " + listvid.get(0));
        } else {
            logger.error("The strings are not equal: " + listvid.get(0) + " and " + PostString);
        }

        logger.info("The test is finished");

        }

    }

