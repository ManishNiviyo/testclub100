package com.gmaxmart.resort1000.models.resortmodels;

import java.io.Serializable;

public class Employee implements Serializable {
    private final String name;   // name of the employee
    private final String email;  // email of the employee

    public Employee(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}