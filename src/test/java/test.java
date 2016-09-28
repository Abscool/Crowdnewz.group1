import com.crowdnewz.crawler.common.utils.DbManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.sql.SQLException;


public class test {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println("hi");
        Logger log = Logger.getLogger("test");
        PropertyConfigurator.configure("Log4j.properties");
        log.info("hi Logs");
        int a = 5;
        String b = "fff";

        log.info("hi end of Logs");

    }
}

