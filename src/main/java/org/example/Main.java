package org.example;

import com.fasterxml.jackson.core.JsonLocation;

import java.awt.*;

public class Main {

    public static void main(String[] args) throws InterruptedException, AWTException {

        //NOTE: Please wait a couple of seconds after the POST test, as we are comparing the string after it has been sent

        //Replace these with your own locations:
        String WebDriverLocation = "S:\\Program\\IntelliJ IDEA\\SideLoad\\chromedriver_win32\\chromedriver.exe";
        String JsonLocation = "C:\\Users\\vikto\\Documents\\Facebook.json";

        FacebookSearch faceSearch = new FacebookSearch(WebDriverLocation, JsonLocation);
        FacebookPost facePost = new FacebookPost(WebDriverLocation, JsonLocation);
        FacebookLog faceLogout = new FacebookLog(WebDriverLocation, JsonLocation);
        //Uncomment the test you want to perform:

        faceLogout.method();
        //faceSearch.method();
        //facePost.method();

    }
}