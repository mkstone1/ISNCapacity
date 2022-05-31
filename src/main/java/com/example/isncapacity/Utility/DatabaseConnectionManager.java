package com.example.isncapacity.Utility;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionManager {

    private static String url;
    private static String username;
    private static String password;
    private static Connection conn;

    private DatabaseConnectionManager(){}

    public static Connection getConnection(){
        if(conn != null){
            return conn;
        }


        url = System.getenv("db.url");
        username = System.getenv("db.username");
        password = System.getenv("db.password");

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
