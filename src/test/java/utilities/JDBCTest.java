package utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCTest {

    public static void main(String[] args) throws SQLException {

        Connection connection=DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/HR_QA",
                "postgres",
                "Admin123"
        );
        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery("select * from employees");

//        resultSet.next();
//        System.out.println(resultSet.getString("FIRST_NAME"));
//        System.out.println(resultSet.getString("SALARY"));
//
//        resultSet.next();
//        System.out.println(resultSet.getString("FIRST_NAME"));
//
//        resultSet.next();
//        resultSet.next();
//        resultSet.next();
//        System.out.println(resultSet.getString("PHONE_NUMBER"));

        // .next(); -> method returns TRUE if there is next row is available
        // .next(); -> method returns FALSE if there is no data in next row

        // ResultSetMetaData -> will provide column names
        ResultSetMetaData metaData=resultSet.getMetaData();
        System.out.println(metaData.getColumnName(5));
        System.out.println(metaData.getColumnCount());

        // Print all columns
        for(int i=1; i<=metaData.getColumnCount(); i++){
            System.out.println(metaData.getColumnName(i));
        }

        List<Map<String, Object>> table=new ArrayList<>();

        // Looping through each row. If there is no next row, then next method return false
        while(resultSet.next()){
            Map<String, Object> map=new HashMap<>();
            // Looping though each column
            for(int i=1; i<metaData.getColumnCount(); i++){
                // Storing each cell of row into map
                map.put(metaData.getColumnName(i), resultSet.getString(i));
            }
            table.add(map);
        }

        System.out.println(table);

        System.out.println("-------------");
        System.out.println(table.get(5).get("email"));
        System.out.println(table.get(19).get("salary"));
        System.out.println(table.get(31).get("phone_number"));
        System.out.println(table.get(37).get("hire_date"));

        System.out.println("===================");
        // Print all last names that starts with letter 'A'
        for(int i=0; i<table.size(); i++){
            if(table.get(i).get("last_name").toString().startsWith("A"))
                System.out.println(table.get(i).get("last_name"));
        }

        /*
        1. Print all first names and salary of those employees who makes more than 25000 salary
        2. Print all ids and names of those employees who has manager id
        3. Print first and last names of employees who has salary more than average salary
         */

    }

}
