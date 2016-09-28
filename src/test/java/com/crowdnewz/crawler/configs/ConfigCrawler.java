package com.crowdnewz.crawler.configs;

public class ConfigCrawler {


    //MYSQL DATABASE DETAILS
    public static String mysqldriver = "com.mysql.jdbc.Driver";
    public static String mysqluserName = "root";
    public static String mysqlpassword = "password";
    public static String mysqlurl = "jdbc:mysql://localhost:3306/crowdnewz";


    public static String NewsArticleTableUpdateQuery = "insert into newsarticle (CHANNEL_ID,CHANNEL_TOPIC,HEADLINE_TAG,ARTICLE_URL,ARTICLE_UPDATED_DTTM,LAST_UPDATED_DTTM)" +
            " VALUES ('NDTV.com', ? ,  ?  , ?  ,NOW(),NOW())";


}
