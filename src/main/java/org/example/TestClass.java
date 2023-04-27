package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestClass {

    public void method() throws InterruptedException, AWTException {


            ArrayList<String> listvid = new ArrayList<String>();

            // Set the path to the chromedriver executable
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\vikto\\Downloads\\chromedriver_win32\\chromedriver.exe");

            // Create a new instance of the ChromeDriver

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            options.addArguments("--start-maximized");
            options.addArguments("--lang=en"); // Set language to English
            options.addArguments("--disable-features=DarkMode");

            // Create a new ChromeDriver instance with the specified options
            WebDriver driver = new ChromeDriver(options);

            // Navigate to YouTube
            driver.get("https://www.youtube.com");

            // Wait for 5 seconds
            TimeUnit.SECONDS.sleep(1);




            WebElement element = driver.findElement(By.id("cb-header"));

// Replace the text content of the element
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].textContent = arguments[1];", element, "Du vill låna Du vill låna Du vill låna Du vill låna Du vill låna Du vill låna Du vill låna Du vill låna Du vill låna Du vill låna Du vill låna Du vill låna Du vill låna Du vill låna");

            TimeUnit.SECONDS.sleep(4);

            WebElement element4 = driver.findElement(By.cssSelector(".yt-spec-button-shape-next.yt-spec-button-shape-next--filled.yt-spec-button-shape-next--call-to-action.yt-spec-button-shape-next--size-m"));

// Click the element
            element4.click();

            TimeUnit.SECONDS.sleep(1);






            WebElement searchBox = driver.findElement(By.name("search_query"));
            searchBox.sendKeys("SlinkySchrubs");
            TimeUnit.MILLISECONDS.sleep(200);
            searchBox.submit();
            TimeUnit.SECONDS.sleep(1);
            WebElement firstChannel = driver.findElement(By.xpath("//*[@id=\"contents\"]/ytd-channel-renderer[1]"));
            firstChannel.click();
            TimeUnit.SECONDS.sleep(1);

            // Go to the newest video
            WebElement newestVideo = driver.findElement(By.xpath("//*[@id=\"tabsContent\"]/tp-yt-paper-tab[2]/div"));
            newestVideo.click();
            TimeUnit.SECONDS.sleep(2);

            // Locate the first video element on the page using its HTML tag and attributes
            WebElement element2 = driver.findElement(By.xpath("//div[contains(@class, 'style-scope ytd-two-column-browse-results-renderer')]"));
            String text = element2.getText();
            String lines[] = text.split("\\r?\\n");
            int tot = 0;
            int place = 0;
            for (int i=0;i<lines.length;i++){
                    if(lines[i].contains("views")){
                            listvid.add(lines[i]);
                            int eee = (listvid.size())-1;
                            System.out.println(listvid.get(eee));
                            String new2 = listvid.get(eee).replaceAll("[^0-9]", "");
                            listvid.set(eee,new2);
                            place = Integer.parseInt(listvid.get(eee));
                            tot = tot+place;
                    }

            }

            System.out.println("the channel has a total of "+tot+" views");

            WebElement elementByPartialText2 = driver.findElement(By.partialLinkText("Multirole"));
            elementByPartialText2.click();


            TimeUnit.SECONDS.sleep(2);

            JavascriptExecutor js2 = (JavascriptExecutor) driver;
            js2.executeScript("window.scrollBy(0, 450)");


        }
    }

