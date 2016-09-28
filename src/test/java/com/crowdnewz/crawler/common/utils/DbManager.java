package com.crowdnewz.crawler.common.utils;

import com.crowdnewz.crawler.configs.ConfigCrawler;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DbManager {

    static Logger log = Logger.getLogger("BaseMethods");

    private static Connection conn = null;


    public static void setMysqlDbConnection() {

        PropertyConfigurator.configure("Log4j.properties");

        try {

            Class.forName(ConfigCrawler.mysqldriver).newInstance();
            conn = DriverManager.getConnection(ConfigCrawler.mysqlurl, ConfigCrawler.mysqluserName, ConfigCrawler.mysqlpassword);
            if (!conn.isClosed())

                log.info("Successfully connected to MySQL server");


        } catch (Throwable e) {

            log.error("Cannot connect to database server", e);


        }


    }


    public static void closeMysqlDbConnection() throws SQLException {

        try {
            conn.close();
            log.info("Succesfully closed connection to database server");

        } catch (SQLException e) {
            log.error("Could not close connection to database server", e);

        }
    }


    public static List<String> getMysqlQuery(String query) throws SQLException {


        Statement St = conn.createStatement();
        ResultSet rs = St.executeQuery(query);
        List<String> values1 = new ArrayList<String>();
        while (rs.next()) {

            values1.add(rs.getString(1));


        }

        return values1;
    }


    public static void MysqlDMLQuery(String query) throws SQLException {


        Statement St = conn.createStatement();
        int rs = St.executeUpdate(query);

    }


    public static void MySqlPreparedStatment(String query, String param1, String param2, String param3) throws SQLException {


        PreparedStatement Statement = null;

        try {
            Statement = conn.prepareStatement(query);

            if (param1 != null) {
                Statement.setString(1, param1);
            }

            if (param2 != null) {
                Statement.setString(2, param2);
            }

            if (param3 != null) {
                Statement.setString(3, param3);
            }

            Statement.executeUpdate();

            log.info("Succesfully updated/inserted to DB");

        } catch (SQLException e) {
            log.error("Could not run the prepared statement", e);

            if (conn != null) {
                try {
                    log.error("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    log.error("SQL Exception found", excep);

                }
            }
        } finally {
            if (Statement != null) {
                Statement.close();
                log.info("SQL statement is closed");
            }

        }

    }

}
