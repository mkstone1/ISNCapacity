package com.example.isncapacity.Services;

import com.example.isncapacity.Model.Customer;
import com.example.isncapacity.Repository.CustomerRepository;

import java.util.ArrayList;

public class CustomerServices {
    CustomerRepository custRepo = new CustomerRepository();

    public boolean createCustomer(String name, String abbr){


        ArrayList<Customer> allCustomers = custRepo.getAllCustomers();
        for(Customer customer : allCustomers){
            if(name.equals(customer.getName()) || abbr.equals(customer.getAbbreviation())){
                return false;
            }
        }
        Customer cust = new Customer(name, abbr);
        custRepo.saveNewCustomer(cust);
        return true;
    }

    public Customer getCustomerFromName(String name){
        return  custRepo.getCustomerFromName(name);
    }

    public ArrayList<Customer> getAllCustomers(){
        return custRepo.getAllCustomers();
    }
}
