package com.crowdnewz.crawler.implementations;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class BaseMethods {

    Logger log = Logger.getLogger("BaseMethods");


    public WebDriver SetUp(String getURL) {

        PropertyConfigurator.configure("Log4j.properties");

        WebDriver driver = new FirefoxDriver();

        driver.get(getURL);

        log.info("Firefox driver set up and getting URL");

        return driver;


    }


    public void tearDown(WebDriver driver) {

        driver.close();
        driver.quit();

        log.info("Firefox driver closed and quit");

    }

}

