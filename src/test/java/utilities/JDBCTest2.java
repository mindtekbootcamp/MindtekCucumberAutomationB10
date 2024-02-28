package utilities;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class JDBCTest2 {

    public static void main(String[] args) throws SQLException {

        JDBCUtils.establishDBConnection(
                ConfigReader.getProperty("HRDBURL"),
                ConfigReader.getProperty("HRDBUsername"),
                ConfigReader.getProperty("HRDBPassword")
        );

        List<Map<String,Object>> data = JDBCUtils.executeQuery("select FIRST_NAME from employees");

        JDBCUtils.closeConnection();

        System.out.println(data);

    }

}
