package com.crowdnewz.crawler.implementations;

import com.crowdnewz.crawler.common.utils.DbManager;
import com.crowdnewz.crawler.configs.ConfigCrawler;
import com.crowdnewz.crawler.configs.PageFields;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class NDTVnewsCrawler {

    static Logger log;

    public static void main(String[] args) throws InterruptedException, SQLException, ClassNotFoundException {

        log = Logger.getLogger("NDTVnewsCrawler");
        PropertyConfigurator.configure("Log4j.properties");

        log.info("Opening the main thread of NDTV crawler");

        BaseMethods baseMethods = new BaseMethods();
        WebDriver driver = baseMethods.SetUp("http://www.ndtv.com/rss");

        log.info("Getting http://www.ndtv.com/rss");

        Thread.sleep(2000L);

        try {

            driver.findElement(By.xpath(PageFields.noThanksForBreakingNewsAlertInNDTBHomePage)).click();
            log.warn("The breaking news Alert was closed by pressing OK button");


        } catch (NoSuchElementException ignored) {
            log.warn("The breaking news Alert Element did not come up on time. Exception Ignored for time being");

        } finally {

            List<WebElement> allLinksInThePage = driver.findElements(By.cssSelector(PageFields.identifierForAllLinksInBDTVHomePage));


            List<String> LinkTextOfAllLinks = new ArrayList<String>();
            List<String> URLOfAllLinks = new ArrayList<String>();


            for (int i = 0; i < allLinksInThePage.size(); i++) {

                LinkTextOfAllLinks.add(allLinksInThePage.get(i).getText());
                URLOfAllLinks.add(allLinksInThePage.get(i).getAttribute("href"));


            }

            log.info("All the links of the content topics are obtained");

            for (int j = 0; j < allLinksInThePage.size(); j++) {


                getHeadLinesFromLinks(LinkTextOfAllLinks.get(j), URLOfAllLinks.get(j), PageFields.identifierForHeadLinesLinksInNDTVRSSPages);
            }


        }


        baseMethods.tearDown(driver);
        log.info("Closing the main thread of NDTV crawler");
    }

    static void getHeadLinesFromLinks(String channelTopicName, String URLs, String selectors) throws SQLException, ClassNotFoundException {

        log = Logger.getLogger("NDTVnewsCrawler");
        PropertyConfigurator.configure("Log4j.properties");

        BaseMethods baseMethods = new BaseMethods();
        WebDriver driver = baseMethods.SetUp(URLs);

        List<WebElement> headLinesFromLinks = driver.findElements(By.cssSelector(selectors));

        List<String> LinkTextOfAllHeadLines = new ArrayList<String>();
        List<String> URLOfAllHeadLines = new ArrayList<String>();

        for (int i = 0; i < headLinesFromLinks.size(); i++) {

            LinkTextOfAllHeadLines.add(headLinesFromLinks.get(i).getText());
            URLOfAllHeadLines.add(headLinesFromLinks.get(i).getAttribute("href"));

        }

        log.info("Obtained all News Headlines URLs for the current topic " + channelTopicName);

        for (int j = 0; j < headLinesFromLinks.size(); j++) {

            DbManager.setMysqlDbConnection();
            DbManager.MySqlPreparedStatment(ConfigCrawler.NewsArticleTableUpdateQuery, channelTopicName, LinkTextOfAllHeadLines.get(j), URLOfAllHeadLines.get(j));
            DbManager.closeMysqlDbConnection();

        }

        log.info("Persited all News Headlines URLs for the current topic in the database " + channelTopicName);

        baseMethods.tearDown(driver);

    }


}

