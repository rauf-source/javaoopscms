/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scms.finances;
import javafx.beans.property.*;
import javafx.beans.property.*;
import com.scms.employees.*;
import com.scms.main.*;
/**
 *
 * @author Admin
 */
public class Finance {
 
    public static DoubleProperty burnRate = new SimpleDoubleProperty();
    public static DoubleProperty totalEmployeesExpenditure = new SimpleDoubleProperty(); 
    public static DoubleProperty currentFunds = new SimpleDoubleProperty();

    public static double getCurrentFunds() {
        return currentFunds.get();
    }

    public static double getBurnRate() {
        return burnRate.get();
    }
    
    public static void setCurrentFunds(double funds) {
       currentFunds.set(funds);
      
    }

    public static double getTotalEmployeesExpenditure() {
        return totalEmployeesExpenditure.get();
    }

    public static void setTotalEmployeesExpenditure(DoubleProperty totalEmployeesExpenditure) {
        Finance.totalEmployeesExpenditure.set(totalEmployeesExpenditure.get());
    }
    //the method below will be called after the employee has been addedd. e.g after fetching from the database on the start/after hiring new Employee
    public static void updateTotalEmployeesExpenditure(){
        double sum = 0;
        for(Employee e: Main.employees){
            sum+=e.getSalary();
        }
        totalEmployeesExpenditure.set(sum);
        burnRate.set(currentFunds.get() / sum);
        System.out.println(totalEmployeesExpenditure.get()+"is zies 0"+ Main.employees.size());
    }
    //h
   
    public static void hireEmployee(double salary){
        
        currentFunds.set(currentFunds.get() - salary);  
        System.out.println(currentFunds.get());
    }
    
    //for buing rockets
    public static void buildRocket(double cost){
        if(currentFunds.get() < cost){
            throw new NotEnoughFundsException();
        }
        else{
        currentFunds.set(currentFunds.get() - cost);
        }
    }
}

