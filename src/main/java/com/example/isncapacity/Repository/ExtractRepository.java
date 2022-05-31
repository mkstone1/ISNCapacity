package com.example.isncapacity.Repository;

import com.example.isncapacity.Utility.DatabaseConnectionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ExtractRepository {

    public String getLastCreatedExtractID(){
        try {
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement psts = conn.prepareStatement("SELECT extract_id from data_extract order by extract_id DESC LIMIT 1");
            ResultSet resultSet = psts.executeQuery();
            String extractID = "";
            while(resultSet.next()){
                extractID = resultSet.getString(1);
            }
            return extractID;
        }

        catch (SQLException e){
            System.out.println("Cannot connect to database or error in database");
            e.printStackTrace();
            return "";
        }
    }

    public void saveExtractToDB(){
        String timestampString = "";
        File timestamp = new File("staticinfo/timestamp.txt");

        try {
            Scanner scanner = new Scanner(timestamp);
            timestampString = scanner.nextLine();
        }
        catch (FileNotFoundException e){
            System.out.println("Could not find timestamp file for data extract");
        }
        Connection conn = DatabaseConnectionManager.getConnection();
        try{
            PreparedStatement ppst = conn.prepareStatement(
                    "insert into data_extract (time) values(?)");
            ppst.setString(1,timestampString);
            ppst.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getErrorCode());
        }
    }
}
