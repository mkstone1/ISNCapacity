package com.example.isncapacity.Repository;

import com.example.isncapacity.Model.Customer;
import com.example.isncapacity.Utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerRepository {


    public void saveNewCustomer(Customer newCust){
        Connection conn = DatabaseConnectionManager.getConnection();
            try{
                PreparedStatement ppst = conn.prepareStatement(
                        "insert into customer(customer_name, customer_abbreviation) values(?,?)");
                ppst.setString(1,newCust.getName());
                ppst.setString(2,newCust.getAbbreviation());
                ppst.executeUpdate();
            }
            catch (SQLException e){
                System.out.println("Failed to create customer");
            }

        }

        public ArrayList<Customer> getAllCustomers(){
        ArrayList<Customer> allCustomers = new ArrayList<>();
            try {
                Connection conn = DatabaseConnectionManager.getConnection();
                PreparedStatement psts = conn.prepareStatement("SELECT * from customer ");
                ResultSet resultSet = psts.executeQuery();
                String customerID= "";
                String customerName = "";
                String customerAbbr = "";

                while(resultSet.next()){
                    customerID = resultSet.getString(1);
                    customerName = resultSet.getString(2);
                    customerAbbr = resultSet.getString(3);
                    allCustomers.add(new Customer(customerID,customerName,customerAbbr));
                }
                return allCustomers;
            }

            catch (SQLException e){
                System.out.println("Cannot connect to database or error in database");
                e.printStackTrace();
                return allCustomers;
            }
        }

        public Customer getCustomerFromName(String name){
            Customer customer = null;
            try {
                Connection conn = DatabaseConnectionManager.getConnection();
                PreparedStatement psts = conn.prepareStatement("SELECT * from customer where customer_name = ?");
                psts.setString(1, name);
                ResultSet resultSet = psts.executeQuery();

                String customerID= "";
                String customerName = "";
                String customerAbbr = "";

                while(resultSet.next()){
                    customerID = resultSet.getString(1);
                    customerName = resultSet.getString(2);
                    customerAbbr = resultSet.getString(3);
                    customer = new Customer(customerID,customerName,customerAbbr);
                }
                return customer;
            }

            catch (SQLException e){
                System.out.println("Cannot connect to database or error in database");
                e.printStackTrace();
                return customer;
            }
        }

    public String getCustomerIDFromName(String customerName){
        try {
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement psts = conn.prepareStatement("SELECT customer_id from customer where customer_name = ?");
            psts.setString(1,customerName);
            ResultSet resultSet = psts.executeQuery();
            String ID = "";
            while(resultSet.next()){
                ID = resultSet.getString(1);
            }
            return ID;
        }

        catch (SQLException e){
            System.out.println("Cannot connect to database or error in database");
            e.printStackTrace();
            return "";
        }
    }

}
