package com.gmaxmart.resort1000.models.resortmodels;

import java.util.ArrayList;

public class Constants {
    // ArrayList and return the ArrayList
    public static ArrayList<Employee> getEmployeeData()
    {
        // create an ArrayList of type Employee class
        ArrayList<Employee> employeeList
                = new ArrayList<Employee>();
        Employee emp1 = new Employee("Chinmaya Mohapatra",
                "chinmaya@gmail.com");
        employeeList.add(emp1);
        Employee emp2
                = new Employee("Ram prakash", "ramp@gmail.com");
        employeeList.add(emp2);
        Employee emp3 = new Employee("OMM Meheta",
                "mehetaom@gmail.com");
        employeeList.add(emp3);
        Employee emp4 = new Employee("Hari Mohapatra",
                "harim@gmail.com");
        employeeList.add(emp4);
        Employee emp5 = new Employee(
                "Abhisek Mishra", "mishraabhi@gmail.com");
        employeeList.add(emp5);
        Employee emp6 = new Employee("Sindhu Malhotra",
                "sindhu@gmail.com");
        employeeList.add(emp6);
        Employee emp7 = new Employee("Anil sidhu",
                "sidhuanil@gmail.com");
        employeeList.add(emp7);
        Employee emp8 = new Employee("Sachin sinha",
                "sinhas@gmail.com");
        employeeList.add(emp8);
        Employee emp9 = new Employee("Amit sahoo",
                "sahooamit@gmail.com");
        employeeList.add(emp9);
        Employee emp10 = new Employee("Raj kumar",
                "kumarraj@gmail.com");
        employeeList.add(emp10);

        return employeeList;
    }
}