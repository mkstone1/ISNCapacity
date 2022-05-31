package com.example.isncapacity;

import com.example.isncapacity.Model.Customer;
import com.example.isncapacity.Services.CustomerServices;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateCustomerTest {
    
    @Test
    void createCustomer(){
        //Arrange
        CustomerServices customerServices = new CustomerServices();
        String nameThatExisits = "kunde1";
        String abbrThatExisits = "kn1";
        String nameThatDoesNotExisits = "kunde11";
        String abbrThatDoesNotExisits = "kn9";

        boolean expctedCreateSuccess = true;
        boolean expctedCreatedFail = false;

        //ACT
        boolean createFail = customerServices.createCustomer(nameThatExisits,abbrThatExisits);
        boolean createFail2 = customerServices.createCustomer(nameThatExisits,abbrThatDoesNotExisits);
        boolean createFail3 =  customerServices.createCustomer(nameThatDoesNotExisits,abbrThatExisits);
        boolean createSuccess = customerServices.createCustomer(nameThatDoesNotExisits,abbrThatDoesNotExisits);

        //Assert
        assertEquals(expctedCreateSuccess, createSuccess);
        assertEquals(expctedCreatedFail,createFail);
        assertEquals(expctedCreatedFail,createFail2);
        assertEquals(expctedCreatedFail,createFail3);
    }
}
