/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scms.employees;

/**
 *
 * @author Admin
 */
public class Cook extends Employee{

    public Cook( String name, int age, double salary) {
        super( name, age, salary);
    }
public Cook(int id, String name, int age, double salary) {
        super(id, name, age, salary);
    }
   
    
   public double  calculateSalary(){
       //depending on the age and type the salary is increased
       if(age > 50){
           return salary + (salary * 5)/ 100;
       }
       else return salary;
   }
   public String getClassName(){
        return "Cook";
    }
}
