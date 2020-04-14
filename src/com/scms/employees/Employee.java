/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scms.employees;

import com.scms.finances.Finance;
import com.scms.main.Main;

/**
 *
 * @author Admin
 */
public abstract class Employee {
    //age,name,salary
    protected int id;
    protected String name;
    protected int age;
    protected double salary;

    public Employee( String name, int age, double salary) {
       
        this.name = name;
        this.age = age;
        this.salary = salary;
        int flag = 0;
        this.id =  (int)(Math.random()*10000);
       Finance.hireEmployee(salary);
   
    }

    public Employee(int id, String name, int age, double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
   
    
    public Employee() {
    }

    
    
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    public int getId() {
        return id;
    }
    
    public abstract double calculateSalary();
    public abstract String getClassName();
    @Override
    public String toString() {
        return "Employee{" + "name=" + name + ", age=" + age + ", salary=" + salary + '}';
    }



}
