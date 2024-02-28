package utilities;

import io.cucumber.java.hu.Ha;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCUtils {

    /*
    .establishDBConnection(URL, Username, Password); -> accept URL, Username, Password as parameters, no returns
    .executeQuery(query); -> accepts query as parameter, returns data as list of maps
    .closeConnection(); -> this method will close connection to connected database.

     Shared objects: Should be declared as Global variable
     Connection
     Statement
     ResultSet
     */

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void establishDBConnection(String URL, String username, String password) throws SQLException {
        connection= DriverManager.getConnection(URL, username, password);
        statement=connection.createStatement();
    }

    public static List<Map<String, Object>> executeQuery(String query) throws SQLException {
        // All data from table - rows
        resultSet=statement.executeQuery(query);

        // All column details
        ResultSetMetaData metaData=resultSet.getMetaData();

        List<Map<String,Object>> table=new ArrayList<>();

        // looping through rows
        while(resultSet.next()){
            // 1row=1map
            Map<String, Object> map=new HashMap<>();
            // Looping through columns
            for(int i=1; i<=metaData.getColumnCount(); i++){
                    //             columnName    ,    value
                map.put(metaData.getColumnName(i), resultSet.getString(i));
            }
            table.add(map);
        }
        return table;
    }

    public static void closeConnection() throws SQLException {
        if(connection!=null && statement!=null && resultSet!=null){
            connection.close();
            statement.close();
            resultSet.close();
        }
    }

}
