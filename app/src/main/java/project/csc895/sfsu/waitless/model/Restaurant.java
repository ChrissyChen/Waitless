package project.csc895.sfsu.waitless.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Restaurant {
    private String restaurantID;
    private String name;
    private String imageUrl;
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    private String telephone;
    private Map<String, Date> openTime; // TODO: 9/14/17
    private List<String> cuisineTags;
    private List<String> dishes;
    private String managerID;
    private String email;
    private String password;
    private Map<Integer, List<String>> tables; //tableSize: tableID //// TODO: 9/14/17

    public Restaurant() {
    }

    public Restaurant(String name, List<String> cuisineTags) {
        this.name = name;
        this.cuisineTags = cuisineTags;
    }

//    public Restaurant(Map<String, String> map) {
//        this(map.get("name"));
//    }

    public Restaurant(String restaurantID, String name, String imageUrl, String streetAddress,
                      String city, String state, String zip, String telephone,
                      Map<String, Date> openTime, List<String> cuisineTags,
                      List<String> dishes, String managerID, String email, String password,
                      Map<Integer, List<String>> tables) {
        this.restaurantID = restaurantID;
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

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
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

    public Map<String, Date> getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Map<String, Date> openTime) {
        this.openTime = openTime;
    }

    public List<String> getCuisineTags() {
        return cuisineTags;
    }

    public String getCuisineTagsString() {
        int len = getCuisineTags().size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len - 1; i++) {
            sb.append(getCuisineTags().get(i));
            sb.append(", ");
        }
        sb.append(getCuisineTags().get(len-1));
        return sb.toString();
    }

    public void setCuisineTags(List<String> cuisineTags) {
        this.cuisineTags = cuisineTags;
    }

    public List<String> getDishes() {
        return dishes;
    }

    public void setDishes(List<String> dishes) {
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

    public Map<Integer, List<String>> getTables() {
        return tables;
    }

    public void setTables(Map<Integer, List<String>> tables) {
        this.tables = tables;
    }
}
