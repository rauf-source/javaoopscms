/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scms.rocket;

import com.scms.main.Main;

/**
 *
 * @author Admin
 */
public class Rocket {
    private int id;
    private String name;
    private double capacity;
    private double priceTag;
    private double pricePerPayload;
    private double profit;
    private double pricePerLaunch;

    public Rocket(String name, double capacity, double priceTag) {
        this.name = name;
        this.capacity = capacity;
        this.priceTag = priceTag;
        //thisn is the average cost per pound
        this.pricePerLaunch = 100000;
        id = (int)(Math.random()*1000);
       
    }
//for database fetching
    public Rocket(int id, String name, double pricePerLaunch,double capacity) {
        this.id = id;
        this.capacity = capacity;
        this.name = name;
        this.pricePerLaunch = pricePerLaunch;
    }

    
    public Rocket(String name, double capacity, double priceTag, double pricePerPayload, double pricePerLaunch) {
        this.name = name;
        this.capacity = capacity;
        this.priceTag = priceTag;
        this.pricePerPayload = pricePerPayload;
        this.pricePerLaunch = pricePerLaunch;
        setProfit();
    }

    public double getPricePerLaunch() {
        return pricePerLaunch;
    }

    public void setPricePerLaunch(double pricePerLaunch) {
        this.pricePerLaunch = pricePerLaunch;
    }

    public int getId() {
        return id;
    }
    
  
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getPriceTag() {
        return priceTag;
    }

    public void setPriceTag(double priceTag) {
        this.priceTag = priceTag;
    }

    public double getPricePerPayload() {
        return pricePerPayload;
    }

    public void setPricePerPayload(double pricePerPayload) {
        this.pricePerPayload = pricePerPayload;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit() {
        this.profit = capacity * pricePerPayload;
    }
    
    
    
    public double calculateCommercialLaunchPrice(){
        return priceTag;
    }
    public double calculateMissionLaunchPrice(double distanceFromEarth){
        return priceTag +  distanceFromEarth* 10;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
