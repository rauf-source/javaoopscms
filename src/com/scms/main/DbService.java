/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scms.main;
import java.sql.*;
import com.scms.employees.*;
import com.scms.finances.Finance;
import com.scms.rocket.Rocket;
/**
 *
 * @author Admin
 */
public class DbService {
    //methods
    //1. load data from db to classes(3 methods)
    //2. a method to add stuff to db
    //3. a method to delete stuff from db
    //4. Update method is only used for finance
       
    public static void fetchEmployeesOnStart(){
        //takes data from the db and adds to the empty Main.employees
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
           connection = DriverManager.getConnection("jdbc:mysql://localhost/scms", "root", "abdul123");
           statement = connection.createStatement();
           resultSet =  statement.executeQuery("SELECT * FROM employees");
           
           while(resultSet.next()){
               String name =  resultSet.getString("name");
               int id = resultSet.getInt("id");
               int age = resultSet.getInt("age");
               double salary = resultSet.getDouble("salary");
               String type = resultSet.getString("type");
//nameint id, String name, int age, double salary
               System.out.println(name + id+""+age+""+salary+type);
               if(type.equals("Engineer")){
                   Main.employees.add(new Engineer(id,name,age,salary));
               }else if(type.equals("Cook")){
                   Main.employees.add(new Cook(id,name,age,salary));
               }else if(type.equals("Doctor")){
                   Main.employees.add(new Doctor(id,name,age,salary));
               }else if(type.equals("Astronaut")){
                   Main.employees.add(new Astronaut(id,name,age,salary));
               }else {
                   Main.employees.add(new Executive(id,name,age,salary));
               }
               
        }
        }catch(SQLException exp){
            System.out.println(exp.getMessage());
        }finally{
            try{
               connection.close();
               statement.close();
               resultSet.close();
            }catch(SQLException exp2){
                System.out.println(exp2.getMessage());
            }
        }
    }


 public static void fetchRocketsOnStart(){
        //takes data from the db and adds to the empty Main.employees
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
           connection = DriverManager.getConnection("jdbc:mysql://localhost/scms", "root", "abdul123");
           statement = connection.createStatement();
           resultSet =  statement.executeQuery("SELECT * FROM rockets");
           
           while(resultSet.next()){
               String name =  resultSet.getString("name");
               int id = resultSet.getInt("id");
               double ppl = resultSet.getDouble("priceperlaunch");
               double capacity = resultSet.getDouble("capacity");
              Main.rockets.add(new Rocket(id,name,ppl,capacity));
//nameint id, String name, int age, double salary
              
               
        }
        }catch(SQLException exp){
            System.out.println(exp.getMessage());
        }finally{
            try{
               connection.close();
               statement.close();
               resultSet.close();
            }catch(SQLException exp2){
                System.out.println(exp2.getMessage());
            }
        }
    }
 public static void fetchFinanceOnStart(){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
           connection = DriverManager.getConnection("jdbc:mysql://localhost/scms", "root", "abdul123");
           statement = connection.createStatement();
           resultSet =  statement.executeQuery("SELECT * FROM finance");
           
           while(resultSet.next()){
               
               int id = resultSet.getInt("id");
               double funds = resultSet.getDouble("currentFunds");
               
             Finance.setCurrentFunds(funds);
//nameint id, String name, int age, double salary
              
               
        }
        }catch(SQLException exp){
            System.out.println(exp.getMessage());
        }finally{
            try{
               connection.close();
               statement.close();
               resultSet.close();
            }catch(SQLException exp2){
                System.out.println(exp2.getMessage());
            }
        }
 }
 public static void saveProgressToDb(){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
           connection = DriverManager.getConnection("jdbc:mysql://localhost/scms", "root", "abdul123");
           statement = connection.createStatement();
           statement.executeUpdate("DELETE FROM employees");
           for(int i = 0; i < Main.employees.size();i++){
               System.out.println(Main.employees.get(i).getId());
               int id = Main.employees.get(i).getId();
               String name = Main.employees.get(i).getName();
               int age = Main.employees.get(i).getAge();
               double salary= Main.employees.get(i).getSalary();
               String type= "";
               if(Main.employees.get(i) instanceof Engineer){
               type =  "Engineer";
           }else if(Main.employees.get(i) instanceof Engineer){
               type =  "Doctor";
           }else if(Main.employees.get(i) instanceof Engineer){
               type =  "Cook";
           }else if(Main.employees.get(i) instanceof Engineer){
               type =  "Astronaut";
           }else {
                type =  "Executive";
           }
               
               
               String sqlEmployees = String.format("INSERT INTO employees (id,age,name,salary,type)"
                       + " VALUES(%d,%d,'%s',%f,'%s') ",
                       id,age,name,salary,type);
               statement.executeUpdate(sqlEmployees);
           }
           statement.executeUpdate("DELETE FROM rockets");
           for(int i  = 0 ; i < Main.rockets.size();i++){
               
               int id = Main.rockets.get(i).getId();
               String name = Main.rockets.get(i).getName();
               double capacity = Main.rockets.get(i).getCapacity();
               double ppl = Main.rockets.get(i).getPricePerLaunch();
               String sqlRockets =  String.format("INSERT INTO rockets (id,name,capacity,priceperlaunch)"
                       + " VALUES(%d,'%s',%f,'%f') ",
                       id,name,capacity,ppl);
               statement.executeUpdate(sqlRockets);
           }
           
          statement.executeUpdate(String.format("UPDATE finance SET currentFunds = %f WHERE id = 1", Finance.getCurrentFunds()));

        }catch(SQLException exp){
            System.out.println(exp.getMessage());
        }finally{
            try{
               connection.close();
               statement.close();
               resultSet.close();
            }catch(SQLException exp2){
                System.out.println(exp2.getMessage());
            }
        }
 }

}
