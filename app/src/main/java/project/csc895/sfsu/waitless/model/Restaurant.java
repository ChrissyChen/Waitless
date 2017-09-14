package project.csc895.sfsu.waitless.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Restaurant {
    private String name;
    private String imageUrl;
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    private String telephone;
    private HashMap<String, Date> openTime; // TODO: 9/14/17
    private ArrayList<String> cuisineTags;
    private ArrayList<Dish> dishes; // TODO: 9/14/17
    private String managerID;
    private String email;
    private String password;
    private HashMap<Integer, String> tables; //tableSize: tableID //// TODO: 9/14/17  

    public Restaurant(String name, String imageUrl, String streetAddress,
                      String city, String state, String zip, String telephone,
                      HashMap<String, Date> openTime, ArrayList<String> cuisineTags,
                      ArrayList<Dish> dishes, String managerID, String email,
                      String password, HashMap<Integer, String> tables) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.telephone = telephone;
        this.openTime = openTime;
        this.cuisineTags = cuisineTags;
        this.dishes = dishes;
        this.managerID = managerID;
        this.email = email;
        this.password = password;
        this.tables = tables;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public HashMap<String, Date> getOpenTime() {
        return openTime;
    }

    public void setOpenTime(HashMap<String, Date> openTime) {
        this.openTime = openTime;
    }

    public ArrayList<String> getCuisineTags() {
        return cuisineTags;
    }

    public void setCuisineTags(ArrayList<String> cuisineTags) {
        this.cuisineTags = cuisineTags;
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<Integer, String> getTables() {
        return tables;
    }

    public void setTables(HashMap<Integer, String> tables) {
        this.tables = tables;
    }
}
