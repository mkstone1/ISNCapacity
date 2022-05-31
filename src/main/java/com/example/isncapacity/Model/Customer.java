package com.example.isncapacity.Model;

import java.util.ArrayList;

public class Customer {
    private String name;
    private String abbreviation;
    private String ID;
    private ArrayList<VM> customersVMs = new ArrayList<>();

    public Customer(String ID, String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.ID = ID;
    }

    public Customer(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
